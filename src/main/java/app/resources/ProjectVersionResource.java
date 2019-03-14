package app.resources;

import app.entities.ProjectVersion;
import app.service.ProjectVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Component
@Path("/version")
public class ProjectVersionResource {

    @Autowired
    private ProjectVersionService projectVersionService;

    @GET
    @Path("/get")
    @Produces(MediaType.APPLICATION_JSON)
    public ProjectVersion getProjectVersion() {
        return projectVersionService.getProjectVersion();
    }
}
