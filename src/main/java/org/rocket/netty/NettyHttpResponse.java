package org.rocket.netty;

import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.AsciiString;
import org.rocket.response.HttpResponse;

import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_LENGTH;

public class NettyHttpResponse extends DefaultFullHttpResponse {

    private final HttpResponse httpResponse;

    public NettyHttpResponse(HttpResponse response) {
        super(HttpVersion.HTTP_1_1, HttpResponseStatus.valueOf(response.status()));
        content().writeBytes(Unpooled.wrappedBuffer(response.body()));
        setContentLength();
        this.httpResponse = response;
    }

    private void setContentLength() {
        headers().setInt(CONTENT_LENGTH, content().readableBytes());
    }


    public AsciiString contentType() {
        return null;
    }

    public byte[] rawEntity() {
        return "Server did not respond within the specified timeout interval (3 seconds). Please, contact technical support.".getBytes();
    }
}
