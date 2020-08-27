package org.rocket.netty;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.EventExecutorGroup;

import java.util.Collection;
import java.util.LinkedList;

public class NettyGroups {


    private final int bossGroupSize;
    private final int workerGroupSize;
    private final Collection<EventLoopGroup> pool = new LinkedList<>();

    public NettyGroups() {
        this(1, 4);
    }

    public NettyGroups(int bossGroupSize, int workerGroupSize) {
        this.bossGroupSize = bossGroupSize;
        this.workerGroupSize = workerGroupSize;
    }

    public EventLoopGroup bossGroup() {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(bossGroupSize);
        pool.add(bossGroup);
        return bossGroup;
    }

    public EventLoopGroup workerGroup() {
        NioEventLoopGroup workerGroup = new NioEventLoopGroup(workerGroupSize);
        pool.add(workerGroup);
        return workerGroup;
    }

    public Class<? extends ServerSocketChannel> allowedChannelType() {
        return NioServerSocketChannel.class;
    }


    public void shutdownGracefullyAll() {
        pool.forEach(EventExecutorGroup::shutdownGracefully);
    }
}
