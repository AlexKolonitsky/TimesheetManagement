package app.resources;

import app.dao.ProjectDao;
import app.entities.Project;
import app.entities.ProjectPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Component
@Path("/project")
public class ProjectResource {

    @Autowired
    private ProjectDao projectPageDao;

    @GET
    @Path("/info/{companyId}")
    @Produces(MediaType.APPLICATION_JSON)
    public ProjectPage getInfo(@PathParam("companyId") int companyId) {
        return projectPageDao.getProjectData(companyId);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Project> findAll() {
        return projectPageDao.findAll();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Project findById(@PathParam("id") int id) {
        return projectPageDao.findById(id);
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") int id, Project project) {
        if (id != project.getId()) {
            return Response.status(Response.Status.CONFLICT.getStatusCode())
                    .build();
        }
        projectPageDao.update(project);
        return Response.status(Response.Status.CREATED.getStatusCode()).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(Project project) {
        projectPageDao.create(project);
        return Response.status(Response.Status.CREATED.getStatusCode()).build();
    }

    @DELETE
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteById(@PathParam("id") int id) {
        projectPageDao.deleteById(id);
        return Response.status(Response.Status.OK.getStatusCode()).build();
    }
}

