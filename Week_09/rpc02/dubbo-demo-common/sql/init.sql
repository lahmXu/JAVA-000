create table tb_account
(
	id bigint auto_increment
		primary key,
	name varchar(10) default '' null comment '姓名'
)
comment '账户表';

create table tb_amount_dollar
(
	id bigint auto_increment
		primary key,
	account_id bigint default 0 null comment '账户id',
	amount decimal default 0 not null comment '账户余额'
)
comment '美元账户余额表';

create table tb_amount_rmb
(
	id bigint auto_increment
		primary key,
	account_id bigint default 0 null comment '账号id',
	amount decimal default 0 null comment '余额'
)
comment '人民币余额表';

