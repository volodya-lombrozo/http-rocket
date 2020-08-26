package org.rocket.netty.response;

import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.AsciiString;
import org.rocket.response.HttpResponse;

public class NettyHttpResponse extends DefaultFullHttpResponse {

    private final HttpResponse httpResponse;

    public NettyHttpResponse(HttpResponse response) {
        super(HttpVersion.HTTP_1_1, HttpResponseStatus.valueOf(response.status()));
        this.httpResponse = response;
    }

    public AsciiString contentType(){
        return null;
    }

    public byte[] rawEntity(){
        return "Server did not respond within the specified timeout interval (3 seconds). Please, contact technical support.".getBytes();
    }
}
