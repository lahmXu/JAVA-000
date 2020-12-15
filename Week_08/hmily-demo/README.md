Week08 作业题目（周六）：
2.（必做）基于 hmily TCC 或 ShardingSphere 的 Atomikos XA 实现一个简单的分布式事务应用 demo（二选一），提交到 Github。


###### hmily-demo 

参考：[hmily-demo-springcloud](https://github.com/SpaceEmpire/hmily/tree/master/hmily-demo/hmily-demo-springcloud)

模拟用户下单，支付，减库存

* springcloud-eureka （服务注册中心）
* springcloud-account （账户服务）
* springcloud-inventory （库存服务）
* springcloud-order （订单服务）
* springcloud-common （数据库操作模块）


模拟下单支付-执行成功

```
curl -X POST \
  http://127.0.0.1:8090/order/orderPay \
  -H 'content-type: application/x-www-form-urlencoded' \
  -d 'count=1&amount=1'
```

```
2020-12-10 00:02:08.337  INFO 72413 --- [0.0-8090-exec-3] c.l.h.o.service.impl.OrderServiceImpl    : =============发起订单支付=============
Creating a new SqlSession
SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@6d252c83] was not registered for synchronization because synchronization is not active
JDBC Connection [HikariProxyConnection@1675808599 wrapping com.mysql.jdbc.JDBC4Connection@5ea776fe] will not be managed by Spring
==>  Preparing: insert into `order` (create_time,number,status,product_id,total_amount,count,user_id) values ( ?,?,?,?,?,?,?) 
==> Parameters: 2020-12-10 00:02:08.339(Timestamp), 4ab745ab-b065-4f17-b6c0-81ef655829cf(String), 1(Integer), 1(String), 1(BigDecimal), 1(Integer), 10000(String)
<==    Updates: 1
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@6d252c83]
Creating a new SqlSession
SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@13b7476b] was not registered for synchronization because synchronization is not active
JDBC Connection [HikariProxyConnection@517498929 wrapping com.mysql.jdbc.JDBC4Connection@5ea776fe] will not be managed by Spring
==>  Preparing: update `order` set status = ? where number = ? 
==> Parameters: 2(Integer), 4ab745ab-b065-4f17-b6c0-81ef655829cf(String)
<==    Updates: 1
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@13b7476b]
Creating a new SqlSession
SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@5fea0724] was not registered for synchronization because synchronization is not active
JDBC Connection [HikariProxyConnection@1540720641 wrapping com.mysql.jdbc.JDBC4Connection@5ea776fe] will not be managed by Spring
==>  Preparing: update `order` set status = ? where number = ? 
==> Parameters: 4(Integer), 4ab745ab-b065-4f17-b6c0-81ef655829cf(String)
<==    Updates: 1
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@5fea0724]
2020-12-10 00:02:09.496  INFO 72413 --- [ecutorHandler-6] c.l.h.o.service.impl.PaymentServiceImpl  : =========进行订单confirm操作完成================



2020-12-10 00:02:08.828  INFO 72467 --- [0.0-8885-exec-1] c.l.h.a.service.impl.AccountServiceImpl  : ============执行try付款接口===============
Creating a new SqlSession
SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@5e4fb38d] was not registered for synchronization because synchronization is not active
2020-12-10 00:02:08.878  INFO 72467 --- [0.0-8885-exec-1] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Starting...
2020-12-10 00:02:09.147  INFO 72467 --- [0.0-8885-exec-1] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Start completed.
JDBC Connection [HikariProxyConnection@1570318231 wrapping com.mysql.jdbc.JDBC4Connection@b916472] will not be managed by Spring
==>  Preparing: update account set balance = balance - ?,freeze_amount= freeze_amount + ? ,update_time = now() where user_id =? and balance > 0 
==> Parameters: 1(BigDecimal), 1(BigDecimal), 10000(String)
<==    Updates: 1
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@5e4fb38d]
2020-12-10 00:02:09.503  INFO 72467 --- [0.0-8885-exec-2] c.l.h.a.service.impl.AccountServiceImpl  : ============执行try付款接口===============
Creating a new SqlSession
SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@5e61bdc3] was not registered for synchronization because synchronization is not active
JDBC Connection [HikariProxyConnection@1353078911 wrapping com.mysql.jdbc.JDBC4Connection@b916472] will not be managed by Spring
==>  Preparing: update account set balance = balance - ?,freeze_amount= freeze_amount + ? ,update_time = now() where user_id =? and balance > 0 
==> Parameters: 1(BigDecimal), 1(BigDecimal), 10000(String)
<==    Updates: 1
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@5e61bdc3]



2020-12-10 00:02:09.432  INFO 72505 --- [0.0-8883-exec-1] c.l.h.i.s.impl.InventoryServiceImpl      : ==========执行try减库存方法===========
Creating a new SqlSession
SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@5f31625] was not registered for synchronization because synchronization is not active
2020-12-10 00:02:09.449  INFO 72505 --- [0.0-8883-exec-1] com.zaxxer.hikari.HikariDataSource       : HikariPool-2 - Starting...
2020-12-10 00:02:09.454  INFO 72505 --- [0.0-8883-exec-1] com.zaxxer.hikari.HikariDataSource       : HikariPool-2 - Start completed.
JDBC Connection [HikariProxyConnection@1533597180 wrapping com.mysql.jdbc.JDBC4Connection@598f1e53] will not be managed by Spring
==>  Preparing: update inventory set total_inventory = total_inventory - ?, lock_inventory= lock_inventory + ? where product_id =? and total_inventory > 0 
==> Parameters: 1(Integer), 1(Integer), 1(String)
<==    Updates: 1
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@5f31625]
2020-12-10 00:02:09.531  INFO 72505 --- [0.0-8883-exec-2] c.l.h.i.s.impl.InventoryServiceImpl      : ==========confirmMethod库存确认方法===========
Creating a new SqlSession
SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@7de13e55] was not registered for synchronization because synchronization is not active
JDBC Connection [HikariProxyConnection@1204408146 wrapping com.mysql.jdbc.JDBC4Connection@598f1e53] will not be managed by Spring
==>  Preparing: update inventory set lock_inventory = lock_inventory - ? where product_id =? and lock_inventory > 0 
==> Parameters: 1(Integer), 1(String)
<==    Updates: 1
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@7de13e55]
```

模拟减库存异常情况

```
curl -X POST \
  http://127.0.0.1:8090/order/mockInventoryWithTryException \
  -H 'content-type: application/x-www-form-urlencoded' \
  -d 'count=1&amount=1'
```

```
2020-12-10 00:35:14.984  INFO 74597 --- [0.0-8090-exec-1] c.l.h.o.service.impl.OrderServiceImpl    : =============发起订单支付=============
Creating a new SqlSession
==>  Preparing: insert into `order` (create_time,number,status,product_id,total_amount,count,user_id) values ( ?,?,?,?,?,?,?) 
==> Parameters: 2020-12-10 00:35:14.984(Timestamp), 6fe46a8e-17c8-4425-a07f-af82aafaa053(String), 1(Integer), 1(String), 1(BigDecimal), 1(Integer), 10000(String)
<==    Updates: 1
JDBC Connection [HikariProxyConnection@1620910274 wrapping com.mysql.jdbc.JDBC4Connection@25eb5ef2] will not be managed by Spring
==>  Preparing: update `order` set status = ? where number = ? 
==> Parameters: 2(Integer), 6fe46a8e-17c8-4425-a07f-af82aafaa053(String)
<==    Updates: 1
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@51868c7a]
2020-12-10 00:35:16.098 ERROR 74597 --- [0.0-8090-exec-1] o.d.h.s.feign.HmilyFeignHandler          : HmilyFeignHandler invoker exception :
2020-12-10 00:35:16.098 ERROR 74597 --- [0.0-8090-exec-1] o.d.h.s.feign.HmilyFeignHandler          : HmilyFeignHandler invoker exception :
feign.FeignException$InternalServerError: [500] during [GET] to [http://inventory-service/inventory-service/inventory/mockWithTryException] [InventoryClient#mockWithTryException(InventoryDTO)]: [{"timestamp":"2020-12-09T16:35:16.084+00:00","status":500,"error":"Internal Server Error","message":"","path":"/inventory-service/inventory/mockWithTryException"}]
==>  Preparing: update `order` set status = ? where number = ? 
==> Parameters: 3(Integer), 6fe46a8e-17c8-4425-a07f-af82aafaa053(String)
<==    Updates: 1
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@451a49dd]
2020-12-10 00:35:16.142  INFO 74597 --- [ecutorHandler-5] c.l.h.o.service.impl.PaymentServiceImpl  : =========进行订单cancel操作完成================
2020-12-10 00:35:16.144 ERROR 74597 --- [0.0-8090-exec-1] o.a.c.c.C.[.[.[/].[dispatcherServlet]    : Servlet.service() for servlet [dispatcherServlet] in context with path [] threw exception [Request processing failed; nested exception is feign.FeignException$InternalServerError: [500] during [GET] to [http://inventory-service/inventory-service/inventory/mockWithTryException] [InventoryClient#mockWithTryException(InventoryDTO)]: [{"timestamp":"2020-12-09T16:35:16.084+00:00","status":500,"error":"Internal Server Error","message":"","path":"/inventory-service/inventory/mockWithTryException"}]] with root cause
feign.FeignException$InternalServerError: [500] during [GET] to [http://inventory-service/inventory-service/inventory/mockWithTryException] [InventoryClient#mockWithTryException(InventoryDTO)]: [{"timestamp":"2020-12-09T16:35:16.084+00:00","status":500,"error":"Internal Server Error","message":"","path":"/inventory-service/inventory/mockWithTryException"}]


2020-12-10 00:35:16.154  INFO 74418 --- [0.0-8885-exec-5] c.l.h.a.service.impl.AccountServiceImpl  : ============执行try付款接口===============
Creating a new SqlSession
SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@487b73bf] was not registered for synchronization because synchronization is not active
JDBC Connection [HikariProxyConnection@1186759872 wrapping com.mysql.jdbc.JDBC4Connection@6b1e0ebb] will not be managed by Spring
==>  Preparing: update account set balance = balance - ?,freeze_amount= freeze_amount + ? ,update_time = now() where user_id =? and balance > 0 
==> Parameters: 1(BigDecimal), 1(BigDecimal), 10000(String)
<==    Updates: 1


2020-12-10 00:35:16.077 ERROR 74141 --- [0.0-8883-exec-3] o.a.c.c.C.[.[.[.[dispatcherServlet]      : Servlet.service() for servlet [dispatcherServlet] in context with path [/inventory-service] threw exception [Request processing failed; nested exception is org.dromara.hmily.common.exception.HmilyRuntimeException: 库存扣减异常！] with root cause
org.dromara.hmily.common.exception.HmilyRuntimeException: 库存扣减异常！
```
