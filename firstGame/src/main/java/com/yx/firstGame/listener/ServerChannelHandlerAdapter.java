package com.yx.firstGame.listener;

// 记录调用方法的元信息的类
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import javax.annotation.Resource;

/**
 * 多线程共享
 */
@Sharable
public class ServerChannelHandlerAdapter extends ChannelHandlerAdapter {
    /**
     * 注入请求分排器
     */
    @Resource
    private RequestDispatcher dispatcher;

    public ServerChannelHandlerAdapter(){
        this.dispatcher=new RequestDispatcher();
    }

    //异常调用
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx,Object o) {
        System.out.println("进入channelRead");
        dispatcher.dispatcher(ctx,o);
    }

    //通知处理器最后的channelread()是当前批处理中的最后一条消息时调用
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

}
