package io.kimmking.rpcfx.api;

/**
 * 异常处理类
 */
public class RpcfxException extends RuntimeException {
    public RpcfxException() {
    }

    public RpcfxException(String message){
        super(message);
    }

    public RpcfxException(String message, Throwable throwable){
        super(message,throwable);
    }

    public RpcfxException(Throwable throwable){
        super(throwable);
    }
}
