package app.security.jwt;

import app.entities.Role;
import org.springframework.security.core.GrantedAuthority;
import app.entities.Integration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class JwtIntegrationsFactory {

    public static JwtIntegration create(Integration integration) {
        return new JwtIntegration(
                integration.getId(),
                integration.getCompanyId(),
		        integration.getType(),
		        integration.getLogin(),
		        integration.getPassword(),
		        true
//		        ,mapToGrantedAuthorities(new ArrayList<>())
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<Role> userRoles) {
    	return userRoles.stream()
			    .map(role ->
			                new SimpleGrantedAuthority(role.getName())
			    ).collect(Collectors.toList());
    }
}
