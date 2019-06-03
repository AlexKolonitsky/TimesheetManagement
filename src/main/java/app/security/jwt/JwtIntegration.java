package app.security.jwt;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class JwtIntegration implements UserDetails {

    private final int id;
    private final int companyId;
    private final String type;
    private final String username;
    private final String password;
    private final boolean enabled;
//    private final Collection<? extends GrantedAuthority> authorities;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return authorities;
        return null;
    }

    public JwtIntegration(int id, int companyId, String type, String username, String password, boolean enabled
//            , Collection<? extends GrantedAuthority> authorities
    ) {
        this.id = id;
        this.companyId = companyId;
        this.type = type;
        this.username = username;
        this.password = password;
        this.enabled = enabled;
//        this.authorities = authorities;
    }

    public int getCompanyId() {
        return companyId;
    }

    public String getType() {
        return type;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public int getId() {
        return id;
    }
}
