FROM mysql/mysql-server:8.0.31

MAINTAINER lengleng(wangiegie@gmail.com)

ENV TZ=Asia/Shanghai

RUN ln -sf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

COPY ./learner.sql /docker-entrypoint-initdb.d

COPY ./learner_codegen.sql /docker-entrypoint-initdb.d

COPY ./learner_config.sql /docker-entrypoint-initdb.d

COPY ./learner_job.sql /docker-entrypoint-initdb.d
