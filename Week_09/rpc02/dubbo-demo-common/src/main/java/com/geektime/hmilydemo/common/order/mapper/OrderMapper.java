package com.geektime.hmilydemo.common.order.mapper;

import com.geektime.hmilydemo.common.order.entity.Order;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper {

    int save(Order order);

    int update(Order order);
}
