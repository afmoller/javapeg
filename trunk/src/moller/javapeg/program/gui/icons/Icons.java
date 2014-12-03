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
    OPEN("open.gif"),
    ARROW_JOIN("arrow_join.png"),
    REMOVE("viewtab/remove.gif"),
    IMAGE_RESIZER("ImageResizer16.gif"),
    COPY("viewtab/copy.gif"),
    MOVE_TO_BOTTOM("viewtab/bottom.gif"),
    VIEW_IMAGES("viewtab/view.gif"),
    MOVE_TO_TOP("viewtab/top.gif"),
    MOVE_DOWN("viewtab/down.gif"),
    MOVE_UP("viewtab/up.gif"),
    EXPORT_IMAGE_LIST("viewtab/export.gif"),
    SAVE_IMAGE_LIST("viewtab/save.gif"),
    REMOVE_ALL("viewtab/removeall.gif");




    private static final String PREFIX = "resources/images/";

    private final String resource;

    public String getResource() {
        return resource;
    }

    Icons(String resourceWithoutPrefix) {
        this.resource = PREFIX + resourceWithoutPrefix;
    }
}
