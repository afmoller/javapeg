package moller.javapeg.program;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class FileSelection implements Transferable {
    // the Image object which will be housed by the ImageSelection
    private final List<File> files;

    public FileSelection(List<File> files) {
        this.files = files;
    }

    // Returns the supported flavors of our implementation
    public DataFlavor[] getTransferDataFlavors() {
        return new DataFlavor[] {DataFlavor.javaFileListFlavor};
    }

    // Returns true if flavor is supported
    public boolean isDataFlavorSupported(DataFlavor flavor)    {
        return DataFlavor.javaFileListFlavor.equals(flavor);
    }

    // Returns Image object housed by Transferable object
    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException,IOException {
        if (!DataFlavor.javaFileListFlavor.equals(flavor)) {
            throw new UnsupportedFlavorException(flavor);
        }
        return files;
    }
}
