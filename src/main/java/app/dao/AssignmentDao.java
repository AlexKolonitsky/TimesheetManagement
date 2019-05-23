package app.dao;

import app.entities.Assignment;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignmentDao extends BasicCrudDao<Assignment> {

    List<Assignment> getEmployeeProjects(int employeeId);
}
