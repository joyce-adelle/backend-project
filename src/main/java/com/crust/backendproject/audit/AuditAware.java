package com.crust.backendproject.audit;

import com.crust.backendproject.models.User;
import com.crust.backendproject.security.PrincipalUser;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuditAware implements AuditorAware<Long> {

    @Override
    public Optional<Long> getCurrentAuditor() {

        Optional<Authentication> authentication = Optional
                .ofNullable(SecurityContextHolder.getContext().getAuthentication());

        if (authentication.isPresent()) {

            if (!(authentication.get() instanceof AnonymousAuthenticationToken)
                && authentication.get().isAuthenticated()) {
                return Optional.of(((PrincipalUser) authentication.get().getPrincipal()).getUser().getId());
            } else {
                return Optional.empty();
            }
        } else {
            return Optional.empty();
        }
    }

    public Optional<User> getCurrentUserAuditor() {

        Optional<Authentication> authentication = Optional
                .ofNullable(SecurityContextHolder.getContext().getAuthentication());

        if (authentication.isPresent()) {

            if (!(authentication.get() instanceof AnonymousAuthenticationToken)
                && authentication.get().isAuthenticated()) {
                return Optional.of(((PrincipalUser) authentication.get().getPrincipal()).getUser());
            } else {
                return Optional.empty();
            }
        } else {
            return Optional.empty();
        }
    }

}
