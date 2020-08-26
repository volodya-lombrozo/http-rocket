package org.rocket.netty;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettySpecificParams {


    private final int bossGroupSize;
    private final int workerGroupSize;


    public NettySpecificParams() {
        this(1, 4);
    }

    public NettySpecificParams(int bossGroupSize, int workerGroupSize) {
        this.bossGroupSize = bossGroupSize;
        this.workerGroupSize = workerGroupSize;
    }

    public EventLoopGroup bossGroup() {
        return new NioEventLoopGroup(bossGroupSize);
    }

    public EventLoopGroup workerGroup() {
        return new NioEventLoopGroup(workerGroupSize);
    }

    public Class<? extends ServerSocketChannel> channelClass() {
        return NioServerSocketChannel.class;
    }


}
