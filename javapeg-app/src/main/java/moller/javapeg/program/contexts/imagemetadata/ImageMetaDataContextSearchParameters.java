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

import moller.javapeg.program.categories.Categories;

import java.util.Map;

public class ImageMetaDataContextSearchParameters {

    private String year;
    private String month;
    private String day;
    private String hour;
    private String minute;
    private String second;
    private String fNumber;
    private String cameraModel;
    private String comment;
    private Map<String, Categories> javaPegIdToCategoriesMap;
    private String iso;
    private String shutterSpeed;
    private String imageSize;
    private boolean[] ratings;

    /**
     * Indicates whether the selected categories (if any) should be evaluated
     * as AND or OR. Meaning that all selected categories must be assigned to
     * an image if the the image shall be added to the search result, if this
     * parameter is set to true.
     */
        private Map<String, Boolean> javaPegToAndCategoriesSearch;

    public ImageMetaDataContextSearchParameters() {
        super();
        this.year = null;
        this.month = null;
        this.day = null;
        this.hour = null;
        this.minute = null;
        this.second = null;
        this.fNumber = null;
        this.cameraModel = null;
        this.comment = null;
        this.javaPegIdToCategoriesMap = null;
        this.iso = null;
        this.shutterSpeed = null;
        this.imageSize = null;
        this.ratings = null;
        this.javaPegToAndCategoriesSearch = null;
    }

    public Map<String, Boolean> getJavaPegToAndCategoriesSearch() {
        return javaPegToAndCategoriesSearch;
    }

    public String getYear() {
        return year;
    }

    public String getMonth() {
        return month;
    }

    public String getDay() {
        return day;
    }

    public String getHour() {
        return hour;
    }

    public String getMinute() {
        return minute;
    }

    public String getSecond() {
        return second;
    }

    public String getFNumber() {
        return fNumber;
    }

    public String getCameraModel() {
        return cameraModel;
    }

    public String getComment() {
        return comment;
    }

    public Map<String, Categories> getJavaPegIdToCategoriesMap() {
        return javaPegIdToCategoriesMap;
    }

    public String getImageSize() {
        return imageSize;
    }

    public String getIso() {
        return iso;
    }

    public boolean[] getRatings() {
        return ratings;
    }

    public String getExposureTime() {
        return shutterSpeed;
    }

    public void setYear(String year) {
        if (!year.equals("")) {
            this.year = year;
        }
    }

    public void setMonth(String month) {
        if (!month.equals("")) {
            this.month = month;
        }
    }

    public void setDay(String day) {
        if (!day.equals("")) {
            this.day = day;
        }
    }

    public void setHour(String hour) {
        if (!hour.equals("")) {
            this.hour = hour;
        }
    }

    public void setMinute(String minute) {
        if (!minute.equals("")) {
            this.minute = minute;
        }
    }

    public void setSecond(String second) {
        if (!second.equals("")) {
            this.second = second;
        }
    }

    public void setFNumber(String fNumber) {
        if (!fNumber.equals("")) {
            this.fNumber = fNumber;
        }
    }

    public void setCameraModel(String cameraModel) {
        if (!cameraModel.equals("")) {
            this.cameraModel = cameraModel;
        }
    }

    public void setComment(String comment) {
        if (!comment.equals("")) {
            this.comment = comment;
        }
    }

    public void setJavaPegIdToCategoriesMap(Map<String, Categories> javaPegIdToCategoriesMap) {
        this.javaPegIdToCategoriesMap = javaPegIdToCategoriesMap;
    }

    public void setImageSize(String imageSize) {
        if (!imageSize.equals("")) {
            this.imageSize  = imageSize;
        }
    }

    public void setIso(String iso) {
        if (!iso.equals("")) {
            this.iso  = iso;
        }
    }

    public void setRating(boolean[] ratings) {
        this.ratings = ratings;
    }

    public void setShutterSpeed(String shutterSpeed) {
        if (!shutterSpeed.equals("")) {
            this.shutterSpeed  = shutterSpeed;
        }
    }

    public void setJavaPegToAndCategoriesSearch(Map<String, Boolean> javaPegToAndCategoriesSearch) {
        this.javaPegToAndCategoriesSearch = javaPegToAndCategoriesSearch;
    }
}
