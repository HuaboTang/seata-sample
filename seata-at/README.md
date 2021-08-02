# Seat AT模式示例

## 示例运行说明

### 环境准备

1. 运行项目前先参考运行seata-server，[Simple说明（含seata-server运行）](../)
2. 准备mysql数据库，修改3个模块下application.yml中数据库连接，指向该库
3. 在数据库中执行seata的client初始化脚本：[client-at.db.mysql.sql](https://github.com/seata/seata/blob/develop/script/client/at/db/mysql.sql)


### 启动SpringBoot应用

本示例模拟下单逻辑，基于spring cloud 实现服务调用

- seata-at-order 订单服务，生成订单操作
- seata-at-account 用户账户服务，操作用户账户余额
- seata-at-storage 库存服务，操作库存


### 调用seata-at.http 中定义的http请求，模拟操作