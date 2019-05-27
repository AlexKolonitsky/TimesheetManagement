package app.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "Projects")
public class Project {
    @Id
    private int id;
    private String name;
    private String logoUrl;
    private Date startDate;
    private Date endDate;
    private long manHours;
    private String code;
    private String color;
    private String description;


    public Project() {
    }

    public Project(int id, String name, String logoUrl,
                   Date startDate, Date endDate, long manHoursInMilliseconds,
                   String code, String color, String description) {
        this.id = id;
        this.name = name;
        this.logoUrl = logoUrl;
        this.startDate = startDate;
        this.endDate = endDate;
        this.manHours = manHoursInMilliseconds;
        this.code = code;
        this.color = color;
        this.description = description;
    }

    public Project(Project project) {
        this(project.getId(),
                project.getName(),
                project.getLogoUrl(),
                project.getStartDate(),
                project.getEndDate(),
                project.getManHours(),
                project.getCode(),
                project.getColor(),
                project.getDescription());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getManHours() {
        return manHours;
    }

    public void setManHours(long manHoursInMilliseconds) {
        this.manHours = manHoursInMilliseconds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Project project = (Project) o;
        if (id != project.id) {
            return false;
        }
        if (manHours != project.manHours) {
            return false;
        }
        if (name != null ? !name
                .equals(project.name) : project.name != null) {
            return false;
        }
        if (logoUrl != null ? !logoUrl
                .equals(project.logoUrl) : project.logoUrl != null) {
            return false;
        }
        if (startDate != null ? !startDate
                .equals(project.startDate) : project.startDate != null) {
            return false;
        }
        if (endDate != null ? !endDate
                .equals(project.endDate) : project.endDate != null) {
            return false;
        }
        if (code != null ? !code
                .equals(project.code) : project.code != null) {
            return false;
        }
        if (color != null ? !color
                .equals(project.color) : project.color != null) {
            return false;
        }
        return description != null ? description
                .equals(project.description) : project.description == null;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getManHours(), getName(), getLogoUrl(),
                getStartDate(), getEndDate(), getCode(), getColor(),
                getDescription());
    }

    @Override
    public String toString() {
        return "Project{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", logoUrl='" + logoUrl + '\''
                + ", startDate=" + startDate
                + ", endDate=" + endDate
                + ", manHours=" + manHours + '\''
                + ", code=" + code
                + ", color" + color + '\''
                + ", description=" + description
                + '}';
    }
}
