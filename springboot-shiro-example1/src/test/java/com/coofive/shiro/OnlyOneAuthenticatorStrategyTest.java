package com.coofive.shiro;

import com.coofive.shiro.realm.FirstRealm;
import com.coofive.shiro.realm.SecondRealm;
import com.coofive.shiro.realm.ThirdRealm;
import com.coofive.shiro.strategy.OnlyOneAuthenticatorStrategy;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author wenwu.liu.o
 */

public class OnlyOneAuthenticatorStrategyTest {
    @Before
    public void setUp() throws Exception {
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        // 设置 realm之前 需先设置authenticator 不然会抛出 No realms have been configured! One or more realms must be present
        ModularRealmAuthenticator authenticator = new ModularRealmAuthenticator();
        authenticator.setAuthenticationStrategy(new OnlyOneAuthenticatorStrategy());
        securityManager.setAuthenticator(authenticator);

        // 设置环境
        securityManager.setRealms(Lists.newArrayList(new FirstRealm(), new SecondRealm()));
        SecurityUtils.setSecurityManager(securityManager);

    }

    @Test
    public void testOnlyOneAuthenticator() {
        UsernamePasswordToken token = new UsernamePasswordToken("test", "123");
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
        } catch (Exception e) {
            Assert.assertEquals(e instanceof AuthenticationException, true);
        }
    }

    @Test
    public void testSuccessfulOnlyOneAuth() {
        UsernamePasswordToken token = new UsernamePasswordToken("test2", "123");
        // 设置环境
        DefaultSecurityManager securityManager = (DefaultSecurityManager) SecurityUtils.getSecurityManager();
        securityManager.setRealms(Lists.newArrayList(new FirstRealm(), new ThirdRealm()));

        Subject subject = SecurityUtils.getSubject();
        subject.login(token);

        Assert.assertEquals(subject.isAuthenticated(), true);
        Assert.assertEquals(subject.getPrincipals().asList().size(), 1);

        subject.logout();
        Assert.assertEquals(subject.isAuthenticated(), false);

    }
}
