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
    @SuppressWarnings("serial")
    private JPEGThumbNailCache() {
        thumbNailLRUCache = new LinkedHashMap<File, JPEGThumbNail>(128, 0.75f, true) {
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

    public boolean exists (File jpegFile) {
        return thumbNailLRUCache.containsKey(jpegFile);
    }

    public int getCurrentSize() {
        return thumbNailLRUCache.size();
    }

    public int getMaxSize() {
        return MAX_ENTRIES;
    }

    public void clear() {
        thumbNailLRUCache.clear();
    }
}