package app.dao.impl;

import app.dao.ProjectPageEditDao;
import app.dto.ProjectEditPage;
import app.entities.*;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Repository
@Transactional
public class ProjectEditPageDaoImpl implements ProjectPageEditDao {

    private final String GETTEAM = "select * from employee e join assignment a on e.id = a.employeeId where a.projectId = :id ";
    private final String GETTIMESHEETS = "select * from timesheet t join assignment a on t.assignmentId = a.id where a.projectId = :id ";
    private final String GETINTEGRATIONS = "select * from integration i join company c on i.companyId = c.id where c.id = :id";

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public ProjectEditPage getPageData(int id) {
        ProjectEditPage projectEditPage = new ProjectEditPage();
        projectEditPage.setProject(getCurrentProject(id));
        projectEditPage.setTeam(getTeamFromProject(id));
        projectEditPage.setManHoursExpected(getManHoursExpected(id));
        projectEditPage.setTimesheets(getTimesheetsForProject(id));
        projectEditPage.setIntegrations(getIntegrationsForProject(id));
        return projectEditPage;
    }

    public Project getCurrentProject(int id) {
        Project project = sessionFactory.getCurrentSession().get(Project.class, id);
        if (project == null) {
            throw new NoSuchElementException();
        }
        return project;
    }

    public List<Employee> getTeamFromProject(int id) {
        Query query = sessionFactory.getCurrentSession().createSQLQuery(GETTEAM);
        query.setParameter("id", id);
        return query.getResultList();
    }

    public double getManHoursExpected(int id) {
        Project project = sessionFactory.getCurrentSession().get(Project.class, id);
        return project.getManHours();
    }

    public List<Timesheet> getTimesheetsForProject(int id) {
        Query query = sessionFactory.getCurrentSession().createSQLQuery(GETTIMESHEETS);
        query.setParameter("id", id);
        return query.getResultList();
    }

    public List<Integration> getIntegrationsForProject(int id) {
        Query query = sessionFactory.getCurrentSession().createSQLQuery(GETINTEGRATIONS);
        query.setParameter("id", id);
        return query.getResultList();
    }
}
