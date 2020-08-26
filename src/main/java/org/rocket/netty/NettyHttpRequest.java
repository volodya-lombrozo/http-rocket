package org.rocket.netty.request;

import io.netty.handler.codec.http.FullHttpRequest;
import org.rocket.general.Body;
import org.rocket.general.Headers;
import org.rocket.general.Parameters;
import org.rocket.request.HttpRequest;

public class NettyHttpRequest implements HttpRequest {

    private final FullHttpRequest request;

    public NettyHttpRequest(FullHttpRequest request) {
        this.request = request;
    }

    @Override
    public Headers headers() {
        return null;
    }

    @Override
    public Body body() {
        return null;
    }

    @Override
    public Parameters params() {
        return null;
    }
}
