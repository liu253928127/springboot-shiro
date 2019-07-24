package com.coofive.shiro.perms;

import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.RolePermissionResolver;
import org.apache.shiro.authz.permission.WildcardPermission;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * @author wenwu.liu.o
 */
public class RolePermsResolver implements RolePermissionResolver {
    @Override
    public Collection<Permission> resolvePermissionsInRole(String roleString) {
        if (roleString.contains("role1")) {
            return Collections.singletonList(new WildcardPermission("menu:*"));
        }
        return null;
    }
}
