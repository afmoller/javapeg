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
package moller.javapeg.program.gui.icons;

public enum Icons {

    ADD("plus_16.png"),
    ARROW_JOIN("arrow_join.png"),
    AUTO_ADJUST_TO_WINDOW_SIZE("imageviewer/AutoAdjustToWindowSize16.gif"),
    BACK("imageviewer/Back16.gif"),
    CANCEL("cancel.png"),
    CENTER("imageviewer/Center16.png"),
    CONFIGURATION("configuration.gif"),
    COPY("viewtab/copy.gif"),
    DB("db.png"),
    DB_ADD("db_add.png"),
    EXPORT_IMAGE_LIST("viewtab/export.gif"),
    FIND("Find16.gif"),
    FORWARD("imageviewer/Forward16.gif"),
    HELP("Help16.gif"),
    IMAGE_RESIZER("ImageResizer16.gif"),
    JAVAPEG("javapeg.gif"),
    LOCK("lock.png"),
    MISSING("missing.png"),
    MOVE_DOWN("viewtab/down.gif"),
    MOVE_TO_BOTTOM("viewtab/bottom.gif"),
    MOVE_TO_TOP("viewtab/top.gif"),
    MOVE_UP("viewtab/up.gif"),
    NAVIGATION_DISABLED("imageviewer/NavigationImageDisabled16.png"),
    NAVIGATION_ENABLED("imageviewer/NavigationImageEnabled16.png"),
    OPEN("imageviewer/Open16.gif"),
    PLUS("plus_16.png"),
    PLAY("play.gif"),
    REMOVE("viewtab/remove.gif"),
    REMOVE_ALL("viewtab/removeall.gif"),
    ROTATE_AUTOMATIC("imageviewer/RotateAutomatic16.gif"),
    ROTATE_LEFT("imageviewer/RotateLeft16.gif"),
    ROTATE_RIGHT("imageviewer/RotateRight16.gif"),
    SAVE("save.gif"),
    STOP("imageviewer/Stop16.gif"),
    STATISTICS("viewtab/statistics.png"),
    VIEW_IMAGES("viewtab/view.gif"),
    ZOOM("imageviewer/Zoom16.gif"),
    ZOOM_IN("imageviewer/ZoomIn16.gif"),
    ZOOM_OUT("imageviewer/ZoomOut16.gif");

    private static final String PREFIX = "resources/images/";

    private final String resource;

    public String getResource() {
        return resource;
    }

    Icons(String resourceWithoutPrefix) {
        this.resource = PREFIX + resourceWithoutPrefix;
    }

    @Override
    public String toString() {
        return resource;
    }
}
