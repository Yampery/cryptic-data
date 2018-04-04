package git.yampery.cryptic.service;

import git.yampery.cryptic.pojo.TUser;

import java.util.List;
import java.util.Map;

/**
 * @decription UserService
 * <p>用户服务</p>
 * @author Yampery
 * @date 2018/4/4 14:48
 */
public interface UserService {

    /**
     * 查询
     * @param map
     */
    List<TUser> queryList(Map<String, Object> map);

    /**
     * 写入数据
     * @param user
     */
    void insert(TUser user);
}
