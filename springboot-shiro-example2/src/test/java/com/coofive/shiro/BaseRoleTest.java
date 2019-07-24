package com.coofive.shiro;

import com.coofive.shiro.realm.MyRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author wenwu.liu.o
 */
public class BaseRoleTest {

    @Before
    public void setUp() throws Exception {
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        securityManager.setRealm(new MyRealm());
        SecurityUtils.setSecurityManager(securityManager);
    }

    @Test
    public void testRole() {
        UsernamePasswordToken token = new UsernamePasswordToken("test", "123");
        Subject subject = SecurityUtils.getSubject();

        subject.login(token);

        Assert.assertEquals(subject.isAuthenticated(), true);
        Assert.assertEquals(subject.hasRole("role1"), true);
        Assert.assertEquals(subject.hasRole("role2"), true);
    }

    @Test
    public void testPerms() {
        UsernamePasswordToken token = new UsernamePasswordToken("test", "123");
        Subject subject = SecurityUtils.getSubject();

        subject.login(token);

        Assert.assertEquals(subject.isAuthenticated(), true);
        Assert.assertEquals(subject.isPermitted("user:*:t"), false);
        Assert.assertEquals(subject.isPermitted("user:1:t"), true);
        Assert.assertEquals(subject.isPermitted("user:1:q"), true);
        Assert.assertEquals(subject.isPermitted("user2:q"), true);
    }
}
