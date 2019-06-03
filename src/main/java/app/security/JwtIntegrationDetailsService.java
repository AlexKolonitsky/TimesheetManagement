package app.security;

import app.dao.IntegrationDao;
import app.dao.impl.IntegrationDaoImpl;
import app.entities.Integration;
import app.security.jwt.JwtIntegration;
import app.security.jwt.JwtIntegrationsFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtIntegrationDetailsService implements UserDetailsService {

	@Autowired
	private IntegrationDao integrationDao;

	public UserDetails loadUserByUsername(String username) {
		Integration integration = (Integration) integrationDao.findByLogin(username);

		if (integration == null) {
			throw new UsernameNotFoundException("User with login: " + username + " not found");
		}

		JwtIntegration jwtIntegration = JwtIntegrationsFactory.create(integration);
		return jwtIntegration;
	}
}
