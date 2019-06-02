package app.resources;

import app.dao.IntegrationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Component
@Path("/integration")
public class IntegrationResources {

    @Autowired
    private IntegrationDao integrationDao;

    @GET
    @Path("/{login}")
    @Produces(MediaType.APPLICATION_JSON)
    public Object findByLogin(@PathParam("login") String login) {
        return integrationDao.findByLogin(login);
    }
}
