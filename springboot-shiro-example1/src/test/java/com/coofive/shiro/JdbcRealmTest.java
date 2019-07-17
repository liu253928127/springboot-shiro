package com.coofive.shiro;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.jdbc.DataSourceBuilder;

import javax.sql.DataSource;

/**
 * @author wenwu.liu.o
 */
public class JdbcRealmTest {

    @Before
    public void setUp() throws Exception {
        // 手动配置数据源
        DataSource dataSource = DataSourceBuilder.create()
                .url("jdbc:mysql://localhost:3306/shiro")
                .username("root").password("123456")
                .driverClassName("com.mysql.jdbc.Driver").build();
        JdbcRealm jdbcRealm = new JdbcRealm();
        jdbcRealm.setDataSource(dataSource);
        // 设置环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(jdbcRealm);
        SecurityUtils.setSecurityManager(defaultSecurityManager);
    }

    @Test
    public void testJdbcRealm() {
        UsernamePasswordToken token = new UsernamePasswordToken("test", "123");
        Subject subject = SecurityUtils.getSubject();
        // 登录
        subject.login(token);
        Assert.assertEquals(subject.isAuthenticated(), true);
        // 登出
        subject.logout();
        Assert.assertEquals(subject.isAuthenticated(), false);
    }
}
