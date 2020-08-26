package org.rocket.netty;

import java.util.Random;

public interface Port {

    int asInt();


    class AsInt implements Port {

        private final int port;

        public AsInt(int port) {
            this.port = port;
        }

        public int asInt() {
            return port;
        }
    }

    class Default implements Port {

        public int asInt() {
            return 8080;
        }
    }

    class Fake implements Port {

        private final int port = new Random().nextInt(10_000) + 20_000;

        public int asInt() {
            return port;
        }
    }
}
