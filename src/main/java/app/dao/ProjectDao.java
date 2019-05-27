package app.dao;

import app.entities.Project;
import app.entities.ProjectPage;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectDao extends BasicCrudDao<Project> {

	ProjectPage getProjectData(int id);
}
