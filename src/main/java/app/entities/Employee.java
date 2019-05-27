package app.entities;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Employee")
public class Employee {

    @Id
    private int id;
    private String name;
    private String photoUrl;
    private String email;
    private String phone;

    @ElementCollection
    @CollectionTable(name = "Project")
    private List<Project> projects = new LinkedList<>();

    public Employee() {
    }

    public Employee(int id, String name, String photoUrl, String email, String phone) {
        this.id = id;
        this.name = name;
        this.photoUrl = photoUrl;
        this.email = email;
        this.phone = phone;
    }
    public Employee(Employee employee) {
        this(employee.getId(),
                employee.getName(),
                employee.getPhotoUrl(),
                employee.getEmail(),
	            employee.getPhone());
        this.projects = employee.getProjects();
    }

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public String getEmail() {
        return email;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void assignToProject(Project project) {
        projects.add(project);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Employee)) {
            return false;
        }
        Employee employee = (Employee) object;
        return id == employee.id
                && Objects.equals(name, employee.name)
                && Objects.equals(photoUrl, employee.photoUrl)
                && Objects.equals(email, employee.email)
                && Objects.equals(projects, employee.projects);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, photoUrl, email, projects);
    }

    @Override
    public String toString() {
        return "Employee{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", photoUrl='" + photoUrl + '\''
                + ", email='" + email + '\''
                + '\n'
                + ", projects=" + projects
                + '}';
    }
}

