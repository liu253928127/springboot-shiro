package com.coofive.shiro;

import com.coofive.shiro.realm.FirstRealm;
import com.coofive.shiro.realm.SecondRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.Authenticator;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.pam.AllSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * @author wenwu.liu.o
 */
public class AllSuccessfulStrategyTest {

    @Before
    public void setUp() throws Exception {
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        // 设置 realm之前 需先设置authenticator 不然会抛出 No realms have been configured! One or more realms must be present
        ModularRealmAuthenticator authenticator = new ModularRealmAuthenticator();
        authenticator.setAuthenticationStrategy(new AllSuccessfulStrategy());
        securityManager.setAuthenticator(authenticator);

        // 设置需要校验的用户名与密码
        List<Realm> realms = Lists.newArrayList(new FirstRealm(), new SecondRealm());
        securityManager.setRealms(realms);

        // 设置环境
        SecurityUtils.setSecurityManager(securityManager);
    }

    @Test
    public void TestAllSuccessful() {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("test", "123");

        subject.login(token);

        // 得到了两条凭证
        PrincipalCollection principals = subject.getPrincipals();
        Assert.assertEquals(principals.asList().size(), 2);

        subject.logout();
    }
}
