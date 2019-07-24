package com.coofive.shiro.realm;

import com.coofive.shiro.perms.MyPermission;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.authz.permission.WildcardPermission;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.Objects;

/**
 * @author wenwu.liu.o
 */
public class MyRealm extends AuthorizingRealm {


    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addRole("role1");
        info.addRole("role2");
        info.addObjectPermission(new WildcardPermission("user:1:*"));
        info.addObjectPermission(new MyPermission("+user:1:s"));
        info.addStringPermission("user2:*");
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        // 得到用户名
        String username = (String) token.getPrincipal();
        // 得到密码
        String password = new String((char[]) token.getCredentials());
        if (!Objects.equals(username, "test")) {
            throw new UnknownAccountException();
        }
        if (!Objects.equals(password, "123")) {
            throw new IncorrectCredentialsException();
        }

        return new SimpleAuthenticationInfo(username, password, getName());
    }
}
