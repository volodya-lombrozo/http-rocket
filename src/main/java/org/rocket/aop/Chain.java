package org.rocket.interceptors;

import org.rocket.request.HttpRequest;

public interface Chain {

    Chain linkWithNext(Chain chain);

    void handle(HttpRequest request);

}
