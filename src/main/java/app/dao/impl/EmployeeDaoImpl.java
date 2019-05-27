package app.dao.impl;

import app.entities.Employee;
import app.entities.Project;

public class EmployeeDaoImpl extends BasicCrudDaoImpl<Employee> {

    public synchronized void assignToProject(
            Employee employee, Project project) {
        employee.assignToProject(project);
    }
}
