package git.yampery.cryptic.cryptic;

import git.yampery.cryptic.annotation.DecryptField;
import git.yampery.cryptic.annotation.EncryptField;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Field;

/**
 * @decription CryptPojo
 * <p>该类是加密对象。需要特殊对一个对象处理，可以采用安全克隆加密方法
 * 其子类可以通过调用<tt>encryptSelf()</tt>方法实现自加密，返回子类对象；
 * 调用<tt>decryptSelf()</tt>实现自解密，返回解密后的子类对象；
 * 上述加密方法<tt>encryptSelf</tt>对注解{@link EncryptField}字段有效；
 * 上述解密方法<tt>decryptSelf</tt>对注解{@link DecryptField}字段有效。</p>
 * @author Yampery
 * @date 2018/4/4 13:36
 */
public class CryptPojo implements Cloneable, ADESInterface {

    /**
     * 拷贝一个对象，并对新对象进行加密
     * 该方法主要用在日志打印上，可防止原对象被加密而影响程序执行
     * @param <T>
     * @return
     */
    public <T extends CryptPojo> T cloneAndEncrypt() {
        T cloneT = null;
        try {
            cloneT = (T) this.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
        if(cloneT !=null)
            return cloneT.encryptSelf();
        throw new RuntimeException("拷贝对象异常");
    }
    /**
     * 重写clone方法
     * @return
     * @throws CloneNotSupportedException
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    @Override
    public <T> T encryptSelf() {
        Field[] declaredFields = this.getClass().getDeclaredFields();
        try {
            if (declaredFields != null && declaredFields.length > 0) {
                for (Field field : declaredFields) {
                    if (field.isAnnotationPresent(EncryptField.class) && field.getType().toString().endsWith("String")) {
                        field.setAccessible(true);
                        String fieldValue = (String) field.get(this);
                        if (StringUtils.isNotEmpty(fieldValue)) {
                            field.set(this, ADESUtils.getInstance().encrypt(fieldValue));
                        }
                        field.setAccessible(false);
                    }
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return (T) this;
    }

    @Override
    public <T> T decryptSelf() {
        Field[] declaredFields = this.getClass().getDeclaredFields();
        try {
            if (declaredFields != null && declaredFields.length > 0) {
                for (Field field : declaredFields) {
                    if (field.isAnnotationPresent(DecryptField.class) && field.getType().toString().endsWith("String")) {
                        field.setAccessible(true);
                        String fieldValue = (String)field.get(this);
                        if(StringUtils.isNotEmpty(fieldValue)) {
                            field.set(this, ADESUtils.getInstance().decrypt(fieldValue));
                        }
                    }
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return (T) this;
    }
}
