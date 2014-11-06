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
package moller.javapeg.program.gui.frames;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.JTabbedPane;

import moller.javapeg.StartJavaPEG;
import moller.javapeg.program.config.model.GUI.GUIWindow;
import moller.javapeg.program.contexts.imagemetadata.ImageMetaDataContext;
import moller.javapeg.program.datatype.ImageSize;
import moller.javapeg.program.gui.GUIDefaults;
import moller.javapeg.program.gui.components.lbchart2d.LBChart2DFactory;
import moller.javapeg.program.gui.components.lbchart2d.LBChart2DFactoryConfiguration;
import moller.javapeg.program.gui.frames.base.JavaPEGBaseFrame;

/**
 * This class displays different kind of meta information about the content in
 * the image repositories.
 *
 * @author Fredrik
 *
 */
public class ImageRepositoryStatisticsViewer extends JavaPEGBaseFrame {

    /**
     *
     */
    private static final long serialVersionUID = -8257537751440691196L;

    public ImageRepositoryStatisticsViewer() {
        this.createMainFrame();
        this.addListeners();
    }

    @Override
    protected void addListeners() {
        super.addListeners();

    }

    private void createMainFrame() {
       loadAndApplyGUISettings();

       InputStream imageStream = StartJavaPEG.class.getResourceAsStream("resources/images/viewtab/statistics.png");

       try {
           this.setIconImage(ImageIO.read(imageStream));
       } catch (IOException e) {
           getLogger().logERROR("Could not load icon: statistics.png");
           getLogger().logERROR(e);
       }

       this.getContentPane().setLayout(new BorderLayout());
       this.add(createStatiticsPanel(), BorderLayout.CENTER);
    }

    private JTabbedPane createStatiticsPanel() {

        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.add(createCameraModelStatistics(), 0);
        tabbedPane.add(createYearStatistics(), 1);
        tabbedPane.add(createDatesStatistics(), 2);
        tabbedPane.add(createHourStatistics(), 3);
        tabbedPane.add(createMinuteStatistics(), 4);
        tabbedPane.add(createSecondStatistics(), 5);
        tabbedPane.add(createImageSizeStatistics(), 6);
        tabbedPane.add(createISOStatistics(), 7);

        return tabbedPane;
    }

    private Component createISOStatistics() {
        ImageMetaDataContext imdc = ImageMetaDataContext.getInstance();

        Set<Integer> isos = imdc.getIsoValues();
        Integer[] isosAsArray = isos.toArray(new Integer[isos.size()]);

        int[] numberOfImages = new int[isosAsArray.length];
        for (int i = 0; i < isosAsArray.length; i++) {
            numberOfImages[i] = imdc.getNumberOfImagesForISO(isosAsArray[i]);
        }

        String[] isosAsStringArray = new String[isosAsArray.length];

        for (int i = 0; i < isosAsArray.length; i++) {
            isosAsStringArray[i] = isosAsArray[i].toString();
        }

        LBChart2DFactoryConfiguration lBC2DFC = new LBChart2DFactoryConfiguration();
        lBC2DFC.setLabelsAxisLabelsTexts(isosAsStringArray);
        lBC2DFC.setLabelsAxisTitleText("ISO");
        lBC2DFC.setNumberOfImages(numberOfImages);
        lBC2DFC.setObjectTitleText("Number of images per ISO");

        return LBChart2DFactory.create(lBC2DFC);
    }

