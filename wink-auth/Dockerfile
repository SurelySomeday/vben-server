FROM amazoncorretto:17.0.9

RUN mkdir -p /wink-auth

WORKDIR /wink-auth

ARG JAR_FILE=target/wink-auth.jar

COPY ${JAR_FILE} app.jar

EXPOSE 3000

ENV TZ=Asia/Shanghai JAVA_OPTS="-Xms128m -Xmx256m -Djava.security.egd=file:/dev/./urandom"

CMD sleep 60; java $JAVA_OPTS -jar app.jar
