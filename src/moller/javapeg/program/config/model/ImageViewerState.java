package moller.javapeg.program.config.model;

/**
 * This class represents the persisted states of the image viewer in the JavaPEG
 * application.
 *
 * 1: automaticallyRotateImages: Hold the state if whether or not the
 * automatically rotate image button shall be activated or not when the image
 * viewer is opened.
 *
 * 2: automaticallyResizeImages: Hold the state if whether or not the
 * automatically resize image button shall be activated or not when the image
 * viewer is opened.
 *
 * @author Fredrik
 *
 */
public class ImageViewerState {

    private boolean automaticallyRotateImages;
    private boolean automaticallyResizeImages;

    public boolean isAutomaticallyRotateImages() {
        return automaticallyRotateImages;
    }
    public boolean isAutomaticallyResizeImages() {
        return automaticallyResizeImages;
    }
    public void setAutomaticallyRotateImages(boolean automaticallyRotateImages) {
        this.automaticallyRotateImages = automaticallyRotateImages;
    }
    public void setAutomaticallyResizeImages(boolean automaticallyResizeImages) {
        this.automaticallyResizeImages = automaticallyResizeImages;
    }
}
