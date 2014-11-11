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
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.JTabbedPane;

import moller.javapeg.StartJavaPEG;
import moller.javapeg.program.config.model.GUI.GUIWindow;
import moller.javapeg.program.contexts.imagemetadata.ImageMetaDataContext;
import moller.javapeg.program.datatype.ExposureTime;
import moller.javapeg.program.datatype.ImageSize;
import moller.javapeg.program.gui.GUIDefaults;
import moller.javapeg.program.gui.frames.base.JavaPEGBaseFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

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
        tabbedPane.add(createExposureTimeStatistics(), 8);
        tabbedPane.add(createFNumberStatistics(), 9);
        tabbedPane.add(createWeekDayStatistics(), 10);

        return tabbedPane;
    }

    private ChartPanel createChart(CategoryDataset bardataset, String label) {
        JFreeChart barChart = ChartFactory.createBarChart("Number of images per " + label, label, "number of images", bardataset);

        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setName(label);
        chartPanel.setMouseWheelEnabled(true);

        final CategoryPlot plot = barChart.getCategoryPlot();
        plot.setBackgroundPaint(new Color(204, 204, 204));

        BarRenderer barRenderer = (BarRenderer) plot.getRenderer();
        barRenderer.setBarPainter(new StandardBarPainter());
        barRenderer.setSeriesPaint(0, new Color(102, 153, 204));
        barRenderer.setDrawBarOutline(true);
        barRenderer.setSeriesOutlinePaint(0, Color.BLACK);

        return chartPanel;
    }

    private <T extends Comparable<?>> DefaultCategoryDataset createDefaultCategoryDataset(Map<Object, Integer> keyToValueMapping, Set<T> sortedKeys, String rowKey) {
        DefaultCategoryDataset defaultCategoryDataset = new DefaultCategoryDataset();

        for (T sortedKey : sortedKeys) {
            defaultCategoryDataset.setValue(keyToValueMapping.get(sortedKey),rowKey ,sortedKey);
        }

        return defaultCategoryDataset;
    }

    private Component createWeekDayStatistics() {
        int[] weekdays = new int[]{0, 0, 0, 0, 0, 0, 0};

        ImageMetaDataContext imdc = ImageMetaDataContext.getInstance();

        Calendar calendar = Calendar.getInstance();

        Map<String, Long> dateTimeValues = imdc.getDateTimeValues();

        for (String imagePath : dateTimeValues.keySet()) {
            calendar.setTimeInMillis(dateTimeValues.get(imagePath));

            ++weekdays[calendar.get(Calendar.DAY_OF_WEEK) - 1];
        }

        DefaultCategoryDataset dataSet = new DefaultCategoryDataset();

        for (int i = 0; i < weekdays.length; i++) {
            dataSet.setValue(weekdays[i], "Weekdays", getWeekDayAsString(i));
        }

        return createChart(dataSet, "Weekdays");
    }

    private String getWeekDayAsString(int weekDayAsInt) {
        switch (weekDayAsInt) {
        case 0:
            // TODO: Fix hard coded string
            return "Sunday";
        case 1:
            // TODO: Fix hard coded string
            return "Monday";
        case 2:
            // TODO: Fix hard coded string
            return "Tuesday";
        case 3:
            // TODO: Fix hard coded string
            return "Wednesday";
        case 4:
            // TODO: Fix hard coded string
            return "Thursday";
        case 5:
            // TODO: Fix hard coded string
            return "Friday";
        case 6:
            // TODO: Fix hard coded string
            return "Saturday";
        default:
            // TODO: Fix hard coded string
            return "Unknown";
        }
    }

    private Component createFNumberStatistics() {
        ImageMetaDataContext imdc = ImageMetaDataContext.getInstance();

        Set<Double> fNumbers = imdc.getFNumberValues();

        Map<Object, Integer> fNumberToNumberOfImagesMapping = new HashMap<Object, Integer>();
        for (Double fNumber : fNumbers) {
            fNumberToNumberOfImagesMapping.put(fNumber, imdc.getNumberOfImagesForFNumber(fNumber));
        }

        DefaultCategoryDataset dataSet = createDefaultCategoryDataset(fNumberToNumberOfImagesMapping, fNumbers, "F-Number");

        return createChart(dataSet, "F-Number");
    }

    private Component createExposureTimeStatistics() {
        ImageMetaDataContext imdc = ImageMetaDataContext.getInstance();

        Set<ExposureTime> exposureTimes = imdc.getExposureTimeValues();

        Map<Object, Integer> isoToNumberOfImagesMapping = new HashMap<Object, Integer>();
        for (ExposureTime exposureTime : exposureTimes) {
            isoToNumberOfImagesMapping.put(exposureTime, imdc.getNumberOfImagesForExposureTime(exposureTime));
        }

        DefaultCategoryDataset dataSet = createDefaultCategoryDataset(isoToNumberOfImagesMapping, exposureTimes, "Exposure Time");

        return createChart(dataSet, "Exposure Time");
    }

    private Component createISOStatistics() {
        ImageMetaDataContext imdc = ImageMetaDataContext.getInstance();

        Set<Integer> isos = imdc.getIsoValues();

        Map<Object, Integer> isoToNumberOfImagesMapping = new HashMap<Object, Integer>();
        for (Integer iso : isos) {
            isoToNumberOfImagesMapping.put(iso, imdc.getNumberOfImagesForISO(iso));
        }

        DefaultCategoryDataset dataSet = createDefaultCategoryDataset(isoToNumberOfImagesMapping, isos, "ISO");

        return createChart(dataSet, "ISO");
    }

    private Component createImageSizeStatistics() {
        ImageMetaDataContext imdc = ImageMetaDataContext.getInstance();

        Set<ImageSize> imageSizes = imdc.getImageSizeValues();

        Map<Object, Integer> imageSizeToNumberOfImagesMapping = new HashMap<Object, Integer>();
        for (ImageSize imageSize : imageSizes) {
            imageSizeToNumberOfImagesMapping.put(imageSize, imdc.getNumberOfImagesForImageSize(imageSize));
        }

        DefaultCategoryDataset dataSet = createDefaultCategoryDataset(imageSizeToNumberOfImagesMapping, imageSizes, "Image Size");


        return createChart(dataSet, "Image Size");
    }

    private Component createSecondStatistics() {
        ImageMetaDataContext imdc = ImageMetaDataContext.getInstance();

        Set<Integer> seconds = imdc.getSeconds();

        Map<Object, Integer> secondToNumberOfImagesMapping = new HashMap<Object, Integer>();
        for (Integer second : seconds) {
            secondToNumberOfImagesMapping.put(second, imdc.getNumberOfImagesForSecond(second));
        }

        DefaultCategoryDataset dataSet = createDefaultCategoryDataset(secondToNumberOfImagesMapping, seconds, "Seconds");

        return createChart(dataSet, "Seconds");
    }

    private Component createMinuteStatistics() {
        ImageMetaDataContext imdc = ImageMetaDataContext.getInstance();

        Set<Integer> minutes = imdc.getMinutes();

        Map<Object, Integer> minuteToNumberOfImagesMapping = new HashMap<Object, Integer>();
        for (Integer minute : minutes) {
            minuteToNumberOfImagesMapping.put(minute, imdc.getNumberOfImagesForMinute(minute));
        }

        DefaultCategoryDataset dataSet = createDefaultCategoryDataset(minuteToNumberOfImagesMapping, minutes, "Minutes");

        return createChart(dataSet, "Minutes");
    }

    private Component createDatesStatistics() {
        ImageMetaDataContext imdc = ImageMetaDataContext.getInstance();

        Set<Integer> dates = imdc.getDates();

        Map<Object, Integer> dateToNumberOfImagesMapping = new HashMap<Object, Integer>();
        for (Integer date : dates) {
            dateToNumberOfImagesMapping.put(date, imdc.getNumberOfImagesForDate(date));
        }

        DefaultCategoryDataset dataSet = createDefaultCategoryDataset(dateToNumberOfImagesMapping, dates, "Day in month");

        return createChart(dataSet, "Day in month");
    }

    private Component createHourStatistics() {
        ImageMetaDataContext imdc = ImageMetaDataContext.getInstance();

        Set<Integer> hours = imdc.getHours();

        Map<Object, Integer> hourToNumberOfImagesMapping = new HashMap<Object, Integer>();
        for (Integer hour : hours) {
            hourToNumberOfImagesMapping.put(hour, imdc.getNumberOfImagesForHour(hour));
        }

        DefaultCategoryDataset dataSet = createDefaultCategoryDataset(hourToNumberOfImagesMapping, hours, "Hour");

        return createChart(dataSet, "Hour");
    }

    private Component createYearStatistics() {
        ImageMetaDataContext imdc = ImageMetaDataContext.getInstance();

        Set<Integer> years = imdc.getYears();

        Map<Object, Integer> yearToNumberOfImagesMapping = new HashMap<Object, Integer>();
        for (Integer year : years) {
            yearToNumberOfImagesMapping.put(year, imdc.getNumberOfImagesForYear(year));
        }

        DefaultCategoryDataset dataSet = createDefaultCategoryDataset(yearToNumberOfImagesMapping, years, "Year");

        return createChart(dataSet, "Year");
    }

    private Component createCameraModelStatistics() {
        ImageMetaDataContext imdc = ImageMetaDataContext.getInstance();

        Set<String> cameraModels = imdc.getCameraModels();

        Map<Object, Integer> yearToNumberOfImagesMapping = new HashMap<Object, Integer>();
        for (String cameraModel : cameraModels) {
            yearToNumberOfImagesMapping.put(cameraModel, imdc.getNumberOfImagesForCameraModel(cameraModel));
        }

        DefaultCategoryDataset dataSet = createDefaultCategoryDataset(yearToNumberOfImagesMapping, cameraModels, "Camera model");

        return createChart(dataSet, "Camera model");
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
