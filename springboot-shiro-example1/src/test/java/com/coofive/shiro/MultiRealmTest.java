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
public class MultiRealmTest {

    @Before
    public void setUp() throws Exception {
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        // 设置需要校验的用户名与密码
        SimpleAccountRealm simpleRealm = new SimpleAccountRealm();
        simpleRealm.addAccount("test", "123");
        simpleRealm.addAccount("test2", "1234");
        defaultSecurityManager.setRealm(simpleRealm);
        // 设置全局的环境
        SecurityUtils.setSecurityManager(defaultSecurityManager);
    }

    @Test
    public void name() {
        UsernamePasswordToken token1 = new UsernamePasswordToken("test", "123");
        UsernamePasswordToken token2 = new UsernamePasswordToken("test2", "1234");
        Subject subject = SecurityUtils.getSubject();
        // 登录
        subject.login(token1);
        Assert.assertEquals(subject.isAuthenticated(), true);
        // 登出
        subject.logout();
        Assert.assertEquals(subject.isAuthenticated(), false);

        // 登录
        subject.login(token2);
        Assert.assertEquals(subject.isAuthenticated(), true);
        // 登出
        subject.logout();
        Assert.assertEquals(subject.isAuthenticated(), false);
    }
}
