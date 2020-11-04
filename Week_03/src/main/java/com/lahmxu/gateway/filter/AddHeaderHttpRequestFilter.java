package com.lahmxu.gateway.filter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

public class AddHeaderHttpRequestFilter implements  HttpRequestFilter {
    @Override
    public void filter(FullHttpRequest fullHttpRequest, ChannelHandlerContext ctx) {
        fullHttpRequest.headers().set("nio","lahmxu");
    }
}
