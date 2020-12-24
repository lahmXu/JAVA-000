package io.kimmking.rpcfx.client;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import io.kimmking.rpcfx.api.RpcfxRequest;
import io.kimmking.rpcfx.api.RpcfxResponse;
import io.kimmking.rpcfx.netty.NettyClient;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;

public final class Rpcfx {

    private final static OkHttpClient client = new OkHttpClient();

    static {
        ParserConfig.getGlobalInstance().addAccept("io.kimmking");
    }

    public static <T> T create(Class<T> target, String url) {
        RpcfxInvocationHandler rpcfxInvocationHandler = new RpcfxInvocationHandler();
        return (T) rpcfxInvocationHandler.creatProxy(target,url);
    }

    /**
     * 实现MethodInterceptor，对接口进行增强
     * @param <T>
     */
    public static class RpcfxInvocationHandler<T> implements MethodInterceptor {

        private Class<T> targetClass;

        private String url;

        public T creatProxy(Class<T> targetClass,String url){
            this.targetClass = targetClass;
            this.url = url;
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(targetClass);
            enhancer.setCallback(this);
            return (T) enhancer.create();
        }

        /**
         *
         * @param o 被代理对象
         * @param method 被代理方法
         * @param objects 方法参数
         * @param methodProxy 当前执行方法的代理对象
         * @return
         * @throws IOException
         */
        @Override
        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws IOException {
            RpcfxRequest request = new RpcfxRequest(targetClass, method.getName(), objects);
            RpcfxResponse response = invoke(request, url);
            return JSON.parse(response.getResult().toString());

        }

        protected RpcfxResponse invoke(RpcfxRequest request, String url) throws IOException {

            RpcfxResponse response = nettyPost(request, url);

            // 这里判断response.status，处理异常
            // 考虑封装一个全局的RpcfxException

            return response;
        }
        private static RpcfxResponse post(RpcfxRequest req, String url) throws IOException {
            final MediaType JSONTYPE = MediaType.get("application/json; charset=utf-8");
            String reqJson = JSON.toJSONString(req);
            System.out.println("req json: "+reqJson);
            final Request request = new Request.Builder()
                    .url(url)
                    .post(RequestBody.create(JSONTYPE, reqJson))
                    .build();
            String respJson = client.newCall(request).execute().body().string();
            System.out.println("resp json: "+respJson);
            return JSON.parseObject(respJson, RpcfxResponse.class);
        }

        public static RpcfxResponse nettyPost(RpcfxRequest request,String url) {
            try {
                return NettyClient.getInstance().getResponse(request,url);
            } catch (URISyntaxException | InterruptedException | UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}

