package org.danekja.ymanager.business.auth.permissions;

import org.danekja.ymanager.domain.User;
import org.danekja.ymanager.domain.UserRole;
import org.danekja.ymanager.domain.Vacation;
import org.danekja.ymanager.repository.VacationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Objects;

/**
 * {@link TypedPermissionEvaluator} for checking permissions for {@link Vacation} objects.
 * <p>
 * Invoke via {@code hasPermissions(<id parameter name>, 'Vacation', null)} permission syntax in security annotations.
 */
@Component
public class VacationModifyPermisionEvaluator implements TypedPermissionEvaluator {

    private static final Logger LOG = LoggerFactory.getLogger(VacationModifyPermisionEvaluator.class);

    private final VacationRepository vacationRepository;

    @Autowired
    public VacationModifyPermisionEvaluator(VacationRepository vacationRepository) {
        this.vacationRepository = vacationRepository;
    }

    @Override
    public String getTargetType() {
        return Vacation.class.getSimpleName();
    }

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        if (targetDomainObject != null && Vacation.class.isAssignableFrom(targetDomainObject.getClass())) {
            Vacation target = (Vacation) targetDomainObject;

            LOG.debug("Checking permission of {} to vacation: {}", authentication.getName(), target.getId());

            if (authentication.getAuthorities().parallelStream().anyMatch(o -> Objects.equals(o.getAuthority(), UserRole.EMPLOYER.name()))) {

                LOG.debug("Permission of {} to vacation: {} granted to employer", authentication.getName(), target.getId());
                return true;

            } else {

                boolean canDo = Objects.equals(target.getUserId(), ((User) authentication.getPrincipal()).getId());
                LOG.debug("Permission of {} to vacation: {} for employee: {}", authentication.getName(), target.getId(), canDo);
                return canDo;

            }
        }

        LOG.warn("Checking permission of {} to vacation: {} failed - null or invalid type of object", authentication.getName(), targetDomainObject);
        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        if (targetId != null && Long.class.isAssignableFrom(targetId.getClass()) && Objects.equals(Vacation.class.getSimpleName(), targetType)) {
            return hasPermission(authentication, vacationRepository.getVacationDay((Long) targetId).orElse(null), permission);
        }

        return false;
    }
}
