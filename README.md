Seata 示例项目

## 1. 示例项目运行说明

### 1.1 Seata服务端部署

1. 下载配置seata-server

[Seata-Server 下载地址](https://github-releases.githubusercontent.com/163387337/b3eee500-a5df-11eb-9378-8a64b25fd9ad?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAIWNJYAX4CSVEH53A%2F20210802%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20210802T071455Z&X-Amz-Expires=300&X-Amz-Signature=f2870f4aaa5729e2afc45f14d687194e2e762e797a14ed91b6bf84b189a55e88&X-Amz-SignedHeaders=host&actor_id=9294615&key_id=0&repo_id=163387337&response-content-disposition=attachment%3B%20filename%3Dseata-server-1.4.2.zip&response-content-type=application%2Foctet-stream)

2. 创建数据库，并执行脚本初始化

[脚本内容](https://github.com/seata/seata/blob/develop/script/server/db/mysql.sql)

3. 修改Seata-server配置

- seata-server-1.4.2/conf/registry.conf

```text
registry {
  # file 、nacos 、eureka、redis、zk、consul、etcd3、sofa
  # 设置为file
  type = "file"
  ...
  file {
    name = "file.conf"
  }
  ...
}
```

- seata-server-1.4.2/conf/file.conf

```text
## transaction log store, only used in seata-server
store {
  ## store mode: file、db、redis
  mode = "db"
  ...
  db {
   # 修改连接、用户名、密码指向数据库
  }
  ...
}
```

### 1.2 具体demo

[AT模式 seata-at](./seata-at)

[本地TCC模式 seata-tcc-transfer](./seata-tcc-transfer)

[远程TCC模式 seata-tcc-spring-cloud](./seata-tcc-spring-cloud)