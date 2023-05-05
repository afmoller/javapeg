package moller.javapeg.program.gui;

import moller.javapeg.program.jpeg.JPEGThumbNail;
import moller.javapeg.program.jpeg.JPEGThumbNailRetriever;
import moller.javapeg.program.image.ImageUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageProducer;
import java.io.File;

public class ButtonIconUtil {

    public static void setSelectedThumbNailImage(AbstractButton button, boolean brightened, int percentage) {
        ImageIcon disabledImage = getSelectedIcon(button, brightened, percentage);
        button.setIcon(disabledImage);
    }

    public static ImageIcon getSelectedIcon(AbstractButton button, boolean brightened, int percentage) {
        GrayFilter filter = new GrayFilter(brightened, percentage);
        ImageProducer prod = new FilteredImageSource(((ImageIcon)button.getIcon()).getImage().getSource(), filter);
        Image image = Toolkit.getDefaultToolkit().createImage(prod);

        return new ImageIcon(image);
    }

    public static void setDeSelectedThumbNailImage(AbstractButton button) {
        setDeSelectedThumbNailImage(button, new File(button.getActionCommand()));
    }

    public static void setDeSelectedThumbNailImage(AbstractButton button, File image) {
        JPEGThumbNail jpegThumbNail = JPEGThumbNailRetriever.getInstance().retrieveThumbNailFrom(image);
        ImageIcon imageIcon = new ImageIcon(jpegThumbNail.getThumbNailData());
        button.setIcon(ImageUtil.rotateIfNeeded(imageIcon, jpegThumbNail.getMetaData()));
    }
}
