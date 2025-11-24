package com.alrussy_dev.inventory_service.Auditing;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;

public class AppAuditing implements AuditorAware<String> {

    @Autowired
    HttpServletRequest httpServletRequest;

    @Override
    public Optional<String> getCurrentAuditor() {
        //	String userDetails=httpServletRequest.getHeader("X-User-Details").toString();
        return Optional.ofNullable("user");
    }
}
