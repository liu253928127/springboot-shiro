package com.coofive.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author wenwu.liu.o
 */
public class SimpleRealmTest {

    @Before
    public void setUp() throws Exception {
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        // 设置需要校验的用户名与密码
        SimpleAccountRealm simpleRealm = new SimpleAccountRealm();
        simpleRealm.addAccount("test", "123");
        defaultSecurityManager.setRealm(simpleRealm);
        // 设置全局的环境
        SecurityUtils.setSecurityManager(defaultSecurityManager);
    }

    @Test
    public void testSimpleRealm() {
        UsernamePasswordToken token = new UsernamePasswordToken("test", "123");
        Subject subject = SecurityUtils.getSubject();

        // 登录认证数据
        subject.login(token);
        Assert.assertEquals(subject.isAuthenticated(), true);

        // 登出
        subject.logout();
        Assert.assertEquals(subject.isAuthenticated(), false);
    }
}
