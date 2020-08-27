package org.rocket.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import org.rocket.HttpServer;
import org.rocket.HttpTarget;
import org.rocket.aop.Chain;
import org.rocket.logger.Logger;
import org.rocket.netty.options.SoBacklog;

import java.util.*;

public class NettyServer implements HttpServer {

    private final Port port;
    private final Collection<HttpTarget> targets;
    private final Chain interceptorChain;
    private final NettySpecificConfiguration nettySpecificConfigutaion;


    public NettyServer() {
        this(new Port.Default());
    }

    public NettyServer(Port port) {
        this(port, Collections.emptyList());
    }

    public NettyServer(Port port, HttpTarget... targets){
        this(port, Arrays.asList(targets), new Chain.Empty());
    }

    public NettyServer(Port port, Collection<HttpTarget> targets) {
        this(port, targets, new Chain.Empty());
    }

    public NettyServer(Port port, Collection<HttpTarget> targets, Chain interceptorChain) {
        this(port, targets, interceptorChain, new NettySpecificConfiguration());
    }

    public NettyServer(Port port, Collection<HttpTarget> targets, Chain interceptorChain, NettySpecificConfiguration nettySpecificConfigutaion) {
        this.port = port;
        this.targets = targets;
        this.interceptorChain = interceptorChain;
        this.nettySpecificConfigutaion = nettySpecificConfigutaion;
    }

    public void run() {
        NettySpecificConfiguration nettySpecificConfiguration = new NettySpecificConfiguration(new NettyGroups(), Collections.singleton(new SoBacklog()));
        NettyChannelPipeline pipeline = new NettyChannelPipeline(targets, interceptorChain);
        NettyServerBootstrap nettyServerBootstrap = new NettyServerBootstrap(nettySpecificConfiguration, pipeline, new Logger.Default());
        nettyServerBootstrap.run(port);
    }

}
