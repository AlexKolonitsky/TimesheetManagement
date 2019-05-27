package app.dao.impl;

import app.dao.ProjectDao;
import app.entities.Employee;
import app.entities.Project;
import app.entities.ProjectPage;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Transactional
public class ProjectDaoImpl extends BasicCrudDaoImpl<Project> implements ProjectDao {

	@Autowired
	private SessionFactory sessionFactory;

	private static final String GET_PROJECT_TEAM_QUERY = "select e.id, e.name, e.phone, e.email, e.photoUrl from Employee as e join Assignments as a on e.id = a.employeeId join Project as p on p.id = a.projectId where p.id = ?";
	private static final String GET_PROJECT_TIME_QUERY = "select sum(time) from logs as l join assignments as a on a.id = l.assignmentId where a.projectId = ?";
	private static final String GET_PROJECT_BY_COMPANY_ID = "from Project where companyId =:id";


	private Double getProjectTime(int projectId) {
		Query query = sessionFactory.getCurrentSession().createSQLQuery(GET_PROJECT_TIME_QUERY);
		query.setParameter(1, projectId);
		return (Double) query.getSingleResult();
	}


	private List<Employee> getProjectTeam(int projectId) {
		Query query = sessionFactory.getCurrentSession().createSQLQuery(GET_PROJECT_TEAM_QUERY);
		query.setParameter(1, projectId);
		return query.getResultList();
	}


	@Override
	public ProjectPage getProjectData(int companyId) {
		Query query = sessionFactory.getCurrentSession().createQuery(GET_PROJECT_BY_COMPANY_ID);
		query.setParameter("id", companyId);
		List<Project> projects = query.getResultList();
		Map<Project, List<Employee>> team = new HashMap<>();
		Map<Project, Double> loading = new HashMap<>();
		ProjectPage projectPage = new ProjectPage();
		for (Project project : projects) {
			team.put(project, getProjectTeam(project.getId()));
			if (getProjectTime(project.getId()) != null) {
				loading.put(project, getProjectTime(project.getId()));
			} else {
				loading.put(project, 0d);
			}
		}
		projectPage.setCompanyId(companyId);
		projectPage.setTeam(team);
		projectPage.setProjectLoading(loading);
		return projectPage;
	}

}




