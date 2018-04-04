package git.yampery.cryptic.pojo;

import git.yampery.cryptic.annotation.DecryptField;
import git.yampery.cryptic.annotation.EncryptField;

/**
 * @decription TUser
 * <p>用户pojo</p>
 * @author Yampery
 * @date 2018/4/4 14:00
 */
public class TUser {
    private Integer id;

    private String userName;

    @EncryptField
    @DecryptField
    private String phone;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}