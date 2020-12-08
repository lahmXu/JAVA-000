### 调试说明
1. 创建两个数据库名称为db,表名为tb_user的数据库实例
2. 本地启动访问地址http://localhost:8001/swagger-ui.html#!/，点击try it out!
3. insert接口示例数据
```sql
{
  "cardId": 15,
  "name": "222"
}
```

4. 控制台可以看到真实执行的SQL
```text
# 插入
Actual SQL: ds1 ::: insert into tb_user(card_id,name) values (?, ?) ::: [15, 222]

# 查询
Actual SQL: ds0 ::: select * from tb_user;
Actual SQL: ds1 ::: select * from tb_user;

```