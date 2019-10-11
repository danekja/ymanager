package org.danekja.ymanager.business.auth.permissions;

import org.springframework.security.access.PermissionEvaluator;

/**
 * Custom {@link PermissionEvaluator} which is capable of checking
 * user permissions for single type.
 */
public interface TypedPermissionEvaluator extends PermissionEvaluator {

    /**
     * @return simpleName of target type (class)
     */
    String getTargetType();

}
