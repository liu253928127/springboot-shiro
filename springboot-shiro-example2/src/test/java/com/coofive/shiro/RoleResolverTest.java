package com.coofive.shiro;

import com.coofive.shiro.perms.RolePermsResolver;
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
public class RoleResolverTest {

    @Before
    public void setUp() throws Exception {
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        // 设置自定义RolePermissionResolver
        ModularRealmAuthorizer authorizer = new ModularRealmAuthorizer();
        authorizer.setRolePermissionResolver(new RolePermsResolver());
        securityManager.setAuthorizer(authorizer);
        securityManager.setRealm(new MyRealm());
        SecurityUtils.setSecurityManager(securityManager);
    }

    @Test
    public void testRolePermsResolver() {
        UsernamePasswordToken token = new UsernamePasswordToken("test", "123");
        Subject subject = SecurityUtils.getSubject();
        subject.login(token);
        Assert.assertEquals(subject.isAuthenticated(), true);

        Assert.assertEquals(subject.isPermitted("menu:wild"), true);
        subject.logout();

        Assert.assertEquals(subject.isAuthenticated(), false);
    }
}
