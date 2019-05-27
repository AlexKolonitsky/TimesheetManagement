package app.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "Integrations")
public class Integration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private int id;
    private int companyId;
    private String type;
    private String login;
    private String password;

    public Integration() {
    }

    public Integration(int id, int companyId, String type,
                       String login, String password) {
        this.id = id;
        this.companyId = companyId;
        this.type = type;
        this.login = login;
        this.password = password;
    }

    public Integration(int companyId, String type,
                       String login, String password) {
        this.companyId = companyId;
        this.type = type;
        this.login = login;
        this.password = password;
    }

    public Integration(Integration integration) {
        this(
                integration.getId(),
                integration.getCompanyId(),
                integration.getType(),
                integration.getLogin(),
                integration.getPassword()
        );
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Integration)) {
            return false;
        }
        Integration that = (Integration) object;
        return getId() == that.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
