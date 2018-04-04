package git.yampery.cryptic.cryptic;

import git.yampery.cryptic.annotation.DecryptField;
import git.yampery.cryptic.annotation.EncryptField;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Field;

/**
 * @decription CryptPojoUtils
 * <p>对象加解密工具
 * 其子类可以通过调用<tt>encrypt(T t)</tt>方法实现自加密，返回参数类型；
 * 调用<tt>decrypt(T t)</tt>实现自解密，返回参数类型；
 * <tt>encrypt</tt>对注解{@link EncryptField}字段有效；
 * <tt>decrypt</tt>对注解{@link DecryptField}字段有效。</p>
 * @author Yampery
 * @date 2017/10/24 13:36
 */
public class CryptPojoUtils {

    /**
     * 对对象t加密
     * @param t
     * @param <T>
     * @return
     */
    public static <T> T encrypt(T t) {
        Field[] declaredFields = t.getClass().getDeclaredFields();
        try {
            if (declaredFields != null && declaredFields.length > 0) {
                for (Field field : declaredFields) {
                    if (field.isAnnotationPresent(EncryptField.class) && field.getType().toString().endsWith("String")) {
                        field.setAccessible(true);
                        String fieldValue = (String) field.get(t);
                        if (StringUtils.isNotEmpty(fieldValue)) {
                            field.set(t, ADESUtils.getInstance().encrypt(fieldValue));
                        }
                        field.setAccessible(false);
                    }
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return t;
    }

    /**
     * 对象解密
     * @param t
     * @param <T>
     * @return
     */
    public static <T> T decrypt(T t) {
        Field[] declaredFields = t.getClass().getDeclaredFields();
        try {
            if (declaredFields != null && declaredFields.length > 0) {
                for (Field field : declaredFields) {
                    if (field.isAnnotationPresent(DecryptField.class) && field.getType().toString().endsWith("String")) {
                        field.setAccessible(true);
                        String fieldValue = (String)field.get(t);
                        if(StringUtils.isNotEmpty(fieldValue)) {
                            field.set(t, ADESUtils.getInstance().decrypt(fieldValue));
                        }
                    }
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return t;
    }

    /**
     * 对含注解字段解密
     * @param t
     * @param <T>
     */
    public static <T> void decryptField(T t) {
        Field[] declaredFields = t.getClass().getDeclaredFields();
        try {
            if (declaredFields != null && declaredFields.length > 0) {
                for (Field field : declaredFields) {
                    if (field.isAnnotationPresent(DecryptField.class) && field.getType().toString().endsWith("String")) {
                        field.setAccessible(true);
                        String fieldValue = (String)field.get(t);
                        if(StringUtils.isNotEmpty(fieldValue)) {
                            field.set(t, ADESUtils.getInstance().decrypt(fieldValue));
                        }
                    }
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        // return t;
    }

    /**
     * 对含注解字段加密
     * @param t
     * @param <T>
     */
    public static <T> void encryptField(T t) {
        Field[] declaredFields = t.getClass().getDeclaredFields();
        try {
            if (declaredFields != null && declaredFields.length > 0) {
                for (Field field : declaredFields) {
                    if (field.isAnnotationPresent(EncryptField.class) && field.getType().toString().endsWith("String")) {
                        field.setAccessible(true);
                        String fieldValue = (String)field.get(t);
                        if(StringUtils.isNotEmpty(fieldValue)) {
                            field.set(t, ADESUtils.getInstance().encrypt(fieldValue));
                        }
                    }
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 隐藏号码中间4位
     * @param t
     * @param <T>
     */
    public static <T> void hidePhone(T t) {
        Field[] declaredFields = t.getClass().getDeclaredFields();
        try {
            if (declaredFields != null && declaredFields.length > 0) {
                for (Field field : declaredFields) {
                    if (field.isAnnotationPresent(DecryptField.class) && field.getType().toString().endsWith("String")) {
                        field.setAccessible(true);
                        String fieldValue = (String)field.get(t);
                        if(StringUtils.isNotEmpty(fieldValue)) {
                            // 暂时与解密注解共用一个注解，该注解隐藏手机号中间四位
                            field.set(t, StringUtils.overlay(fieldValue, "****", 3, 7));
                        }
                    }
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
