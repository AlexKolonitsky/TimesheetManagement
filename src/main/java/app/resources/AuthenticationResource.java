package app.resources;

import app.dao.IntegrationDao;
import app.dto.AuthenticationRequestDto;
import app.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.util.HashMap;
import java.util.Map;

@Component
@Path("/")
public class AuthenticationResource {

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	@Autowired
	private IntegrationDao integrationDao;

	@POST
	@Path("/login")
	public ResponseEntity login (@RequestBody AuthenticationRequestDto requestDto) {
		try {
			String login = requestDto.getLogin();
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login, requestDto.getPassword()));
			Object integration = integrationDao.findByLogin(login);

			if (integration == null) {
				throw new UsernameNotFoundException("User does not exist");
			}

			String token = jwtTokenProvider.createToken(login);
			Map<Object, Object> response = new HashMap<>();
			response.put("login", login);
			response.put("token", token);

			return ResponseEntity.ok(response);
		} catch (AuthenticationException e) {
			throw new BadCredentialsException("Invalid login or password");
		}

	}
}
