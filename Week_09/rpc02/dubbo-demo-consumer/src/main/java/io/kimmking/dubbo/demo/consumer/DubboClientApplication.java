package io.kimmking.dubbo.demo.consumer;

import io.kimmking.dubbo.demo.api.service.OrderService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;

@SpringBootApplication(exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class,DataSourceAutoConfiguration.class})
public class DubboClientApplication {

	@DubboReference(version = "1.0.0", url = "dubbo://127.0.0.1:12345")
	private OrderService orderService;

	public static void main(String[] args) {SpringApplication.run(DubboClientApplication.class).close();}

	@Bean
	public ApplicationRunner runner() {
		return args -> {
			// try confirm
//			orderService.orderPay(2, BigDecimal.valueOf(100));

			// try cancel
			orderService.mockInventoryWithTryException(20,BigDecimal.valueOf(1000));

		};
	}

}
