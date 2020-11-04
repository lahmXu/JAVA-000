package com.lahmxu.gateway.inbound;

import com.lahmxu.gateway.filter.AddHeaderHttpRequestFilter;
import com.lahmxu.gateway.outbound.httpclient4.HttpOutboundHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HttpInboundHandler extends ChannelInboundHandlerAdapter {

    private final String proxyServer;
    private HttpOutboundHandler handler;
    private AddHeaderHttpRequestFilter addHeaderHttpRequestFilter = new AddHeaderHttpRequestFilter();


    public HttpInboundHandler(String proxyServer) {
        this.proxyServer = proxyServer;
        handler = new HttpOutboundHandler(this.proxyServer);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        try {
            log.info("channelRead流量接口请求开始，时间为{}", System.currentTimeMillis());
            FullHttpRequest fullRequest = (FullHttpRequest) msg;
            String uri = fullRequest.uri();
            log.info("接收到的请求url为{}", uri);

            // filter
            addHeaderHttpRequestFilter.filter(fullRequest,ctx);

            handler.handle(fullRequest, ctx);
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }
}
