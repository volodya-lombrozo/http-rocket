package org.rocket.response;

public interface HttpResponse {
    int status();

    byte[] body();
}
