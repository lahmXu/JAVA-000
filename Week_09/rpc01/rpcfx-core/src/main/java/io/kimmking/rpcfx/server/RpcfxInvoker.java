package io.kimmking.rpcfx.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import io.kimmking.rpcfx.api.RpcfxRequest;
import io.kimmking.rpcfx.api.RpcfxResolver;
import io.kimmking.rpcfx.api.RpcfxResponse;
import io.kimmking.rpcfx.demo.api.IUserService;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class RpcfxInvoker {

    private RpcfxResolver resolver;

    public RpcfxInvoker(RpcfxResolver resolver){
        this.resolver = resolver;
    }

    public RpcfxResponse invoke(RpcfxRequest request) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        RpcfxResponse response = new RpcfxResponse();

        // 作业1：改成泛型和反射
        Class<?> userClazz = Class.forName(request.getServiceClass().getName());
        Method method = userClazz.getMethod(request.getMethod(), request.getParams()[0].getClass());
        Object result = method.invoke(resolver.resolve(request.getServiceClass().getName()), request.getParams()[0]);

        // 两次json序列化能否合并成一个
        response.setResult(JSON.toJSONString(result, SerializerFeature.WriteClassName));
        response.setStatus(true);
        return response;
    }

}
