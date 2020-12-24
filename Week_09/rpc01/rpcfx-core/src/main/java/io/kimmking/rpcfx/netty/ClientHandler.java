package io.kimmking.rpcfx.netty;

import com.alibaba.fastjson.JSON;
import io.kimmking.rpcfx.api.RpcfxResponse;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.util.CharsetUtil;

import java.util.concurrent.CountDownLatch;

/**
 * 返回处理类
 * @author lahmxu
 */
public class ClientHandler extends SimpleChannelInboundHandler<FullHttpResponse> {

    private CountDownLatch countDownLatch;
    private RpcfxResponse response;

    @Override
    public void channelRead0(ChannelHandlerContext channelHandlerContext, FullHttpResponse rpcResponse) throws Exception {
        if (rpcResponse instanceof FullHttpResponse) {
            FullHttpResponse fullHttpResponse = (FullHttpResponse) rpcResponse;
            ByteBuf buf = fullHttpResponse.content();
            String result = buf.toString(CharsetUtil.UTF_8);
            response = JSON.parseObject(result, RpcfxResponse.class);
        }
        countDownLatch.countDown();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
        cause.printStackTrace();
        ctx.close();
    }

    void setLatch(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    public RpcfxResponse getResponse() throws InterruptedException {
        countDownLatch.await();
        return response;
    }
}
