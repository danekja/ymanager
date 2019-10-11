package org.danekja.ymanager.business.auth.anot;


import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.*;

/**
 * This annotation enforces EMPLOYER user role.
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@PreAuthorize("hasAuthority('EMPLOYER')")
public @interface IsEmployer {
}