    private Component createImageSizeStatistics() {
        ImageMetaDataContext imdc = ImageMetaDataContext.getInstance();

        Set<ImageSize> imageSizes = imdc.getImageSizeValues();
        ImageSize[] imageSizesAsArray = imageSizes.toArray(new ImageSize[imageSizes.size()]);

        int[] numberOfImages = new int[imageSizesAsArray.length];
        for (int i = 0; i < imageSizesAsArray.length; i++) {
            numberOfImages[i] = imdc.getNumberOfImagesForImageSize(imageSizesAsArray[i]);
        }

        String[] imageSizesAsStringArray = new String[imageSizesAsArray.length];

        for (int i = 0; i < imageSizesAsArray.length; i++) {
            imageSizesAsStringArray[i] = imageSizesAsArray[i].toString();
        }

        LBChart2DFactoryConfiguration lBC2DFC = new LBChart2DFactoryConfiguration();
        lBC2DFC.setLabelsAxisLabelsTexts(imageSizesAsStringArray);
        lBC2DFC.setLabelsAxisTitleText("Image Size");
        lBC2DFC.setNumberOfImages(numberOfImages);
        lBC2DFC.setObjectTitleText("Number of images per Image size");

        return LBChart2DFactory.create(lBC2DFC);
    }

    private Component createSecondStatistics() {
        ImageMetaDataContext imdc = ImageMetaDataContext.getInstance();

        Set<Integer> seconds = imdc.getSeconds();
        Integer[] secondsAsArray = seconds.toArray(new Integer[seconds.size()]);

        int[] numberOfImages = new int[secondsAsArray.length];
        for (int i = 0; i < secondsAsArray.length; i++) {
            numberOfImages[i] = imdc.getNumberOfImagesForSecond(secondsAsArray[i]);
        }

        String[] secondsAsStringArray = new String[secondsAsArray.length];

        for (int i = 0; i < secondsAsArray.length; i++) {
            secondsAsStringArray[i] = secondsAsArray[i].toString();
        }

        LBChart2DFactoryConfiguration lBC2DFC = new LBChart2DFactoryConfiguration();
        lBC2DFC.setLabelsAxisLabelsTexts(secondsAsStringArray);
        lBC2DFC.setLabelsAxisTitleText("Second");
        lBC2DFC.setNumberOfImages(numberOfImages);
        lBC2DFC.setObjectTitleText("Number of images per Second");

        return LBChart2DFactory.create(lBC2DFC);
    }

    private Component createMinuteStatistics() {
        ImageMetaDataContext imdc = ImageMetaDataContext.getInstance();

        Set<Integer> minutes = imdc.getMinutes();
        Integer[] minutesAsArray = minutes.toArray(new Integer[minutes.size()]);

        int[] numberOfImages = new int[minutesAsArray.length];
        for (int i = 0; i < minutesAsArray.length; i++) {
            numberOfImages[i] = imdc.getNumberOfImagesForMinute(minutesAsArray[i]);
        }

        String[] minutesAsStringArray = new String[minutesAsArray.length];

        for (int i = 0; i < minutesAsArray.length; i++) {
            minutesAsStringArray[i] = minutesAsArray[i].toString();
        }

        LBChart2DFactoryConfiguration lBC2DFC = new LBChart2DFactoryConfiguration();
        lBC2DFC.setLabelsAxisLabelsTexts(minutesAsStringArray);
        lBC2DFC.setLabelsAxisTitleText("Minute");
        lBC2DFC.setNumberOfImages(numberOfImages);
        lBC2DFC.setObjectTitleText("Number of images per Minute");

        return LBChart2DFactory.create(lBC2DFC);
    }

    private Component createDatesStatistics() {
        ImageMetaDataContext imdc = ImageMetaDataContext.getInstance();

        Set<Integer> dates = imdc.getDates();
        Integer[] datesAsArray = dates.toArray(new Integer[dates.size()]);

        int[] numberOfImages = new int[datesAsArray.length];
        for (int i = 0; i < datesAsArray.length; i++) {
            numberOfImages[i] = imdc.getNumberOfImagesForDate(datesAsArray[i]);
        }

        String[] datesAsStringArray = new String[datesAsArray.length];

        for (int i = 0; i < datesAsArray.length; i++) {
            datesAsStringArray[i] = datesAsArray[i].toString();
        }

        LBChart2DFactoryConfiguration lBC2DFC = new LBChart2DFactoryConfiguration();
        lBC2DFC.setLabelsAxisLabelsTexts(datesAsStringArray);
        lBC2DFC.setLabelsAxisTitleText("Day in month");
        lBC2DFC.setNumberOfImages(numberOfImages);
        lBC2DFC.setObjectTitleText("Number of images per Day in month");

        return LBChart2DFactory.create(lBC2DFC);
    }

