package org.rocket.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.HttpServerExpectContinueHandler;
import io.netty.handler.codec.http.cors.CorsConfigBuilder;
import io.netty.handler.codec.http.cors.CorsHandler;
import org.rocket.HttpTarget;
import org.rocket.aop.Chain;

import java.util.Collection;

public class NettyChannel extends ChannelInitializer<SocketChannel> {


    private final Collection<HttpTarget> targets;
    private final EventLoopGroup loopGroup;
    private final Chain interceptorChain;

    public NettyChannel(Collection<HttpTarget> targets, EventLoopGroup loopGroup, Chain interceptorChain) {
        this.targets = targets;
        this.loopGroup = loopGroup;
        this.interceptorChain = interceptorChain;
    }


    protected void initChannel(SocketChannel socketChannel) {
        int MAX_CONTENT_LENGTH = 1048576;
        ChannelPipeline pipeline = socketChannel.pipeline()
                .addLast(new HttpServerCodec())
                .addLast(new HttpServerExpectContinueHandler())
                .addLast(new HttpObjectAggregator(MAX_CONTENT_LENGTH))
                .addLast(new CorsHandler(CorsConfigBuilder.forAnyOrigin()
                        .allowedRequestHeaders("Content-Type", "api_key", "Authorization", "X-REQUEST-SIGN")
                        .allowedRequestHeaders("Content-Type", "api_key", "Authorization", "AUTH_TOKEN")
                        .allowedRequestMethods(HttpMethod.POST, HttpMethod.GET, HttpMethod.PUT, HttpMethod.DELETE, HttpMethod.OPTIONS)
                        .allowNullOrigin().allowCredentials().build()));
        targets.forEach(t -> pipeline.addLast(loopGroup, new NettyTargetHandler(t, interceptorChain)));
    }

}
