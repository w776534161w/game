package com.yx.firstGame.listener;

import com.yx.firstGame.config.NettyConstant;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.CharsetUtil;

import javax.swing.text.ParagraphView;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 请求分排器
 */
public class RequestDispatcher {
    private ExecutorService executorService = Executors.newFixedThreadPool(NettyConstant.getMaxThreads());

    /**
     * 发送
     *
     * @param ctx
     */
    public void dispatcher(final ChannelHandlerContext ctx, Object o) {
        executorService.submit(()-> {

                ChannelFuture f = null;
                try {
                    System.out.println("进入到了dispatcher");
                    ByteBuf in = (ByteBuf) o;
                    // 打印客户端输入，传输过来的的字符
                    System.out.print(in.toString(CharsetUtil.UTF_8));
                } catch (Exception e) {

                } finally {
                    f.addListener(ChannelFutureListener.CLOSE);
                }
        });
    }


}
