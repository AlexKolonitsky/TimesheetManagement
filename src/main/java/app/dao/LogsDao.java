package app.dao;

import app.entities.Logs;
import app.entities.Assignment;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogsDao extends BasicCrudDao<Logs> {

    List<Logs> getLogsByAssignmentId(List<Assignment> assignmentList);
}
