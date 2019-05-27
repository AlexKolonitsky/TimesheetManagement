package app.dto;

import app.entities.Employee;
import app.entities.Integration;
import app.entities.Project;
import app.entities.Timesheet;

import java.util.List;
import java.util.Objects;

public class ProjectEditPage {

    private Project project;
    private List<Employee> team;
    private List<Timesheet> timesheets;
    private double manHoursExpected;
    private List<Integration> integrations;

    public ProjectEditPage() {
    }

    public void setTeam(List<Employee> team) {
        this.team = team;
    }

    public void setTimesheets(List<Timesheet> timesheets) {
        this.timesheets = timesheets;
    }

    public void setManHoursExpected(double manHoursExpected) {
        this.manHoursExpected = manHoursExpected;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public List<Integration> getIntegrations() {
        return integrations;
    }

    public void setIntegrations(List<Integration> integrations) {
        this.integrations = integrations;
    }

    public Project getProject() {
        return project;
    }

    public List<Employee> getTeam() {
        return team;
    }

    public List<Timesheet> getTimesheets() {
        return timesheets;
    }

    public double getManHoursExpected() {
        return manHoursExpected;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof ProjectEditPage)) {
            return false;
        }
        ProjectEditPage that = (ProjectEditPage) object;
        return getProject().getId() == that.getProject().getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProject().getId());
    }
}
