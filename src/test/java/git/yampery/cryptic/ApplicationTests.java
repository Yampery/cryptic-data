package git.yampery.cryptic;

import git.yampery.cryptic.common.QueryParams;
import git.yampery.cryptic.common.SysException;
import git.yampery.cryptic.cryptic.ADESUtils;
import git.yampery.cryptic.pojo.TUser;
import git.yampery.cryptic.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

	@Resource private UserService userService;
	private static final Logger logger = LoggerFactory.getLogger(ApplicationTests.class);

	@Test
	public void contextLoads() {
	}

	/**
	 * 生成AES加密秘钥
	 */
	@Test
	public void testGeneratorSalt() throws Exception {
		String salt = ADESUtils.generateAESKey();
		System.out.println("秘钥: " + salt);
	}

	/**
	 * 测试写入数据
	 */
	@Test
	public void testInsert() {
		TUser user = new TUser();
		user.setPhone("18700000000");
		user.setUserName("yampery");
		try {
			userService.insert(user);
		} catch (SysException e) {
			System.err.println("insert user error");
			e.printStackTrace();
		}
	}

	/**
	 * 测试选择数据
	 */
	@Test
	public void testSelect() {
		try {
			List<TUser> users = userService.queryList(null);
			users.forEach(System.out::println);
		} catch (SysException e) {
			System.err.println("select user error");
			e.printStackTrace();
		}
	}

	/**
	 * 测试携参查询
	 */
	@Test
	public void testSelectByParams() {
		try {
			Map<String, Object> map = new HashMap<>();
			map.put("phone", "18700000000");
			// 封装参数
			QueryParams params = new QueryParams(map);
			List<TUser> users = userService.queryList(params);
			users.forEach(System.out::println);
		} catch (SysException e) {
			System.err.println("select user error");
			e.printStackTrace();
		}
	}

}
