package com.coofive.shiro.perms;

import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.PermissionResolver;
import org.apache.shiro.authz.permission.WildcardPermission;

/**
 * @author wenwu.liu.o
 */
public class MyPermsResolver implements PermissionResolver {

    @Override
    public Permission resolvePermission(String permissionString) {
        if (permissionString.startsWith("+")) {
            return new MyPermission(permissionString);
        }
        return new WildcardPermission(permissionString);
    }
}
