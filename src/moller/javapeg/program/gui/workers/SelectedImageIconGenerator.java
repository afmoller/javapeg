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
package moller.javapeg.program.gui.workers;

import moller.javapeg.program.config.Config;
import moller.javapeg.program.config.model.Configuration;
import moller.javapeg.program.config.model.thumbnail.ThumbNailGrayFilter;
import moller.javapeg.program.gui.ButtonIconUtil;
import moller.javapeg.program.gui.LoadedThumbnails;
import moller.javapeg.program.logger.Logger;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SelectedImageIconGenerator extends SwingWorker<Void, String> {

    private static Configuration configuration;
    private static Logger logger;

    LoadedThumbnails loadedThumbnails;
    Map<File, ImageIcon> imageFileToSelectedImageMapping;

    public SelectedImageIconGenerator(LoadedThumbnails loadedThumbnails, Map<File, ImageIcon> imageFileToSelectedImageMapping) {
        logger = Logger.getInstance();
        configuration = Config.getInstance().get();

        this.loadedThumbnails = loadedThumbnails;
        this.imageFileToSelectedImageMapping = imageFileToSelectedImageMapping;
    }

    @Override
    protected Void doInBackground() throws Exception {
        logger.logDEBUG("Starting to create selected images for the " + loadedThumbnails.size() + " loaded thumbnails");

        ThumbNailGrayFilter grayFilter = configuration.getThumbNail().getGrayFilter();
        final int percentage = grayFilter.getPercentage();
        final boolean pixelsBrightened = grayFilter.isPixelsBrightened();

        ExecutorService newCachedThreadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        for (final JToggleButton loadedThumbnail : loadedThumbnails) {
            final String actionCommand = loadedThumbnail.getActionCommand();
            final File actionCommandAsFile = new File(actionCommand);

            newCachedThreadPool.submit(new Runnable() {

                @Override
                public void run() {
                    if (!imageFileToSelectedImageMapping.containsKey(actionCommandAsFile)) {
                        Image selectedIcon = ButtonIconUtil.getSelectedIcon(loadedThumbnail, pixelsBrightened, percentage);
                        imageFileToSelectedImageMapping.put(actionCommandAsFile, new ImageIcon(selectedIcon));
                    }
                }
            });
        }

        logger.logDEBUG("Waiting for treads to finnish");
        newCachedThreadPool.shutdown();
        newCachedThreadPool.awaitTermination(1, TimeUnit.MINUTES);
        logger.logDEBUG("Finished waiting for treads to finnish");

        logger.logDEBUG("Finished to create selected images for thumbnails");
        return null;
    }
}
