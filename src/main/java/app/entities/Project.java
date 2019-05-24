package app.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "Project")
public class Project {
    @Id
    private int id;
    private int companyId;
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

    public Project(int id, int companyId, String name, String logoUrl,
                   Date startDate, Date endDate, long manHours, String code, String color, String description) {
        this.id = id;
        this.companyId = companyId;
        this.name = name;
        this.logoUrl = logoUrl;
        this.startDate = startDate;
        this.endDate = endDate;
        this.manHours = manHours;
        this.code = code;
        this.color = color;
        this.description = description;
    }

    public Project(Project project) {
        this(project.getId(),
                project.getCompanyId(),
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

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
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

    public void setManHours(long manHours) {
        this.manHours = manHours;
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
        if (id != project.id)
            return false;
        if (companyId != project.companyId)
            return false;
        if (name != null ? !name.equals(project.name) : project.name != null)
            return false;
        if (logoUrl != null ? !logoUrl.equals(project.logoUrl) : project.logoUrl != null)
            return false;
        return code != null ? !code.equals(project.code) : project.code != null;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, companyId, name, logoUrl, code);
    }

    @Override
    public String toString() {
        return "Project{"
                + "id=" + id
                + ", companyId='" + companyId + '\''
                + ", name='" + name + '\''
                + ", logoUrl='" + logoUrl + '\''
                + ", code=" + code
                + '}';
    }
}
