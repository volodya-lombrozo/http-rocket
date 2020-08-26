package org.rocket;

import org.rocket.request.HttpRequest;
import org.rocket.response.HttpResponse;
import reactor.core.publisher.Mono;

import java.time.Duration;

public interface HttpTarget {

    String path();

    Mono<? extends HttpResponse> handle(HttpRequest request);

    Duration timeout();
}
