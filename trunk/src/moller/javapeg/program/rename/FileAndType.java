package moller.javapeg.program.rename;

import java.io.File;

import moller.javapeg.program.enumerations.Type;

public class FileAndType {

    private File file;
    private Type type;

    public FileAndType(File file) {
        this.file = file;
        this.type = null;
    }

    public FileAndType(Type type) {
        this.file = null;
        this.type = type;
    }

    public FileAndType(File file, Type type) {
        this.file = file;
        this.type = type;
    }

    public File getFile() {
        return file;
    }

    public Type getType() {
        return type;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public void setType(Type type) {
        this.type = type;
    }
}