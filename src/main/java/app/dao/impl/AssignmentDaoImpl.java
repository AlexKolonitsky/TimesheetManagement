package app.dao.impl;

import app.dao.AssignmentDao;
import app.entities.Assignment;
import app.exceptions.EntityAlreadyExistsException;
import app.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

@Repository
public class AssignmentDaoImpl implements AssignmentDao {

    @Value("${db-driver}")
    private String driver;
    @Value("${db-url}")
    private String url;
    @Value("${db-username}")
    private String username;
    @Value("${db-password}")
    private String password;

    private Connection connection = null;

    private static final int ROW_EXISTS = 1;

    private void openDatabaseConnection() {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException exception) {
            exception.printStackTrace();
        }
        try {
            connection = DriverManager
                    .getConnection(url, username, password);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public List<Assignment> getAll() {
        openDatabaseConnection();
        List<Assignment> assignmentList = null;
        try (PreparedStatement preparedStatement = connection
                .prepareStatement("SELECT * FROM "
                        + "timesheet_dev.Assignment")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            assignmentList = assignmentListMapper(resultSet);
            preparedStatement.close();
            connection.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return assignmentList;
    }

    @Override
    public Assignment findById(int projectId, int employeeId) {
        openDatabaseConnection();
        Assignment assignment = null;
        try (PreparedStatement preparedStatement = connection
                .prepareStatement("SELECT * FROM "
                        + "timesheet_dev.Assignment "
                        + "WHERE projectId=? AND employeeId=?")) {
            preparedStatement.setInt(1, projectId);
            preparedStatement.setInt(2, employeeId);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            assignment = assigmentMapper(resultSet);
            if (assignment == null) {
                throw new EntityNotFoundException();
            }
            preparedStatement.close();
            connection.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return assignment;
    }

    @Override
    public void save(Assignment assignment) {
        openDatabaseConnection();
        try (PreparedStatement preparedStatement = connection
                .prepareStatement("INSERT INTO "
                        + "timesheet_dev.Assignment "
                        + "(projectId, employeeId, workLoadInMinutes) "
                        + "VALUE (?, ?, ?)")) {
            preparedStatement.setInt(1, assignment.getProjectId());
            preparedStatement.setInt(2, assignment.getEmployeeId());
            preparedStatement.setInt(3, assignment.getWorkLoadInMinutes());
            preparedStatement.execute();
            preparedStatement.close();
            connection.close();
        } catch (SQLIntegrityConstraintViolationException exception) {
            throw new EntityAlreadyExistsException();
        } catch (SQLException exception) {
            exception.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void delete(Assignment assignment) {
        delete(assignment.getProjectId(), assignment.getEmployeeId());
    }

    @Override
    public void delete(int projectId, int employeeId) {
        if (!isAssignmentExists(projectId, employeeId)) {
            throw new EntityNotFoundException();
        }
        openDatabaseConnection();
        try (PreparedStatement preparedStatement = connection
                .prepareStatement("DELETE FROM "
                        + "timesheet_dev.Assignment "
                        + "WHERE projectId=? AND employeeId=?")) {
            preparedStatement.setInt(1, projectId);
            preparedStatement.setInt(2, employeeId);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void edit(Assignment assignment) {
        if (!isAssignmentExists(assignment.getProjectId(),
                assignment.getEmployeeId())) {
            throw new EntityNotFoundException();
        }
        openDatabaseConnection();
        try (PreparedStatement preparedStatement = connection
                .prepareStatement("UPDATE "
                        + "timesheet_dev.Assignment "
                        + "SET workLoadInMinutes=? "
                        + "WHERE projectId=? AND employeeId=?")) {
            preparedStatement.setInt(1, assignment.getWorkLoadInMinutes());
            preparedStatement.setInt(2, assignment.getProjectId());
            preparedStatement.setInt(3, assignment.getEmployeeId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private boolean isAssignmentExists(int projectId, int employeeId) {
        openDatabaseConnection();
        try (PreparedStatement preparedStatement = connection
                .prepareStatement("SELECT EXISTS"
                        + "(SELECT projectId, employeeId FROM "
                        + "timesheet_dev.Assignment "
                        + "WHERE projectId=? AND employeeId=?)")) {
            preparedStatement.setInt(1, projectId);
            preparedStatement.setInt(2, employeeId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()
                    && resultSet.getInt(1) == ROW_EXISTS) {
                return true;
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    private List<Assignment> assignmentListMapper(ResultSet resultSet)
            throws SQLException {
        List<Assignment> assignmentList = new LinkedList<>();
        Assignment assignment;
        while (resultSet.next()) {
            assignment = assigmentMapper(resultSet);
            assignmentList.add(assignment);
        }
        return assignmentList;
    }

    private Assignment assigmentMapper(ResultSet resultSet)
            throws SQLException {
        Assignment assignment = new Assignment();
        if (!resultSet.wasNull()) {
            assignment.setProjectId(resultSet.getInt("projectId"));
            assignment.setEmployeeId(resultSet.getInt("employeeId"));
            assignment.setWorkLoadInMinutes(
                    resultSet.getInt("workLoadInMinutes")
            );
        }
        return assignment;
    }
}
