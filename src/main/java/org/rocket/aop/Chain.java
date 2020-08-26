package org.rocket.aop;

import org.rocket.request.HttpRequest;
import reactor.core.publisher.Mono;

public interface Chain {

    Mono<HttpRequest> handle(HttpRequest request);

}
