package org.danekja.ymanager.business.auth.permissions;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.access.expression.DenyAllPermissionEvaluator;
import org.springframework.security.core.Authentication;

import java.io.Serializable;
import java.util.Map;

/**
 * Custom permission evaluator which delegates permission check to other custom evaluators based on
 * target type.
 * <p>
 * This mechanism allows for custom evaluator for each of domain classes -> different logic when evaluating permissions.
 * <p>
 * Documentation: <a href="https://insource.io/blog/articles/custom-authorization-with-spring-boot.html">Source</a>
 */
public class DelegatingPermissionEvaluator implements PermissionEvaluator {

    private static final PermissionEvaluator denyAll = new DenyAllPermissionEvaluator();
    private final Map<String, PermissionEvaluator> permissionEvaluators;

    public DelegatingPermissionEvaluator(Map<String, PermissionEvaluator> permissionEvaluators) {
        this.permissionEvaluators = permissionEvaluators;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        PermissionEvaluator permissionEvaluator = permissionEvaluators.get(targetDomainObject.getClass().getSimpleName());
        if (permissionEvaluator == null) {
            permissionEvaluator = denyAll;
        }

        return permissionEvaluator.hasPermission(authentication, targetDomainObject, permission);
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        PermissionEvaluator permissionEvaluator = permissionEvaluators.get(targetType);
        if (permissionEvaluator == null) {
            permissionEvaluator = denyAll;
        }

        return permissionEvaluator.hasPermission(authentication, targetId, targetType, permission);
    }
}
