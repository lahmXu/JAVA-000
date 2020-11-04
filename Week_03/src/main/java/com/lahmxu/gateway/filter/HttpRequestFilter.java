package com.lahmxu.gateway.filter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

/**
 * 请求 filter
 */
public interface HttpRequestFilter {
    void filter(FullHttpRequest fullHttpRequest, ChannelHandlerContext ctx);
}
