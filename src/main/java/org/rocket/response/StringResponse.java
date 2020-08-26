package org.rocket.response;

public class StringResponse implements HttpResponse {

    private final String response;

    public StringResponse(String response) {
        this.response = response;
    }

    @Override
    public int status() {
        return 200;
    }

    @Override
    public byte[] body() {
        return response.getBytes();
    }
}
