package app.resources;


import app.dao.impl.AssignmentDaoImpl;
import app.dao.impl.EmployeeDaoImpl;
import app.dao.impl.LogsDaoImpl;
import app.dao.impl.ProjectDaoImpl;
import app.entities.Assignment;
import app.entities.Employee;
import app.entities.EmployeeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Component
@Path("/employee")
public class EmployeeResource {

    @Autowired
    private EmployeeDaoImpl employeeDao;

    @Autowired
    private ProjectDaoImpl projectDao;

    @Autowired
    private LogsDaoImpl logsDao;

    @Autowired
    private AssignmentDaoImpl assignmentDao;

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Employee> getAll() {
        return employeeDao.getAll();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public EmployeeResponse get(@PathParam("id") int id) {
        EmployeeResponse response = new EmployeeResponse();
        response.setEmployee(employeeDao.findById(id));
        List<Assignment> assignmentList = assignmentDao.getEmployeeProjects(id);
        response.setAssignments(assignmentList);
        response.setLogs(logsDao.findAll());
        response.setProjects(projectDao.findAll());

        return response;
    }

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response add(Employee employee) {
        employeeDao.save(employee);
        return Response.status(Response.Status.CREATED.getStatusCode()).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response edit(@PathParam("id") int id, Employee employee) {
        employeeDao.edit(employee);
        return Response.status(Response.Status.CREATED.getStatusCode()).build();
    }

    @DELETE
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") int id) {
        employeeDao.delete(id);
        return Response.status(Response.Status.OK.getStatusCode()).build();
    }

    @POST
    @Path("/assign/{employeeId}/{projectId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response assignToProject(
            @PathParam("employeeId") int employeeId,
            @PathParam("projectId") int projectId) {
        employeeDao.assignToProject(employeeDao.findById(employeeId),
                projectDao.findById(projectId));
        return Response.status(Response.Status.OK.getStatusCode()).build();
    }
}
