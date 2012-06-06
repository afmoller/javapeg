package moller.javapeg.program.config.model.repository;

public class Repository {

    private RepositoryPaths paths;
    private RepositoryExceptions exceptions;

    public RepositoryPaths getPaths() {
        return paths;
    }
    public RepositoryExceptions getExceptions() {
        return exceptions;
    }
    public void setPaths(RepositoryPaths paths) {
        this.paths = paths;
    }
    public void setExceptions(RepositoryExceptions exceptions) {
        this.exceptions = exceptions;
    }
}
