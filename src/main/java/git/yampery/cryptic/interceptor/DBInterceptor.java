package git.yampery.cryptic.interceptor;

import git.yampery.cryptic.annotation.DecryptField;
import git.yampery.cryptic.cryptic.CryptPojoUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @decription DBInterceptor
 * <p>实现Mybatis拦截器，用于拦截修改，插入和返回需要加密或者解密的对象</p>
 * @author Yampery
 * @date 2018/4/4 14:17
 */
@Intercepts({
        @Signature(type=Executor.class,method="update",args={MappedStatement.class,Object.class}),
        @Signature(type=Executor.class,method="query",args={MappedStatement.class,Object.class,RowBounds.class,ResultHandler.class})
})
@Component
public class DBInterceptor implements Interceptor {
    private final Logger logger = LoggerFactory.getLogger(DBInterceptor.class);
    @Value("${sys.aes.switch}") private String CRYPTIC_SWITCH;
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement statement = (MappedStatement) invocation.getArgs()[0];
        String methodName = invocation.getMethod().getName();
        Object parameter = invocation.getArgs()[1];
        BoundSql sql = statement.getBoundSql(parameter);
        logger.info("sql is: {}", sql.getSql());

        /**
         * @TODO 处理查询
         */
        if (StringUtils.equalsIgnoreCase("query", methodName)) {
            /**
             * 在这里可以处理查询参数，如传递的参数为明文，要按照密文查询
             * 本文选择使用同一参数封装处理方案{@link git.yampery.cryptic.common.QueryParams}
             */
        }
        /**
         * 拦截批量插入操作不仅繁琐，而且为了通用逐一通过反射加密不妥
         * 如果有批量操作，最好在传递参数之前，向list中添加之前就加密
         */
        if (!"0".equals(CRYPTIC_SWITCH)) {
            if (StringUtils.equalsIgnoreCase("update", methodName)
                    || StringUtils.equalsIgnoreCase("insert", methodName)) {
                CryptPojoUtils.encryptField(parameter);
            }
        }

        Object returnValue = invocation.proceed();

        try {
            if (!"0".equals(CRYPTIC_SWITCH)) {
                if (returnValue instanceof ArrayList<?>) {
                    List<?> list = (ArrayList<?>) returnValue;
                    if (null == list || 1 > list.size())
                        return returnValue;
                    Object obj = list.get(0);
                    if (null == obj)  // 这里虽然list不是空，但是返回字符串等有可能为空
                        return returnValue;
                    // 判断第一个对象是否有DecryptField注解
                    Field[] fields = obj.getClass().getDeclaredFields();
                    int len;
                    if (null != fields && 0 < (len = fields.length)) {
                        // 标记是否有解密注解
                        boolean isD = false;
                        for (int i = 0; i < len; i++) {
                            /**
                             * 由于返回的是同一种类型列表，因此这里判断出来之后可以保存field的名称
                             * 之后处理所有对象直接按照field名称查找Field从而改之即可
                             * 有可能该类存在多个注解字段，所以需要保存到数组（项目中目前最多是2个）
                             * @TODO 保存带DecryptField注解的字段名称到数组，按照名称获取字段并解密
                             * */
                            if (fields[i].isAnnotationPresent(DecryptField.class)) {
                                isD = true;
                                break;
                            }
                        } /// for end ~
                        if (isD)  // 将含有DecryptField注解的字段解密
                            list.forEach(l -> CryptPojoUtils.decryptField(l));
                    } /// if end ~
                } /// if end ~
            }

        } catch (Exception e) {
            // 打印异常，由于拦截器本身抛出异常，比如拦截到很奇葩的返回，应算正常
            // 直接返回原结果即可
            logger.info("抛出异常，正常返回==> " + e.getMessage());
            e.printStackTrace();
            return returnValue;
        }
        return returnValue;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        // TODO Auto-generated method stub
    }
}
