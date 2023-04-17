package top.yxlgx.wink.config.orm.jpa;

import org.hibernate.boot.Metadata;
import org.hibernate.boot.model.relational.SqlStringGenerationContext;
import org.hibernate.dialect.Dialect;
import org.hibernate.mapping.ForeignKey;
import org.hibernate.tool.schema.internal.StandardForeignKeyExporter;
import org.hibernate.tool.schema.spi.Exporter;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class DialectInterceptor implements MethodInterceptor {
    @Override
    public Object intercept(Object object, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {

        //新写法，直接返回Exporter.NO_COMMANDS
        if(method.getName().equals("getForeignKeyExporter")){
            return new StandardForeignKeyExporter((Dialect) object){
                @Override
                public String[] getSqlCreateStrings(ForeignKey foreignKey, Metadata metadata, SqlStringGenerationContext context) {
                    return Exporter.NO_COMMANDS;
                }

                @Override
                public String[] getSqlDropStrings(ForeignKey foreignKey, Metadata metadata, SqlStringGenerationContext context) {
                    return Exporter.NO_COMMANDS;
                }
            };
        }
        return methodProxy.invokeSuper(object, objects);
    }
}


