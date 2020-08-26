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

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;

public class NettyServer implements HttpServer {

    private final Logger logger;
    private final Port port;
    private final NettySpecificParams specificParams;
    private final Collection<HttpTarget> targets;
    private Chain interceptorChain;

    public NettyServer() {
        this(new Port.Default(), new Logger.Default(), new NettySpecificParams());
    }

    public NettyServer(Port port, Logger logger, NettySpecificParams specificParams) {
        this.port = port;
        this.logger = logger;
        this.specificParams = specificParams;
        this.targets = new LinkedList<>();
    }


    public void run() {
        EventLoopGroup bossGroup = specificParams.bossGroup();
        EventLoopGroup workerGroup = specificParams.workerGroup();
        Class<? extends ServerSocketChannel> channelClass = specificParams.channelClass();
        try {
            Channel channel = new ServerBootstrap()
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .group(bossGroup, workerGroup)
                    .channel(channelClass)
                    .childHandler(new NettyChannel(targets, workerGroup, interceptorChain)).bind(port.asInt())
                    .sync().channel();
            logStarting();
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            logErr(e);
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public void addHttpTargets(Collection<HttpTarget> targets) {
        this.targets.addAll(targets);
    }

    public void addHttpTargets(HttpTarget... targets) {
        this.targets.addAll(Arrays.asList(targets));
    }

    public void registerInterceptorChain(Chain chain) {
        this.interceptorChain = chain;
    }


    private void logStarting() {
        logger.info("Netty server started: " + "http://127.0.0.1:" + port.asInt() + "/");
    }

    private void logErr(Throwable throwable) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        throwable.printStackTrace(printWriter);
        logger.err(stringWriter.toString());
    }
}
