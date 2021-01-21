package com.lahmxu.xmq.core;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashMap;

@Data
@AllArgsConstructor
public class XmqMessage<T> {

    private HashMap<String, Object> headers;

    private T body;
}
