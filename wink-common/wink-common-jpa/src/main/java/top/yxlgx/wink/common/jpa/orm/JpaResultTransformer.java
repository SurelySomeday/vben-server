package top.yxlgx.wink.common.jpa.orm;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import lombok.SneakyThrows;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.hibernate.query.TupleTransformer;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yanxin
 * @Description: tuple转实体对象
 */
public class JpaResultTransformer implements TupleTransformer {

    private final Class<?> clazz;
    private final Map<String, Field> fieldMap;

    public JpaResultTransformer(Class<?> clazz) {
        this.clazz = clazz;
        List<Field> allFieldsList = FieldUtils.getAllFieldsList(clazz);
        Map<String, Field> fieldMap = new HashMap<>();
        for (Field field : allFieldsList) {
            fieldMap.put(field.getName(), field);
        }
        this.fieldMap = fieldMap;
    }

    @SneakyThrows
    @Override
    public Object transformTuple(Object[] tuple, String[] aliases) {
        Object object = clazz.getDeclaredConstructor().newInstance();
        for (int i = 0; i < tuple.length; i++) {
            String fieldName = "";
            if (aliases[i].contains("_")) {
                fieldName = StrUtil.toCamelCase(aliases[i].toLowerCase(), '_');
            } else {
                fieldName = aliases[i];
            }
            try {
                Field field = getField(fieldName);
                if (field != null && tuple[i] != null) {
                    //autoSetField(tuple[i], object, field);
                    BeanUtil.setFieldValue(object, field.getName(), tuple[i]);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        return object;
    }


    private Field getField(String name) {
        return fieldMap.getOrDefault(name, null);
    }
}
