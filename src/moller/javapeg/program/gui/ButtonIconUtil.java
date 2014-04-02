package moller.javapeg.program.gui;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageProducer;
import java.io.File;

import javax.swing.AbstractButton;
import javax.swing.GrayFilter;
import javax.swing.ImageIcon;
import javax.swing.JToggleButton;

import moller.javapeg.program.jpeg.JPEGThumbNailRetriever;

public class ButtonIconUtil {

    public static void setSelectedThumbNailImage(AbstractButton button) {
        GrayFilter filter = new GrayFilter(true, 35);
        ImageProducer prod = new FilteredImageSource(((ImageIcon)button.getIcon()).getImage().getSource(), filter);
        Image disabledImage = Toolkit.getDefaultToolkit().createImage(prod);

        button.setIcon(new ImageIcon(disabledImage));
    }

    public static void setDeSelectedThumbNailImage(JToggleButton toggleButton) {
        toggleButton.setIcon(new ImageIcon(JPEGThumbNailRetriever.getInstance().retrieveThumbNailFrom(new File(toggleButton.getActionCommand())).getThumbNailData()));
    }

}
