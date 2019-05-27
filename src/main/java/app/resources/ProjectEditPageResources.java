package app.resources;

import app.dao.ProjectPageEditDao;
import app.dto.ProjectEditPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Component
@Path("/projects")
public class ProjectEditPageResources {

    @Autowired
    private ProjectPageEditDao<ProjectEditPage> projectPageEditDao;

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public ProjectEditPage get(@PathParam("id") int id) {
        return projectPageEditDao.getPageData(id);
    }
}
