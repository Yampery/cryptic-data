package git.yampery.cryptic.cryptic;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @decription ADESUtils
 * <p>字段加解密，使用MySql AES算法</p>
 * @author Yampery
 * @date 2018/4/4 13:10
 */
@Component
public class ADESUtils {
    private static final String ENCRYPT_TYPE = "AES";
    private static final String ENCODING = "UTF-8";

    // 密盐
    private static String aesSalt;
    private static ADESUtils adesUtils;
    private static Cipher encryptCipher;    // 加密cipher
    private static Cipher decryptChipher;   // 解密chipher

    // 加解密开关，从配置获取
    private static String CRYPTIC_SWITCH;
    /**
     * 从配置中获取秘钥
     * :默认值填写自己生成的秘钥
     * @param key
     */
    @Value("${sys.aes.salt:0}")
    public void setAESSalt(String key){
        ADESUtils.aesSalt = key;
    }

    /**
     * 获取开关
     * 默认为不加密
     * @param val
     */
    @Value("${sys.aes.switch:0}")
    public void setCrypticSwitch(String val) {
        ADESUtils.CRYPTIC_SWITCH = val;
    }
    /**
     * encryptCipher、decryptChipher初始化
     */
    public static void init(){
        try {
            encryptCipher = Cipher.getInstance(ENCRYPT_TYPE);
            decryptChipher = Cipher.getInstance(ENCRYPT_TYPE);
            encryptCipher.init(Cipher.ENCRYPT_MODE, generateMySQLAESKey(aesSalt));
            decryptChipher.init(Cipher.DECRYPT_MODE, generateMySQLAESKey(aesSalt));
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (NoSuchPaddingException e) {
            throw new RuntimeException(e);
        }
    }

    private ADESUtils() {  }

    /**
     * 获取单例
     * @return
     */
    public static ADESUtils getInstance(){
        if(adesUtils == null){
            // 当需要创建的时候在加锁
            synchronized(ADESUtils.class) {
                if (adesUtils == null) {
                    adesUtils = new ADESUtils();
                    init();
                }
            }
        }
        return adesUtils;
    }

    /**
     * 对明文加密
     * @param pString
     * @return
     */
    public String encrypt(String pString) {

        if (StringUtils.isBlank(pString) || StringUtils.equals("0", CRYPTIC_SWITCH))
            return StringUtils.trimToEmpty(pString);
        try{
            return new String(Hex.encodeHex(encryptCipher.doFinal(pString.getBytes(ENCODING)))).toUpperCase();
        } catch (Exception e) {
            e.printStackTrace();
            return pString;
        }
    }

    /**
     * 对密文解密
     * @param eString
     * @return
     */
    public String decrypt(String eString) {
        if (StringUtils.isBlank(eString) || StringUtils.equals("0", CRYPTIC_SWITCH))
            return StringUtils.trimToEmpty(eString);
        try {
            return new String(decryptChipher.doFinal(Hex.decodeHex(eString.toCharArray())));
        } catch (Exception e) {
            e.printStackTrace();
            return eString;
        }
    }
    /**
     * 产生mysql-aes_encrypt
     * @param key 加密的密盐
     * @return
     */
    public static SecretKeySpec generateMySQLAESKey(final String key) {
        try {
            final byte[] finalKey = new byte[16];
            int i = 0;
            for(byte b : Hex.decodeHex(key.toCharArray()))
                finalKey[i++ % 16] ^= b;
            return new SecretKeySpec(finalKey, "AES");
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 生成秘钥(128位)
     * @return
     * @throws Exception
     */
    public static String generateAESKey() throws Exception{
        //实例化
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        //设置密钥长度
        kgen.init(128);
        //生成密钥
        SecretKey skey = kgen.generateKey();
        // 转为16进制字串
        String key = new String(Hex.encodeHex(skey.getEncoded()));
        //返回密钥的16进制字串
        return key.toUpperCase();
    }
}
