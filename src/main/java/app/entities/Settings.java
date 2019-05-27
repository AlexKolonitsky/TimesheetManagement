package app.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "Settings")
public class Settings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private int id;
    private int companyId;
    private String type;
    private String value;

    public Settings() {
    }

    public Settings(int id, int companyId, String type, String value) {
        this.id = id;
        this.companyId = companyId;
        this.type = type;
        this.value = value;
    }

    public Settings(int companyId, String type, String value) {
        this.companyId = companyId;
        this.type = type;
        this.value = value;
    }

    public Settings(Settings settings) {
        this(
                settings.getId(),
                settings.getCompanyId(),
                settings.getType(),
                settings.getValue()
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Settings)) {
            return false;
        }
        Settings settings = (Settings) object;
        return getId() == settings.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
