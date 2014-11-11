package moller.javapeg.program.gui;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageProducer;
import java.io.File;

import javax.swing.AbstractButton;
import javax.swing.GrayFilter;
import javax.swing.ImageIcon;

import moller.javapeg.program.jpeg.JPEGThumbNailRetriever;

public class ButtonIconUtil {

    public static void setSelectedThumbNailImage(AbstractButton button, boolean brightened, int percentage) {
        Image disabledImage = getSelectedIcon(button, brightened, percentage);
        button.setIcon(new ImageIcon(disabledImage));
    }

    public static Image getSelectedIcon(AbstractButton button, boolean brightened, int percentage) {
        GrayFilter filter = new GrayFilter(brightened, percentage);
        ImageProducer prod = new FilteredImageSource(((ImageIcon)button.getIcon()).getImage().getSource(), filter);
        return Toolkit.getDefaultToolkit().createImage(prod);
    }

    public static void setDeSelectedThumbNailImage(AbstractButton button) {
        setDeSelectedThumbNailImage(button, new File(button.getActionCommand()));
    }

    public static void setDeSelectedThumbNailImage(AbstractButton button, File image) {
        button.setIcon(new ImageIcon(JPEGThumbNailRetriever.getInstance().retrieveThumbNailFrom(image).getThumbNailData()));
    }

}
