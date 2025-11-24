package com.alrussy.product_service.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    @Override
    public AbstractAuthenticationToken convert(@NonNull Jwt jwt) {
        Collection<GrantedAuthority> roles = extractAuthorition(jwt);

        return new JwtAuthenticationToken(jwt, roles);
    }

    private Collection<GrantedAuthority> extractAuthorition(Jwt jwt) {

        final String realm = "realm_access";
        if (jwt.getClaim(realm) != null) {
            Map<String, Object> realmAccess = jwt.getClaim(realm);
            ObjectMapper mapper = new ObjectMapper();
            System.out.println("roles" + realmAccess.get("roles"));
            List<String> keycloakRoles = mapper.convertValue(realmAccess.get("roles"), new ArrayList().getClass());

            List<GrantedAuthority> roles = new ArrayList<>();
            for (String keycloakRole : keycloakRoles) {
                roles.add(new SimpleGrantedAuthority(keycloakRole));
            }

            return roles;
        }
        return new ArrayList<>();
    }
}
