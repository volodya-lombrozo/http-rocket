package org.rocket.netty;

import org.rocket.netty.options.Option;

import java.util.Collection;
import java.util.Collections;

public class NettySpecificConfiguration {

    private final NettyGroups groups;
    private final Collection<Option<?>> options;


    public NettySpecificConfiguration() {
        this(new NettyGroups(), Collections.emptyList());
    }

    public NettySpecificConfiguration(NettyGroups groups, Collection<Option<?>> options) {
        this.groups = groups;
        this.options = options;
    }

    public NettyGroups groups() {
        return groups;
    }

    public Collection<Option<?>> options() {
        return options;
    }
}
