package com.coofive.shiro.perms;

import lombok.Data;
import org.apache.shiro.authz.Permission;

import java.util.Objects;

/**
 * @author wenwu.liu.o
 */
@Data
public class MyPermission implements Permission {

    private String perms;

    public MyPermission(String perms) {
        this.perms = perms;
    }

    @Override
    public boolean implies(Permission p) {
        MyPermission myPermission = (MyPermission) p;
        return Objects.equals(myPermission.getPerms(), this.perms);
    }
}
