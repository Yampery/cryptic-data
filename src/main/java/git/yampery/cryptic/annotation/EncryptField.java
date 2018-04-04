package git.yampery.cryptic.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @decription EncryptField
 * <p>字段加密注解</p>
 * @author Yampery
 * @date 2017/10/24 13:01
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EncryptField {
    String value() default "";
}
