package app.security.jwt;


import app.entities.Integration;

public final class JwtUserFactory {

    public static JwtIntegration create(Integration integration) {
        return new JwtIntegration(
                integration.getId(),
                integration.getLogin(),
                integration.getPassword(),
                , , )
    }
}
