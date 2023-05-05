package moller.javapeg.program.config.model.thumbnail;

import javax.swing.*;

/**
 * This class persists the configuration properties of an {@link GrayFilter}
 * object, which is used in different places in the JavaPEG application.
 *
 * @author Fredrik
 *
 */
public class ThumbNailGrayFilter {

    /**
     * A value 0 to 100 where 100 means dark gray and 0 means light gray
     */
    private int percentage;

    /**
     * Indicates whether the pixels in the image should be brightened or not.
     */
    private boolean pixelsBrightened;

    public int getPercentage() {
        return percentage;
    }
    public boolean isPixelsBrightened() {
        return pixelsBrightened;
    }
    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }
    public void setPixelsBrightened(boolean pixelsBrightened) {
        this.pixelsBrightened = pixelsBrightened;
    }

}
