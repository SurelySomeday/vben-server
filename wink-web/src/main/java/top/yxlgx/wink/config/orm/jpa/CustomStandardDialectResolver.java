package top.yxlgx.wink.config.orm.jpa;

import org.hibernate.dialect.Database;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.jdbc.dialect.spi.DialectResolutionInfo;
import org.hibernate.engine.jdbc.dialect.spi.DialectResolver;
import org.springframework.cglib.proxy.Enhancer;

public class CustomStandardDialectResolver implements DialectResolver {
    @Override
    public Dialect resolveDialect(DialectResolutionInfo info) {
        for ( Database database : Database.values() ) {
            if (database.matchesResolutionInfo(info)) {
                Dialect dialect = database.createDialect(info);
                if (dialect != null) {
                    DialectInterceptor dialectInterceptor = new DialectInterceptor();
                    Enhancer enhancer = new Enhancer();
                    enhancer.setSuperclass(dialect.getClass());  // 设置超类，cglib是通过继承来实现的
                    enhancer.setCallback(dialectInterceptor);
                    return (Dialect) enhancer.create();
                }
            }
        }

        return null;
    }
}
