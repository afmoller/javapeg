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
package moller.javapeg.program.jpeg;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

import moller.javapeg.program.config.Config;

public class JPEGThumbNailCache {

    /**
     * The static singleton instance of this class.
     */
    private static JPEGThumbNailCache instance;

    /**
     * The actual map containing the cached thumbnails.
     */
    private Map<File, JPEGThumbNail> thumbNailLRUCache;

    /**
     * The maximum number of entries in the cache.
     */
    private static final int MAX_ENTRIES = Config.getInstance().get().getThumbNail().getCache().getMaxSize();

    /**
     * Private constructor. It creates an LinkedHashMap
     * configured to work as an LRU cache.
     */
    private JPEGThumbNailCache() {
        thumbNailLRUCache = new LinkedHashMap<File, JPEGThumbNail>(128, 0.75f, true) {

            private static final long serialVersionUID = 1L;

            @Override
            protected boolean removeEldestEntry(Map.Entry<File, JPEGThumbNail> entry) {
                return size() > MAX_ENTRIES;
            }
        };
    }

    /**
     * Accessor method for this Singleton class.
     *
     * @return the singleton instance of this class.
     */
    public static JPEGThumbNailCache getInstance() {
        if (instance != null)
            return instance;
        synchronized (JPEGThumbNailCache.class) {
            if (instance == null) {
                instance = new JPEGThumbNailCache();
            }
            return instance;
        }
    }

    /**
     * Add a thumbnail to the cache data structure.
     *
     * @param jpegFile is the key in the cache and links the JPEG
     *        file with the thumbnail of the JPEG file
     *
     * @param thumbNailData is the actual data bytes of the thumbnail
     *        connected to the key value in the cache Map data
     *        structure (jpegFile).
     */
    public void add(File jpegFile, JPEGThumbNail thumbNail) {
        thumbNailLRUCache.put(jpegFile, thumbNail);
    }

    /**
     * Return the thumbnail related to a JPEG file that is stored
     * in the LRU cache data structure.
     *
     * @param jpegFile the JPEG file for which the thumbnail
     *        data shall be returned.
     *
     * @return An byte array containing the thumbnail data related
     *         to the parameter jpegFile, or null if there is no
     *         entry for the value of jpegFile.
     */
    public JPEGThumbNail get(File jpegFile) {
        return thumbNailLRUCache.get(jpegFile);
    }

    /**
     * Answers the question whether or not an thumbnail for an JPEG file is
     * present in the cahce or not.
     *
     * @param jpegFile
     *            is the file to investigate if the corresponding thumbnail
     *            exists in the cache or not.
     * @return true if the corresponding thumbnail for the file which is defined
     *         by the parameter jpegFile exists in the cache otherwise false.
     */
    public boolean exists (File jpegFile) {
        return thumbNailLRUCache.containsKey(jpegFile);
    }

    /**
     * The number of entries in the cache
     *
     * @return the number of entries in the cache.
     */
    public int getCurrentSize() {
        return thumbNailLRUCache.size();
    }

    /**
     * The maximum number of entries that this cache is configured to keep.
     *
     * @return the maximum number of entries that this cache is configured to
     *         keep.
     */
    public int getMaxSize() {
        return MAX_ENTRIES;
    }

    /**
     * Remove all entries in the cache.
     */
    public void clear() {
        thumbNailLRUCache.clear();
    }
}