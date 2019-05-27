package app.entities;

import java.util.List;
import java.util.Map;

public class ProjectPage {

    private int companyId;
    private Map<Project, List<Employee>> team;
    private Map<Project, Double> projectLoading;


    public ProjectPage() {
    }

    public Map<Project, Double> getProjectLoading() {
        return projectLoading;
    }

    public void setProjectLoading(Map<Project, Double> projectLoading) {
        this.projectLoading = projectLoading;
    }

    public Map<Project, List<Employee>> getTeam() {
        return team;
    }

    public void setTeam(Map<Project, List<Employee>> team) {
        this.team = team;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }
}

