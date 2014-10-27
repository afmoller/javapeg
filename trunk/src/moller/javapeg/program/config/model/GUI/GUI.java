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
package moller.javapeg.program.config.model.GUI;

public class GUI {

    private GUIWindow main;
    private GUIWindow imageViewer;
    private GUIWindow imageSearchResultViewer;
    private GUIWindow configViewer;
    private GUIWindow helpViewer;
    private GUIWindow imageResizer;
    private GUIWindow imageConflictViewer;
    private GUIWindow imageRepositoryStatisticsViewer;

    public GUIWindow getMain() {
        return main;
    }
    public GUIWindow getImageViewer() {
        return imageViewer;
    }
    public GUIWindow getImageSearchResultViewer() {
        return imageSearchResultViewer;
    }
    public GUIWindow getConfigViewer() {
        return configViewer;
    }
    public GUIWindow getHelpViewer() {
        return helpViewer;
    }
    public GUIWindow getImageResizer() {
        return imageResizer;
    }
    public GUIWindow getImageConflictViewer() {
        return imageConflictViewer;
    }
    public GUIWindow getImageRepositoryStatisticsViewer() {
        return imageRepositoryStatisticsViewer;
    }
    public void setMain(GUIWindow main) {
        this.main = main;
    }
    public void setImageViewer(GUIWindow imageViewer) {
        this.imageViewer = imageViewer;
    }
    public void setImageSearchResultViewer(GUIWindow imageSearchResultViewer) {
        this.imageSearchResultViewer = imageSearchResultViewer;
    }
    public void setConfigViewer(GUIWindow configViewer) {
        this.configViewer = configViewer;
    }
    public void setHelpViewer(GUIWindow helpViewer) {
        this.helpViewer = helpViewer;
    }
    public void setImageResizer(GUIWindow imageResizer) {
        this.imageResizer = imageResizer;
    }
    public void setImageConflictViewer(GUIWindow imageConflictViewer) {
        this.imageConflictViewer = imageConflictViewer;
    }
    public void setImageRepositoryStatisticsViewer(GUIWindow imageRepositoryStatisticsViewer) {
        this.imageRepositoryStatisticsViewer = imageRepositoryStatisticsViewer;
    }
}
