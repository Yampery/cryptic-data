package git.yampery.cryptic.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @decription DruidConfig
 * <p>Druid连接池配置</p>
 * @author Yampery
 * @date 2018/4/4 12:59
 */
@Configuration
@PropertySource("classpath:jdbc.properties")
public class DruidConfig {

    // private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 数据源
     * @return
     */
    @Bean("dataSource")
    @Primary
    public DataSource druidDataSource() {
        DruidDataSource datasource = new DruidDataSource();

        datasource.setUrl(this.dbUrl);
        datasource.setUsername(username);
        datasource.setPassword(password);
        datasource.setDriverClassName(driverClassName);
        datasource.setInitialSize(initialSize);
        datasource.setMinIdle(minIdle);
        datasource.setMaxActive(maxActive);
        datasource.setMaxWait(maxWait);
        datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        datasource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
        return datasource;
    }


    @Value("${jdbc.url}")
    private String dbUrl;
    @Value("${jdbc.username}")
    private String username;
    @Value("${jdbc.password}")
    private String password;
    @Value("${jdbc.driver}")
    private String driverClassName;
    @Value("${jdbc.pool.initialSize}")
    private int initialSize;
    @Value("${jdbc.pool.minIdle}")
    private int minIdle;
    @Value("${jdbc.pool.maxActive}")
    private int maxActive;
    @Value("${jdbc.pool.maxWait}")
    private int maxWait;
    @Value("${jdbc.pool.timeBetweenEvictionRunsMillis}")
    private int timeBetweenEvictionRunsMillis;
    @Value("${jdbc.pool.minEvictableIdleTimeMillis}")
    private int minEvictableIdleTimeMillis;
    @Value("${jdbc.pool.maxPoolPreparedStatementPerConnectionSize}")
    private int maxPoolPreparedStatementPerConnectionSize;
}

