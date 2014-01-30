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
package moller.javapeg.program.imagerepository;

import java.io.File;

import moller.util.io.Status;

public class ImageRepositoryItem implements Comparable<ImageRepositoryItem>{

    private File path;
    private Status pathStatus;

    public ImageRepositoryItem() {
        this.path = null;
        this.pathStatus = null;
    }

    public ImageRepositoryItem(File path, Status pathStatus) {
        super();
        this.path = path;
        this.pathStatus = pathStatus;
    }

    public File getPath() {
        return path;
    }

    public Status getPathStatus() {
        return pathStatus;
    }

    public void setPath(File path) {
        this.path = path;
    }

    public void setPathStatus(Status pathStatus) {
        this.pathStatus = pathStatus;
    }

    @Override
    public int compareTo(ImageRepositoryItem iri) {
        return this.path.compareTo(iri.getPath());
    }
}