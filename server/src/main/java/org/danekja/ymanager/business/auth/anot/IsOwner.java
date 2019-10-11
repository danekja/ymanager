package org.danekja.ymanager.business.auth.anot;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.*;

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
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@PreAuthorize("hasAuthority('EMPLOYER') or #userId == authentication.principal.id")
public @interface IsOwner {
}
