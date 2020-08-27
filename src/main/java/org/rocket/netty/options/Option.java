package org.rocket.netty.options;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;

public interface Option<T> {

    ChannelOption<T> channelOption();

    T optionValue();

    ServerBootstrap addToServer(ServerBootstrap serverBootstrap);
}
