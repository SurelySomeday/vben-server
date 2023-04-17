package top.yxlgx.wink.config.orm.jpa;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.metamodel.ManagedType;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.boot.spi.MetadataImplementor;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.hbm2ddl.SchemaUpdate;
import org.hibernate.tool.schema.TargetType;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.orm.jpa.JpaBaseConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.core.convert.support.GenericConversionService;
import top.yxlgx.wink.config.orm.jpa.convert.JpaStandardConverter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

@AutoConfigureBefore(JpaBaseConfiguration.class)
@Configuration
public class JpaConfig {
    @PersistenceContext
    EntityManager entityManager;

    @PostConstruct
    public void init() {
        Map<String, Object> properties = entityManager.getEntityManagerFactory().getProperties();
        StandardServiceRegistry standardServiceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(properties)
                .build();
        MetadataSources metadata = new MetadataSources(standardServiceRegistry);
        Set<ManagedType<?>> managedTypes = entityManager.getMetamodel().getManagedTypes();
        for (ManagedType<?> managedType : managedTypes) {
            Class<?> javaType = managedType.getJavaType();
            metadata.addAnnotatedClass(javaType);
        }
        generateUpdate(metadata);
        generateCreateAndDrop(metadata);


        //注册对象转换器
        GenericConversionService genericConversionService = ((GenericConversionService) DefaultConversionService.getSharedInstance());
        genericConversionService.addConverter(new JpaStandardConverter());

    }

    private void generateUpdate(MetadataSources metadata) {
        MetadataImplementor metadataImplementor = (MetadataImplementor) metadata.getMetadataBuilder().build();
        SchemaExport schemaExport = new SchemaExport();
        String outputFile = "create.sql";
        truncFile(outputFile);
        schemaExport.setOutputFile(outputFile);
        schemaExport.setDelimiter(";");
        schemaExport.execute(EnumSet.of(TargetType.SCRIPT), SchemaExport.Action.BOTH, metadataImplementor);
    }

    private void generateCreateAndDrop(MetadataSources metadata) {
        MetadataImplementor metadataImplementor = (MetadataImplementor) metadata.getMetadataBuilder().build();
        SchemaUpdate schemaExport=new SchemaUpdate();
        String outputFile = "update.sql";
        truncFile(outputFile);
        schemaExport.setOutputFile(outputFile);
        schemaExport.setDelimiter(";");
        schemaExport.execute(EnumSet.of(TargetType.SCRIPT), metadataImplementor);
    }

    private void truncFile(String outputFile) {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(outputFile);
            fileWriter.write("");
            fileWriter.flush();
            fileWriter.close();
        } catch (Exception ignore) {
        } finally {
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
