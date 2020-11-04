package com.lahmxu.gateway.outbound.netty4;


import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;

import java.net.URI;

public class NettyHttpClient {
    public void connect(String host,int port) {

        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workerGroup);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.option(ChannelOption.SO_KEEPALIVE,true);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    // 发送的http request请求，使用HttpRequestEncoder编码
                    socketChannel.pipeline().addLast(new HttpRequestEncoder());
                    // 使用HttpResponseDecoder解码http response
                    socketChannel.pipeline().addLast(new HttpResponseDecoder());
                    // 指定Handler处理响应
                    socketChannel.pipeline().addLast(new NettyHttpClientOutBoundHandler());
                }
            });

            ChannelFuture channelFuture = bootstrap.connect(host, port).sync();

            URI uri = new URI("http://127.0.0.1:8888");
            String msg = "Are you OK?";
            DefaultFullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET,uri.toASCIIString(), Unpooled.wrappedBuffer(msg.getBytes("UTF-8")));
            request.headers().set(HttpHeaders.Names.HOST,host);
            request.headers().set(HttpHeaders.Names.CONNECTION,HttpHeaders.Values.KEEP_ALIVE);
            request.headers().set(HttpHeaders.Names.CONTENT_LENGTH,request.content().readableBytes());

            channelFuture.channel().write(request);
            channelFuture.channel().flush();
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
            workerGroup.shutdownGracefully();
        }
    }
}
