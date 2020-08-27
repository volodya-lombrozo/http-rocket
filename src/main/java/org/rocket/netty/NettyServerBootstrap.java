package org.rocket.netty;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import org.rocket.logger.Logger;
import org.rocket.netty.options.Option;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collection;

public class NettyServerBootstrap {

    private final NettySpecificConfiguration nettySpecificConfiguration;
    private final ChannelInitializer<?> pipeline;
    private final Logger logger;


    public NettyServerBootstrap(NettySpecificConfiguration nettySpecificConfiguration, ChannelInitializer<?> pipeline, Logger logger) {
        this.nettySpecificConfiguration = nettySpecificConfiguration;
        this.pipeline = pipeline;
        this.logger = logger;
    }

    public void run(Port port) {
        NettyGroups groups = nettySpecificConfiguration.groups();
        try {
            Channel channel = new ServerBootstrap()
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .group(groups.bossGroup(), groups.workerGroup())
                    .channel(groups.allowedChannelType())
                    .childHandler(pipeline)
                    .bind(port.asInt())
                    .sync().channel();
            logStarting(port);
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            logErr(e);
        } finally {
            groups.shutdownGracefullyAll();
        }
    }

    private ServerBootstrap fillOptions(ServerBootstrap serverBootstrap){
        Collection<Option<?>> options = nettySpecificConfiguration.options();
        for (Option<?> option : options) {
            serverBootstrap = option.addToServer(serverBootstrap);
        }
        return serverBootstrap;
    }


    private void logStarting(Port port) {
        logger.info("Netty server started: " + "http://127.0.0.1:" + port.asInt() + "/");
    }

    private void logErr(Throwable throwable) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        throwable.printStackTrace(printWriter);
        logger.err(stringWriter.toString());
    }


}
