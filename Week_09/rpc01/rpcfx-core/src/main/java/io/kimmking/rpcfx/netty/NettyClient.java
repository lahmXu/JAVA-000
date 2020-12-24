package io.kimmking.rpcfx.netty;

import com.alibaba.fastjson.JSON;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.kimmking.rpcfx.api.RpcfxRequest;
import io.kimmking.rpcfx.api.RpcfxResponse;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

/**
 * @author lahmxu
 */
@Slf4j
public class NettyClient {

    private static enum NettyClientSingleton {
        INSTANCE;
        private NettyClient instance;

        NettyClientSingleton() {
            instance = new NettyClient();
        }

        public NettyClient getInstance() {
            return instance;
        }
    }

    public static NettyClient getInstance() {
        return NettyClientSingleton.INSTANCE.getInstance();
    }


    private ConcurrentHashMap<String, Channel> channelPool = new ConcurrentHashMap<>();
    private EventLoopGroup clientGroup = new NioEventLoopGroup(1, new ThreadFactoryBuilder().setNameFormat("client work-%d").build());

    private NettyClient() {
    }

    public RpcfxResponse getResponse(RpcfxRequest rpcfxRequest, String url) throws URISyntaxException, InterruptedException, UnsupportedEncodingException {
        URI uri = new URI(url);
        FullHttpRequest request = convertNettyRequest(rpcfxRequest, uri);

        String cacheKey = uri.getHost() + ":" + uri.getPort();
        if (channelPool.contains(cacheKey)) {
            Channel channel = channelPool.get(cacheKey);
            if (!channel.isActive() || !channel.isWritable() || !channel.isOpen()) {
                log.error("Channel can't use");
            } else {
                ClientHandler clientHandler = new ClientHandler();
                clientHandler.setLatch(new CountDownLatch(1));
                channel.pipeline().replace("clientHandler", "clientHandler", clientHandler);
                try {
                    channel.writeAndFlush(request).sync();
                    return clientHandler.getResponse();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    channel.close();
                    channelPool.remove(cacheKey);
                }
            }
        }

        // 创建一个新的 channel 处理请求，并接入 channelPool
        ClientHandler clientHandler = new ClientHandler();
        clientHandler.setLatch(new CountDownLatch(1));
        Channel channel = createChannel(uri.getHost(), uri.getPort());
        channel.pipeline().replace("clientHandler", "clientHandler", clientHandler);
        channelPool.put(cacheKey, channel);

        channel.writeAndFlush(request).sync();
        return clientHandler.getResponse();

    }

    private Channel createChannel(String host, int port) throws InterruptedException {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(clientGroup)
                .option(ChannelOption.SO_REUSEADDR, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.AUTO_CLOSE, true)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .channel(NioSocketChannel.class)
                .handler(new ClientInitializer());
        return bootstrap.connect(host, port).sync().channel();
    }

    /**
     * 将 {@RpcRequest} 转成 netty 自定义的通信格式 {@RpcProtocol}
     * 使用FullHttpRequest包装请求
     *
     * @param rpcRequest RpcRequest
     * @return FullHttpRequest
     */
    private FullHttpRequest convertNettyRequest(RpcfxRequest rpcRequest, URI uri) throws UnsupportedEncodingException {
        String content = JSON.toJSONString(rpcRequest);
        FullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_0, HttpMethod.POST, uri.toASCIIString(), Unpooled.wrappedBuffer(content.getBytes("UTF-8")));
        request.headers().add(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
        request.headers().add(HttpHeaderNames.CONTENT_LENGTH, request.content().readableBytes());
        request.headers().add(HttpHeaders.Names.CONTENT_TYPE, "application/json");
        return request;
    }


}
