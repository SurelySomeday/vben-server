package top.yxlgx.wink.config.orm.jpa;


import lombok.SneakyThrows;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.text.CaseUtils;
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

public class JpaResultTransformer implements TupleTransformer {

    private Class<?> clazz;
    private Map<String, Field> fieldMap;

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
        Object object = clazz.newInstance();
        for (int i = 0; i < tuple.length; i++) {
            String fieldName = "";
            if (aliases[i].contains("_")) {
                fieldName = CaseUtils.toCamelCase(aliases[i].toLowerCase(), false, '_');
            } else {
                fieldName = aliases[i];
            }
            try {
                Field field = getField(fieldName);
                if (field != null && tuple[i] != null) {
                    autoSetField(tuple[i], object, field);
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

    /**
     * 自动配置字段值
     *
     * @param tuple
     * @param target
     * @param field
     * @throws IllegalAccessException
     */
    private void autoSetField(Object tuple, Object target, Field field) throws IllegalAccessException {
        if (field == null || tuple == null) {
            return;
        }
        field.setAccessible(true);
        //常见类型处理
        if (field.getType() == String.class) {
            field.set(target, tuple.toString());
        } else if (field.getType() == Integer.class) {
            field.set(target, Integer.parseInt(tuple.toString()));
        } else if (field.getType() == Long.class) {
            field.set(target, Long.parseLong(tuple.toString()));
        } else if (field.getType() == Double.class) {
            field.set(target, Double.parseDouble(tuple.toString()));
        } else if (field.getType() == Float.class) {
            field.set(target, Float.parseFloat(tuple.toString()));
        } else {
            if (field.getType() == Date.class) {
                if (tuple instanceof Timestamp) {
                    long time = ((Timestamp) tuple).getTime();
                    field.set(target, new Date(time));
                }
            } else if (field.getType() == LocalDate.class) {
                if (tuple instanceof Timestamp) {
                    long time = ((Timestamp) tuple).getTime();
                    field.set(target, LocalDate.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault()));
                }
            } else if (field.getType() == LocalDateTime.class) {
                if (tuple instanceof Timestamp) {
                    long time = ((Timestamp) tuple).getTime();
                    field.set(target, LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault()));
                }
            }

        }


    }
}
