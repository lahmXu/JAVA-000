package io.kimmking.rpcfx.demo.provider;

import io.kimmking.rpcfx.demo.api.Order;
import io.kimmking.rpcfx.demo.api.OrderService;

public class OrderServiceImpl implements OrderService {

    @Override
    public Order findOrderById(Integer id) {
        return new Order(id, "order--" + System.currentTimeMillis(), 9.9f);
    }
}
