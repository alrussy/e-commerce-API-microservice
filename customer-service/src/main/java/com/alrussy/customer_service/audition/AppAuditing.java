package com.alrussy.customer_service.audition;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;

public class AppAuditing implements AuditorAware<String> {

    @Autowired
    HttpServletRequest httpServletRequest;

    @Override
    public Optional<String> getCurrentAuditor() {
        //    	Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        //    	JwtAuthenticationToken authenticationToken=new JwtAuthenticationToken((Jwt)
        // authentication.getPrincipal());
        //    	var username=authenticationToken.getTokenAttributes().get("preferred_username").toString();
        return Optional.ofNullable("@aalrussy");
    }
}
