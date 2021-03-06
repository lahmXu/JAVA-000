package io.kimmking.rpcfx.api;

public class RpcfxRequest<T> {

    private Class<T> serviceClass;

    private String method;

    private Object[] params;

    public RpcfxRequest() {
    }

    public RpcfxRequest(Class<T> targetClass, String methodName, Object[] objects) {
        this.serviceClass = targetClass;
        this.method = methodName;
        this.params = objects;
    }

    public Class<T> getServiceClass() {
        return serviceClass;
    }

    public void setServiceClass(Class<T> serviceClass) {
        this.serviceClass = serviceClass;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }

}
