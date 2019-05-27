package app.config;

import app.resources.AssignmentResource;
import app.resources.CompanyResource;
import app.resources.EmployeeResource;
import app.resources.InvitationResource;
import app.resources.LogsResource;
import app.resources.NotificationResource;
import app.resources.ProjectResource;
import app.resources.ProjectVersionResource;
import app.resources.SettingsResource;
import app.resources.TimesheetResource;
import app.resources.ProjectEditPageResources;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("/*")
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        register(CompanyResource.class);
        register(EmployeeResource.class);
        register(LogsResource.class);
        register(ProjectResource.class);
        register(AssignmentResource.class);
        register(ProjectVersionResource.class);
        register(NotificationResource.class);
        register(InvitationResource.class);
        register(SettingsResource.class);
        register(TimesheetResource.class);
        register(ProjectEditPageResources.class);
    }
}
