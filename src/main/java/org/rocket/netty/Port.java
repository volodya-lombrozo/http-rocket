package org.rocket.netty;

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

        public int asInt() {
            return 0;
        }
    }
}
