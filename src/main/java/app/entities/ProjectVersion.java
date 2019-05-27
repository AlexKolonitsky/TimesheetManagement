package app.entities;

import java.util.Objects;

public class ProjectVersion {

    private String version;

    public ProjectVersion() {
    }

    public ProjectVersion(String version) {
        this.version = version;
    }

    public ProjectVersion(ProjectVersion version) {
        this(version.getVersion());
    }

    public void setVersion(String version) {
        this.version = version;
    }


    public String getVersion() {
        return version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProjectVersion that = (ProjectVersion) o;

        return Objects.equals(version, that.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getVersion());
    }
}
