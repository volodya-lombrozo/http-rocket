package org.rocket.response;

public class TimeoutError implements HttpResponse {
    @Override
    public int status() {
        return 408;
    }
}