    private Component createHourStatistics() {
        ImageMetaDataContext imdc = ImageMetaDataContext.getInstance();

        Set<Integer> hours = imdc.getHours();
        Integer[] hoursAsArray = hours.toArray(new Integer[hours.size()]);

        int[] numberOfImages = new int[hoursAsArray.length];
        for (int i = 0; i < hoursAsArray.length; i++) {
            numberOfImages[i] = imdc.getNumberOfImagesForHour(hoursAsArray[i]);
        }

        String[] hoursAsStringArray = new String[hoursAsArray.length];

        for (int i = 0; i < hoursAsArray.length; i++) {
            hoursAsStringArray[i] = hoursAsArray[i].toString();
        }

        LBChart2DFactoryConfiguration lBC2DFC = new LBChart2DFactoryConfiguration();
        lBC2DFC.setLabelsAxisLabelsTexts(hoursAsStringArray);
        lBC2DFC.setLabelsAxisTitleText("Hour");
        lBC2DFC.setNumberOfImages(numberOfImages);
        lBC2DFC.setObjectTitleText("Number of images per Hour");

        return LBChart2DFactory.create(lBC2DFC);
    }

    private Component createYearStatistics() {
        ImageMetaDataContext imdc = ImageMetaDataContext.getInstance();

        Set<Integer> years = imdc.getYears();
        Integer[] yearsAsArray = years.toArray(new Integer[years.size()]);

        int[] numberOfImages = new int[yearsAsArray.length];
        for (int i = 0; i < yearsAsArray.length; i++) {
            numberOfImages[i] = imdc.getNumberOfImagesForYear(yearsAsArray[i]);
        }

        String[] yearsAsStringArray = new String[yearsAsArray.length];

        for (int i = 0; i < yearsAsArray.length; i++) {
            yearsAsStringArray[i] = yearsAsArray[i].toString();
        }

        LBChart2DFactoryConfiguration lBC2DFC = new LBChart2DFactoryConfiguration();
        lBC2DFC.setLabelsAxisLabelsTexts(yearsAsStringArray);
        lBC2DFC.setLabelsAxisTitleText("Year");
        lBC2DFC.setNumberOfImages(numberOfImages);
        lBC2DFC.setObjectTitleText("Number of images per Year");

        return LBChart2DFactory.create(lBC2DFC);
    }

    private Component createCameraModelStatistics() {
        ImageMetaDataContext imdc = ImageMetaDataContext.getInstance();

        Set<String> cameraModels = imdc.getCameraModels();
        String[] cameraModelsAsArray = cameraModels.toArray(new String[cameraModels.size()]);

        int[] numberOfImages = new int[cameraModelsAsArray.length];
        for (int i = 0; i < cameraModelsAsArray.length; i++) {
            numberOfImages[i] = imdc.getNumberOfImagesForCameraModel(cameraModelsAsArray[i]);
        }

        LBChart2DFactoryConfiguration lBC2DFC = new LBChart2DFactoryConfiguration();
        lBC2DFC.setLabelsAxisLabelsTexts(cameraModelsAsArray);
        lBC2DFC.setLabelsAxisTitleText("Camera model");
        lBC2DFC.setNumberOfImages(numberOfImages);
        lBC2DFC.setObjectTitleText("Number of images per Cameramodel");

        return LBChart2DFactory.create(lBC2DFC);
    }

    @Override
    public GUIWindow getGUIWindowConfig() {
        return getConfiguration().getgUI().getImageRepositoryStatisticsViewer();
    }

    @Override
    public Dimension getDefaultSize() {
        return new Dimension(GUIDefaults.IMAGE_REPOSITORY_STATISTICS_VIEWER_WIDTH, GUIDefaults.IMAGE_REPOSITORY_STATISTICS_VIEWER_HEIGHT);
    }
}
