package app.resources;

import app.dao.impl.RoleDaoImpl;
import app.entities.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Component
@Path("/role")
public class RoleResource {

    @Autowired
    private RoleDaoImpl roleDao;

    @GET
    @Path("/{code}")
    @Produces(MediaType.APPLICATION_JSON)
    public Role get(@PathParam("code") String code) {
        return roleDao.findByCode(code);
    }
}
