package org.rocket.netty;

import io.netty.channel.*;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpUtil;
import org.rocket.HttpTarget;
import org.rocket.aop.Chain;
import org.rocket.response.InternalServerError;
import org.rocket.response.TimeoutError;

import java.util.concurrent.TimeoutException;

import static io.netty.handler.codec.http.HttpHeaderNames.CONNECTION;
import static io.netty.handler.codec.http.HttpHeaderValues.*;

public class NettyTargetHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private final HttpTarget target;
    private final Chain interceptorChain;

    public NettyTargetHandler(HttpTarget target, Chain interceptorChain) {
        this.target = target;
        this.interceptorChain = interceptorChain;
    }

    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) {
        interceptorChain.handle(new NettyHttpRequest(msg))
                .flatMap(request -> target.handle(request).timeout(target.timeout()).map(NettyHttpResponse::new))
                .subscribe(
                        response -> writeHttpResponse(ctx, msg, response),
                        throwable -> writeErrorHttpResponse(ctx, msg, throwable)
                );
    }


    private FullHttpResponse addConnectionHeader(boolean keepAlive, boolean protocolVersionIsKeepAliveDefault, FullHttpResponse response) {
        if (keepAlive) {
            if (!protocolVersionIsKeepAliveDefault)
                response.headers().set(CONNECTION, KEEP_ALIVE);
        } else response.headers().set(CONNECTION, CLOSE);
        return response;
    }


    private void writeHttpResponse(ChannelHandlerContext ctx, FullHttpRequest request, FullHttpResponse response) {
        boolean keepAlive = HttpUtil.isKeepAlive(request);
        boolean protocolVersionIsKeepAliveDefault = request.protocolVersion().isKeepAliveDefault();
        ChannelFuture f = ctx.write(addConnectionHeader(keepAlive, protocolVersionIsKeepAliveDefault, response));
        if (!keepAlive) f.addListener(ChannelFutureListener.CLOSE);
        ctx.flush();
    }

    private void writeErrorHttpResponse(ChannelHandlerContext ctx, FullHttpRequest request, Throwable throwable) {
        if (throwable instanceof TimeoutException) {
            writeHttpResponse(ctx, request, new NettyHttpResponse(new TimeoutError()));
        } else
            writeHttpResponse(ctx, request, new NettyHttpResponse(new InternalServerError()));
    }
}
