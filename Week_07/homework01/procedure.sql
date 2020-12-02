# 创建存储过程
DELIMITER //
CREATE PROCEDURE insert_emps_test(IN loop_times INT)
BEGIN
    DECLARE var INT DEFAULT 0;
    WHILE var < loop_times DO
        insert into tb_order(user_id,amount,goods_id,discount_id,status) VALUES (0,var * 10,0,0,1);
        SET var = var + 1;
    END WHILE;
END
//
DELIMITER ;

# 执行结果104s
call insert_emps_test(1000000);