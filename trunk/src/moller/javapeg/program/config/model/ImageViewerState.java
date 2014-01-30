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
