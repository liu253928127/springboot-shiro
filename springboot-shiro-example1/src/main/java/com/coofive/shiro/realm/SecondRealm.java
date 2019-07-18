package com.coofive.shiro.realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.realm.Realm;

import java.util.Objects;

/**
 * @author coofive
 */
public class SecondRealm implements Realm {
    @Override
    public String getName() {
        return "SecondRealm";
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        // 仅支持UsernamePasswordToken
        return token instanceof UsernamePasswordToken;
    }

    @Override
    public AuthenticationInfo getAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
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

        return new SimpleAuthenticationInfo(username + "@163.com", password, getName());
    }
}
