package com.alrussy.product_service.audition;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

public class AppAuditing implements AuditorAware<String> {

    @Autowired
    HttpServletRequest httpServletRequest;

    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtAuthenticationToken authenticationToken = new JwtAuthenticationToken((Jwt) authentication.getPrincipal());
        System.out.println(authenticationToken.getTokenAttributes().get("preferred_username"));
        var username = authenticationToken
                .getTokenAttributes()
                .get("preferred_username")
                .toString();
        return Optional.ofNullable(username);
    }
}
