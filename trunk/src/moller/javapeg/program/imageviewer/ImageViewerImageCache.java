package moller.javapeg.program.imageviewer;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import moller.javapeg.program.config.Config;

public class ImageViewerImageCache {

    private final Map<Dimension, Map<File, BufferedImage>> sizeToImageCache;

    private int currentNumberOfCacheEntries;

    private final Lock lock = new ReentrantLock();

    /**
     * The static singleton instance of this class.
     */
    private static ImageViewerImageCache instance;



    /**
     * Private constructor.
     */
    private ImageViewerImageCache() {
        sizeToImageCache = new HashMap<Dimension, Map<File,BufferedImage>>();
    }


    /**
     * The maximum number of entries in the cache.
     */
    private static final int MAX_ENTRIES = 10;




    /**
     * Accessor method for this Singleton class.
     *
     * @return the singleton instance of this class.
     */
    public static ImageViewerImageCache getInstance() {
        if (instance != null)
            return instance;
        synchronized (Config.class) {
            if (instance == null) {
                instance = new ImageViewerImageCache();
            }
            return instance;
        }
    }

    public void add(Dimension dimension, File fileName, BufferedImage image) {
        try {
            lock.lock();
            if (sizeToImageCache.get(dimension) == null) {
                sizeToImageCache.put(dimension, new HashMap<File, BufferedImage>());
            }
            sizeToImageCache.get(dimension).put(fileName, image);
            currentNumberOfCacheEntries++;
        } finally {
            lock.unlock();
        }
    }

    public void clear() {
        try {
            lock.lock();
            sizeToImageCache.clear();
            currentNumberOfCacheEntries = 0;
        } finally {
            lock.unlock();
        }
    }

    public BufferedImage get(Dimension dimension, File fileName) {
        try {
            lock.lock();
            Map<File, BufferedImage> fileToBufferedImageMapping = sizeToImageCache.get(dimension);

            if (fileToBufferedImageMapping == null) {
                return null;
            }

            return fileToBufferedImageMapping.get(fileName);
        } finally {
            lock.unlock();
        }
    }

    private void removeOldestEntry() {

    }



}
