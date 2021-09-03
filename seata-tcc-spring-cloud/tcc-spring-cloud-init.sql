drop table if exists seata.tcc_storage;
create table if not exists `seata`.`tcc_storage` (
    id int not null primary key auto_increment,
    goods_id int not null,
    stock int not null default 0,
    freeze_stock int not null default 0
);
alter table seata.tcc_storage add unique index (goods_id);

insert into seata.tcc_storage (goods_id, stock, freeze_stock) values (200, 1000, 0)
on duplicate key update goods_id = 200;

create table if not exists `seata`.`tcc_account` (
    id int not null primary key auto_increment,
    account_user_id int not null,
    amount int not null default 0,
    freeze_amount int not null default 0
);
alter table seata.tcc_account add unique index (account_user_id);

insert into seata.tcc_account (account_user_id, amount, freeze_amount) values (100, 10000, 0)
on duplicate key update account_user_id = 100;

create table if not exists `seata`.`tcc_order` (
    id int not null auto_increment primary key,
    goods_id int not null,
    amount int not null default 0,
    create_time timestamp not null default now(),
    status int not null default 1
);