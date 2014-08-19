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

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * This class acts as a container for a {@link File} object and a corresponding
 * timestamp as a long.
 * <p/>
 * The intent of this class is to speed up sorting of a {@link Collection} of
 * {@link File} objects, by the creation of a list of this type of objects and
 * then use the {@link Collections#sort(java.util.List)} on that {@link List} to
 * have it sorted in time, without having to ask the file object for the
 * timestamp for each comparison.
 *
 * @author Fredrik
 *
 */
public class FileAndTimeStampPair implements Comparable<FileAndTimeStampPair> {

    private final long timeStamp;
    private final File file;

    public FileAndTimeStampPair(File file, Long timeStamp) {
        this.file = file;
        this.timeStamp = timeStamp;
    }

    @Override
    public int compareTo(FileAndTimeStampPair other) {
        long otherTimeStamp = other.timeStamp;
        return timeStamp < otherTimeStamp ? -1 : timeStamp == otherTimeStamp ? 0 : 1;
    }

    /**
     * Returns the {@link File} object of this instance.
     *
     * @return
     */
    public File getFile() {
        return file;
    }
}
