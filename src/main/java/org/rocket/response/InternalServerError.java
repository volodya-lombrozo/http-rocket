package org.rocket.response;

public class InternalServerError implements HttpResponse {
    @Override
    public int status() {
        return 500;
    }

    @Override
    public byte[] body() {
        return "Internal server error".getBytes();
    }
}
