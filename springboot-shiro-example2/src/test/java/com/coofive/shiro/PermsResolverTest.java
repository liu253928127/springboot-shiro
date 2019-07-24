package com.coofive.shiro;

import com.coofive.shiro.perms.MyPermsResolver;
import com.coofive.shiro.realm.MyRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.ModularRealmAuthorizer;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author wenwu.liu.o
 */
public class PermsResolverTest {
    @Before
    public void setUp() throws Exception {
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        // 设置自定义的PermissionResolver
        ModularRealmAuthorizer authorizer = new ModularRealmAuthorizer();
        authorizer.setPermissionResolver(new MyPermsResolver());
        securityManager.setAuthorizer(authorizer);
        securityManager.setRealm(new MyRealm());
        SecurityUtils.setSecurityManager(securityManager);
    }

    @Test
    public void testMyPermsResolver() {
        UsernamePasswordToken token = new UsernamePasswordToken("test", "123");
        Subject subject = SecurityUtils.getSubject();
        subject.login(token);
        Assert.assertEquals(subject.isAuthenticated(), true);
        // 校验 + 开头
        Assert.assertEquals(subject.isPermitted("+user:1:s"), true);
        subject.logout();
        Assert.assertEquals(subject.isAuthenticated(), false);
    }
}
