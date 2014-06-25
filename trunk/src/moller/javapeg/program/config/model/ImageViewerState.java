/*******************************************************************************
 * Copyright (c) JavaPEG developers
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package moller.javapeg.program.config.model;

import org.imgscalr.Scalr.Method;

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
 * 3: resizeQuality: Holds the state of which {@link Method} quality to use when
 * an image is resized in the image viewer.
 *
 * 4: showNavigationImage: Defines whether or not an navigation image shall be
 * displayed in the image in the image viewer.
 *
 * 5: slideShowDelayInSeconds: Defines how many seconds of delay it shall be
 * between two images when the slide show is running
 *
 * @author Fredrik
 *
 */
public class ImageViewerState {

    private boolean automaticallyRotateImages;
    private boolean automaticallyResizeImages;
    private Method resizeQuality;
    private boolean showNavigationImage;
    private int slideShowDelayInSeconds;

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
    public Method getResizeQuality() {
        return resizeQuality;
    }
    public void setResizeQuality(Method resizeQuality) {
        this.resizeQuality = resizeQuality;
    }
    public boolean isShowNavigationImage() {
        return showNavigationImage;
    }
    public void setShowNavigationImage(boolean showNavigationImage) {
        this.showNavigationImage = showNavigationImage;
    }
    public void setSlideShowDelay(int slideShowDelayInSeconds) {
        this.slideShowDelayInSeconds = slideShowDelayInSeconds;
    }
    public int getSlideShowDelayInSeconds() {
        return slideShowDelayInSeconds;
    }
}
