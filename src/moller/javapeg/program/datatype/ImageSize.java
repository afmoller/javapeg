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
package moller.javapeg.program.datatype;

public class ImageSize implements Comparable<ImageSize>{

    private int height;
    private int width;

    public ImageSize(int height, int width) {
        super();
        this.height = height;
        this.width = width;
    }

    public ImageSize(String imageSizeValueString) {
        String[] parts = imageSizeValueString.split("x");

        this.width = Integer.parseInt(parts[0].trim());
        this.height = Integer.parseInt(parts[1].trim());
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public String toString() {
        return Integer.toString(this.getWidth()) + " x " + Integer.toString(this.getHeight());
    }

    @Override
    public int compareTo(ImageSize other) {

        long thisImageSize  = this.getWidth() * this.getHeight();
        long otherImageSize = other.getWidth() * other.getHeight();

        if (thisImageSize < otherImageSize) {
            return -1;
        } else if (thisImageSize == otherImageSize) {
            if (this.getWidth() < other.getWidth()) {
                return -1;
            } else if (this.getWidth() == other.getWidth()) {
                return 0;
            } else {
                return 1;
            }
        } else {
            return 1;
        }
    }
}
