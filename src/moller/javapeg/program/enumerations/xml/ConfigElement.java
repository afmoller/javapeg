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
package moller.javapeg.program.enumerations.xml;

import java.util.HashMap;
import java.util.Map;

/**
 * This class contains constants for each XML element and element attributes
 * that exists in the configuration XML file. The constants are used when the
 * configuration file is read and written.
 *
 * @author Fredrik
 *
 */
public enum ConfigElement implements IXmlElement {

    HIGHEST_USED_ID("highest-used-id"),
    CONFIG_VIEWER("configViewer"),
    HELP_VIEWER("helpViewer"),
    IMAGE_SEARCH_RESULT_VIEWER("imageSearchResultViewer"),
    IMAGE_VIEWER("imageViewer"),
    MAIN("main"),
    X_LOCATION("xLocation"),
    Y_LOCATION("yLocation"),
    WIDTH("width"),
    HEIGHT("height"),
    SPLIT_PANE("splitPane"),
    ID("id"),
    INSTANCE("instance"),
    JAVAPEG_CLIENT_ID("javapegClientId"),
    JAVAPEG_CLIENT_ID_ATTRIBUTE("javapegclientid"),
    DISPLAY_NAME("displayname"),
    AUTOMATIC_SELECTION("automaticSelection"),
    GUI_LANGUAGE_ISO_6391("gUILanguageISO6391"),
    FILE_NAME("fileName"),
    DEVELOPER_MODE("developerMode"),
    LEVEL("level"),
    ROTATE("rotate"),
    ROTATE_SIZE("rotateSize"),
    ROTATE_ZIP("rotateZip"),
    TIMESTAMP_FORMAT("timestampFormat"),
    CAMERA_MODEL_NAME_MAXIMUM_LENGTH("cameraModelNameMaximumLength"),
    CREATE_THUMBNAILS("createThumbNails"),
    PATH_DESTINATION("pathDestination"),
    PATH_SOURCE("pathSource"),
    PROGRESS_LOG_TIMESTAMP_FORMAT("progressLogTimestampFormat"),
    TEMPLATE_FILE_NAME("templateFileName"),
    TEMPLATES_FILE_NAME("templatesFileName"),
    TEMPLATE("template"),
    TEMPLATE_SUB_DIRECTORY_NAME("templateSubDirectoryName"),
    TEMPLATES_SUB_DIRECTORY_NAME("templatesSubDirectoryName"),
    USE_LAST_MODIFIED_DATE("useLastModifiedDate"),
    USE_LAST_MODIFIED_TIME("useLastModifiedTime"),
    EXCEPTIONS("exceptions"),
    PATH("path"),
    ALLWAYS_ADD("allwaysAdd"),
    NEVER_ADD("neverAdd"),
    PATHS("paths"),
    CATEGORIES("categories"),
    PREVIEW("preview"),
    USE_EMBEDDED_THUMBNAIL("useEmbeddedThumbnail"),
    ADD_TO_REPOSITORY_POLICY("addToRepositoryPolicy"),
    OR_RADIO_BUTTON_IS_SELECTED("orRadioButtonIsSelected"),
    WARN_WHEN_REMOVE("warnWhenRemove"),
    WARN_WHEN_REMOVE_WITH_SUB_CATEGORIES("warnWhenRemoveWithSubCategories"),
    CACHE("cache"),
    CREATION("creation"),
    ALGORITHM("algorithm"),
    IF_MISSING_OR_CORRUPT("ifMissingOrCorrupt"),
    ENABLED("enabled"),
    MAX_SIZE("maxSize"),
    OVERVIEW_STATE("overviewState"),
    IMAGE_SEARCH_RESULT_STATE("imageSearchResultState"),
    OVERVIEW_IMAGE_VIEWER_STATE("overviewImageViewerState"),
    ATTACH_VERSION_INFORMATION("attachVersionInformation"),
    TIMEOUT("timeout"),
    URL_VERSION("urlVersion"),
    URL_VERSION_INFORMATION("urlVersionInformation"),
    CONFIG("config"),
    IMPORTEDCATEGORIES("importedcategories"),
    GUI("gui"),
    LANGUAGE("language"),
    LOGGING("logging"),
    RENAME_IMAGES("renameImages"),
    REPOSITORY("repository"),
    TAG_IMAGES("tagImages"),
    THUMBNAIL("thumbNail"),
    TOOL_TIPS("toolTips"),
    UPDATES_CHECKER("updatesChecker"),
    VERTICAL("vertical"),
    THUMB_NAIL_META_DATA_PANEL("thumbNailMetaDataPanel"),
    PREVIEW_COMMENT_CATEGORIES_RATING("previewCommentCategoriesRating"),
    PREVIEW_AND_COMMENT("previewAndComment"),
    IMAGE_META_DATA("imageMetaData"),
    IMAGE_RESIZER("imageResizer"),
    RESIZE_IMAGES("resizeImages"),
    SELECTED_QUALITY_INDEX("selectedQualityIndex"),
    IMAGE_CONFLICT_VIEWER("imageConflictViewer"),
    AUTOMATICALLY_ROTATE_IMAGES("automaticallyRotateImages"),
    AUTOMATICALLY_RESIZE_IMAGES("automaticallyResizeImages"),
    IMAGE_VIEWER_STATE("imageViewerState"),
    RESIZE_QUALITY("resizeQuality"),
    SHOW_NAVIGATION_IMAGE("showNavigationImage"),
    GRAYFILTER("grayfilter"),
    PERCENTAGE("percentage"),
    PIXELS_BRIGHTENED("pixelsBrightened"),
    CATEGORY("category"),
    SLIDE_SHOW_DELAY_IN_SECONDS("slideShowDelayInSeconds"),
    IMAGE_SEARCH_RESULT_VIEWER_STATE("imageSearchResultViewerState"),
    NUMBER_OF_IMAGES_TO_DISPLAY("numberOfImagesToDisplay"),
    IMAGE_REPOSITORY_STATISTICS_VIEWER("imageRepositoryStatisticsViewer"),
    ISO_FILTERS("isoFilters"),
    ISO_FILTER("isoFilter"),
    CAMERA_MODEL("cameraModel"),
    METADATA("metadata"),
    EXPOSURETIME_FILTERS("exposureTimeFilters"),
    EXPOSURETIME_FILTER("exposureTimeFilter"),
    FILTER_MASK("filterMask"),
    SELECTED_MAIN_GUI_TAB("selectedMainGUITab"),
    MAIN_TO_IMAGELIST("mainToImageList"),
    NO_OPERATION("");

    private String elementValue;

    ConfigElement(String elementValue) {
        this.elementValue = elementValue;
    }

    public String getElementValue() {
        return elementValue;
    }

    public static ConfigElement getEnum(String strVal) {
        if(!strValMap.containsKey(strVal)) {
            return NO_OPERATION;
        }
        return strValMap.get(strVal);
    }

    private static final Map<String, ConfigElement> strValMap = new HashMap<>();

    static {
        for(final ConfigElement en : ConfigElement.values()) {
            strValMap.put(en.elementValue, en);
        }
    }
}
