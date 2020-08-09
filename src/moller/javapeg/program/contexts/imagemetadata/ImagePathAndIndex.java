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
package moller.javapeg.program.contexts.imagemetadata;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ImagePathAndIndex {

    /**
     * The static singleton instance of this class.
     */
    private static ImagePathAndIndex instance;

    private final Map<Integer, String> indexAndImagePath;
    private final Map<String, Integer> imagePathAndIndex;
    private int imagePathIndex;

    /**
     * Private constructor.
     */
    private ImagePathAndIndex() {
        indexAndImagePath = new HashMap<Integer, String>();
        imagePathAndIndex = new HashMap<String, Integer>();
        imagePathIndex = 0;
    }

    /**
     * Accessor method for this Singleton class.
     *
     * @return the singleton instance of this class.
     */
    public static ImagePathAndIndex getInstance() {
        if (instance != null)
            return instance;
        synchronized (ImagePathAndIndex.class) {
            if (instance == null) {
                instance = new ImagePathAndIndex();
            }
            return instance;
        }
    }

    public Integer getIndexForImagePath(String imagePath) {
        Integer index = imagePathAndIndex.get(imagePath);

        if (null == index) {
            imagePathIndex++;
            index = Integer.valueOf(imagePathIndex);
            imagePathAndIndex.put(imagePath, index);
            indexAndImagePath.put(index, imagePath);
        }
        return index;
    }

    public Set<String> getImagePaths() {
        return imagePathAndIndex.keySet();
    }

    public String getImagePathForIndex(Integer index) {
        return indexAndImagePath.get(index);
    }
}
