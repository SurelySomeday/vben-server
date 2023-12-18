package top.yxlgx.wink.common.jpa.orm.convert;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.ConditionalGenericConverter;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author yanxin
 * @Description: spring转换器，用于JPA查询结果转实体对象，只适用于Repository中@Query(value = "",nativeQuery = true)
 */
public class JpaStandardConverter implements ConditionalGenericConverter {


    @Override
    public boolean matches(TypeDescriptor source, TypeDescriptor target) {
        return source.isMap();
    }

    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        Set<ConvertiblePair> convertibleTypes = new LinkedHashSet<>(1);
        convertibleTypes.add(new ConvertiblePair(Map.class, Object.class));
        return convertibleTypes;
    }

    @Override
    public Object convert(Object o, TypeDescriptor source, TypeDescriptor target) {
        try {
            Object o3 = target.getObjectType().getDeclaredConstructor().newInstance();
            if (o instanceof Map) {
                return copyMapToObj((Map<String, Object>) o, o3);
            } else {
                return o;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return o;
    }

    /**
     * 将map中的值copy到bean中对应的字段上,使用驼峰命名
     *
     * @param map
     * @param target
     * @return
     * @author bazhandao
     * @date 2020-03-26
     */
    private Object copyMapToObj(Map<String, Object> map, Object target) {
        if (map == null || target == null || map.isEmpty()) {
            return target;
        }
        Class<?> actualEditable = target.getClass();
        PropertyDescriptor[] targetPds = BeanUtils.getPropertyDescriptors(actualEditable);
        for (PropertyDescriptor targetPd : targetPds) {
            if (targetPd.getWriteMethod() == null) {
                continue;
            }
            try {
                String key = targetPd.getName();
                String toUnderlineCaseKey = StrUtil.toUnderlineCase(key);
                Object value = null;
                //驼峰转下划线
                if(map.containsKey(toUnderlineCaseKey)){
                    value = map.get(toUnderlineCaseKey);
                }
                if (value == null) {
                    continue;
                }
                Method writeMethod = targetPd.getWriteMethod();
                if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                    writeMethod.setAccessible(true);
                }
                Parameter[] parameters = writeMethod.getParameters();
                Class<?> type = parameters[0].getType();
                BeanUtil.setFieldValue(target, key, value);
            } catch (Exception ex) {
                //throw new FatalBeanException("Could not copy properties from source to target", ex);
                ex.printStackTrace();
            }
        }
        return target;
    }


}
