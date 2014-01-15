package moller.javapeg.program.imagerepository;

import java.io.File;

import moller.util.io.Status;

public class ImageRepositoryItem implements Comparable<ImageRepositoryItem>{

    private File path;
    private Status pathStatus;

    public ImageRepositoryItem() {
        this.path = null;
        this.pathStatus = null;
    }

    public ImageRepositoryItem(File path, Status pathStatus) {
        super();
        this.path = path;
        this.pathStatus = pathStatus;
    }

    public File getPath() {
        return path;
    }

    public Status getPathStatus() {
        return pathStatus;
    }

    public void setPath(File path) {
        this.path = path;
    }

    public void setPathStatus(Status pathStatus) {
        this.pathStatus = pathStatus;
    }

    @Override
    public int compareTo(ImageRepositoryItem iri) {
        return this.path.compareTo(iri.getPath());
    }
}