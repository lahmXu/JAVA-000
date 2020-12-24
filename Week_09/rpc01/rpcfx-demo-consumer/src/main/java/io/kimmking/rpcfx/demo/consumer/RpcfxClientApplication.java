package io.kimmking.rpcfx.demo.consumer;

import io.kimmking.rpcfx.client.Rpcfx;
import io.kimmking.rpcfx.demo.api.IUserService;
import io.kimmking.rpcfx.demo.api.Order;
import io.kimmking.rpcfx.demo.api.OrderService;
import io.kimmking.rpcfx.demo.api.User;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RpcfxClientApplication {

	public static void main(String[] args) {

		// IUserService service = new xxx();
		// service.findById

		IUserService userService = Rpcfx.create(IUserService.class, "http://localhost:8081/");
		User user = userService.findById(1);
		System.out.println("find user id=1 from server: " + user.getName());

		System.out.println("******************************************");
		OrderService orderService = Rpcfx.create(OrderService.class, "http://localhost:8081/");
		Order order = orderService.findOrderById(1992129);
		System.out.println(String.format("find order name=%s, amount=%f",order.getName(),order.getAmount()));

	}

}
