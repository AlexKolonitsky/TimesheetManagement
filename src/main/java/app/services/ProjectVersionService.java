package app.services;

import app.entities.ProjectVersion;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ProjectVersionService {

    @Value("${version}")
    private String version;

    public ProjectVersion getProjectVersion() {
        return new ProjectVersion(version);
    }
}
