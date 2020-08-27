package org.rocket.netty.options;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;

public class SoBacklog implements Option<Integer> {

    private final int numberConnections;

    public SoBacklog() {
        this(1024);
    }

    public SoBacklog(int numberConnections) {
        this.numberConnections = numberConnections;
    }

    @Override
    public ChannelOption<Integer> channelOption() {
        return ChannelOption.SO_BACKLOG;
    }

    @Override
    public Integer optionValue() {
        return numberConnections;
    }

    @Override
    public ServerBootstrap addToServer(ServerBootstrap serverBootstrap) {
        return serverBootstrap.option(channelOption(), optionValue());
    }

}
