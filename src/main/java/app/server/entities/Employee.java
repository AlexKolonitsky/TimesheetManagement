package app.server.entities;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Employee {

    private int id;
    private String name;
    private String photo;
    private String email;

    private List<Project> projects = new LinkedList<>();

    public Employee() {}

    public Employee(int id, String name, String photo, String email) {
        this.id = id;
        this.name = name;
        this.photo = photo;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhoto() {
        return photo;
    }

    public String getEmail() {
        return email;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
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
                && Objects.equals(photo, employee.photo)
                && Objects.equals(email, employee.email)
                && Objects.equals(projects, employee.projects);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, photo, email, projects);
    }

    @Override
    public String toString() {
        return "Employee{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", photo='" + photo + '\''
                + ", email='" + email + '\''
                + '\n'
                + ", projects=" + projects
                + '}';
    }
}
