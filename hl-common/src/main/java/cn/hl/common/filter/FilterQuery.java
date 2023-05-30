package cn.hl.common.filter;

import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Indexed
public @interface FilterQuery {

    /**
     * 字段名
     */
    String field() default "";

    /**
     * 字段查询类型
     */
    FilterEnum filterEnum() default FilterEnum.LIKE;
}
