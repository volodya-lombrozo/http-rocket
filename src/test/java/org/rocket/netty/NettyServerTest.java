package org.rocket.netty;

import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.rocket.HttpTarget;
import org.rocket.request.HttpRequest;
import org.rocket.response.HttpResponse;
import org.rocket.response.StringResponse;
import reactor.core.publisher.Mono;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class NettyServerTest {

    private final NettyServer server = new NettyServer(new Port.Fake(), new HttpTarget() {
        @Override
        public String path() {
            return "api";
        }

        @Override
        public Mono<? extends HttpResponse> handle(HttpRequest request) {
            StringResponse data = new StringResponse("Wake up, Neo!");
            return Mono.just(data).delayElement(Duration.ofSeconds(3));
        }

        @Override
        public Duration timeout() {
            return Duration.ofSeconds(1);
        }
    }
    );

    @BeforeEach
    public void startServer() {
        server.run();
    }


    @Disabled("Integration test")
    @Test
    public void serverIsStarted() {
        assertNotNull(server);
    }


}