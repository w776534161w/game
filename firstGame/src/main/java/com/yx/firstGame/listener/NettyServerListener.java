package com.yx.firstGame.listener;


import com.yx.firstGame.config.NettyConfig;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

/**
 * 服务启动监听器
 *
 * @author 叶云轩
 */
public class NettyServerListener {
    ServerBootstrap serverBootstrap = new ServerBootstrap();
    EventLoopGroup boss = new NioEventLoopGroup();
    EventLoopGroup work = new NioEventLoopGroup();

    private ServerChannelHandlerAdapter channelHandlerAdapter;
    private NettyConfig nettyConfig;
    public NettyServerListener(){
        this.nettyConfig=new NettyConfig(8888);
        this.channelHandlerAdapter= new ServerChannelHandlerAdapter();
    }


    public void close() {
        //优雅退出
        boss.shutdownGracefully();
        work.shutdownGracefully();
    }

    public void start() {
        // 从配置文件中(application.yml)获取服务端监听端口号
        int port = nettyConfig.getPort();
        serverBootstrap.group(boss, work)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 128)
                .handler(new LoggingHandler(LogLevel.INFO));
        try {
            //设置事件处理
            serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ChannelPipeline pipeline = ch.pipeline();
//                    pipeline.addLast(new LengthFieldBasedFrameDecoder(1024
//                            , 0, 2, 0, 2));
//                    pipeline.addLast(new LengthFieldPrepender(2));
//                    pipeline.addLast(new ObjectCodec());
                    System.out.println("get in to listener");
                    pipeline.addLast(channelHandlerAdapter);
                }
            });
            ChannelFuture f = serverBootstrap.bind(port).sync();
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            boss.shutdownGracefully();
            work.shutdownGracefully();
        }
    }
}

