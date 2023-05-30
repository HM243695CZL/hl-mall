package cn.hl.common.filter;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Field;

/**
 * 动态自定义查询条件工具类
 */
public class FilterUtil {

    /**
     * 拼接查询条件
     * @param queryWrapper 条件对象
     * @param obj 数据试题
     */
    public static void convertQuery(QueryWrapper queryWrapper, Object obj) throws IllegalAccessException {
        Class clazz = obj.getClass();
        // 反射遍历属性
        for (Field field : clazz.getDeclaredFields()) {
            // 抑制java对修饰符的检查
            field.setAccessible(true);
            // 获取属性值
            Object fieldValue = field.get(obj);
            // 如果是null则跳过
            if (ObjectUtils.isEmpty(fieldValue)) {
                continue;
            }
            // 判断是不是空字符串
            if (fieldValue instanceof String) {
                if (StringUtils.isEmpty(fieldValue.toString().trim())) {
                    continue;
                }
            }
            // 查询注解
            FilterQuery filterQuery = AnnotationUtils.getAnnotation(field, FilterQuery.class);
            if (ObjectUtils.isEmpty(filterQuery)) {
                continue;
            }
            // 获取字段名
            String fieldName = filterQuery.field();
            // 获取枚举
            FilterEnum filterEnum = filterQuery.filterEnum();
            // 拼接查询条件
            switch (filterEnum) {
                case EQ:
                    queryWrapper.eq(fieldName, fieldValue);
                    break;
                case LIKE:
                    queryWrapper.like(fieldName, fieldValue);
                    break;
                case GT:
                    queryWrapper.gt(fieldName, fieldValue);
                    break;
                case LT:
                    queryWrapper.lt(fieldName, fieldValue);
                    break;
                default:
                    break;
            }
        }
    }
}
