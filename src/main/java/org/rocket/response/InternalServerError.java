package org.rocket.response;

public class InternalServerError implements HttpResponse {
    @Override
    public int status() {
        return 500;
    }
}
