package git.yampery.cryptic.dao;

import git.yampery.cryptic.pojo.TUser;

import java.util.List;
import java.util.Map;

/**
 * @decription UserDao
 * <p>用户数据库表访问</p>
 * @author Yampery
 * @date 2018/4/4 13:54
 */
public interface UserDao {

    int insert(TUser record);

    int insertSelective(TUser record);

    List<TUser> queryList(Map<String, Object> map);
}
