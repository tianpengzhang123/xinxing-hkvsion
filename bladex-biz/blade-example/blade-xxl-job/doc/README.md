## 分布式任务调度服务启动
### XXL-JOB
1. 执行/doc/sql文件夹下的sql脚本

2. 拉取docker镜像

   ```shell
   docker pull xuxueli/xxl-job-admin:2.4.0
   ```

3. 执行docker命令运行服务
  ```shell
  docker run -d  --add-host="host.docker.internal:host-gateway" \
    -p 8080:8080 \
    --restart=always \
    --name xxl-job \
    -e TZ="Asia/Shanghai" \
    -e JAVA_OPTS="-Xms512M -Xmx512m" \
    -e PARAMS="--spring.datasource.url=jdbc:mysql://host.docker.internal:3306/xxl_job?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&serverTimezone=Asia/Shanghai --spring.datasource.username=root --spring.datasource.password=root" \
    -v ~/docker/xxl-job:/data/applogs \
    xuxueli/xxl-job-admin:2.4.0
  ```