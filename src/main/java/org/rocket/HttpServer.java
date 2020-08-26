package org.rocket;

import org.rocket.aop.Chain;

import java.util.Collection;

public interface HttpServer {

    void run();

    void addHttpTargets(Collection<HttpTarget> targets);

    void addHttpTargets(HttpTarget... targets);

    void registerInterceptorChain(Chain chain);
}
