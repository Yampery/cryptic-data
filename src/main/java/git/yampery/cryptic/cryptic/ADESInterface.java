package git.yampery.cryptic.cryptic;

/**
 * @decription ADESInterface
 * <p>自加解密接口</p>
 * @author Yampery
 * @date 2017/10/24 13:15
 */
public interface ADESInterface {
    public <T> T encryptSelf();
    public <T> T decryptSelf();
}
