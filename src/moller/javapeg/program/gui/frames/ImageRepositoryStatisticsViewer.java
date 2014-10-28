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

import java.awt.Dimension;

import moller.javapeg.program.config.model.GUI.GUIWindow;
import moller.javapeg.program.gui.GUIDefaults;
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

    private void addListeners() {
        // TODO Auto-generated method stub

    }

    private void createMainFrame() {
       loadAndApplyGUISettings();


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
