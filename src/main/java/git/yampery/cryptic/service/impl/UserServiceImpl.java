package git.yampery.cryptic.service.impl;

import git.yampery.cryptic.common.SysException;
import git.yampery.cryptic.dao.UserDao;
import git.yampery.cryptic.pojo.TUser;
import git.yampery.cryptic.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @decription UserServiceImpl
 * <p></p>
 * @author Yampery
 * @date 2018/4/4 14:50
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource private UserDao userDao;

    @Override
    public List<TUser> queryList(Map<String, Object> map) {
        try {
            return userDao.queryList(map);
        } catch (Exception e) {
            throw new SysException("获取用户列表出错", e);
        }
    }

    @Override
    public void insert(TUser user) {
        try {
            userDao.insert(user);
        } catch (Exception e) {
            throw new SysException("写入用户列表出错", e);
        }
    }
}
