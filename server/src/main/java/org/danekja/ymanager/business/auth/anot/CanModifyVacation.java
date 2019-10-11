package org.danekja.ymanager.business.auth.anot;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.*;

/**
 * Methods annotated with this interface can be called either by EMPLOYER role
 * or owner of the given vacation instance.
 * <p>
 * This annotation expects method to have {@code Long vacationId} argument.
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@PreAuthorize("hasPermission(#vacationId, 'Vacation', null)")
public @interface CanModifyVacation {

}
