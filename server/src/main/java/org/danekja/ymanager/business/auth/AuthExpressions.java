package org.danekja.ymanager.business.auth;

/**
 * This class holds String expressions used in authorization checks.
 * <p>
 * Please comment each expressions well enough to clarify what constraints on user authority it enforces.
 */
public class AuthExpressions {

    /**
     * Used in cases where the action can be triggered by either:
     * <ul>
     * <li>employer - typically can edit all records</li>
     * <li>data owner - employee can edit only his records</li>
     * </ul>
     * <p>
     * In this case, the protected method needs to take <b>userId</b> parameter which represents the "userId" value and
     * is compared to principal id.
     */
    public static final String MASTER_SELF_ID_PARAM = "hasAuthority('EMPLOYER') or #userId == authentication.principal.id";
}
