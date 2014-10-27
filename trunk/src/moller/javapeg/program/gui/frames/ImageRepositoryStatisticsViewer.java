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

import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import moller.javapeg.program.config.Config;
import moller.javapeg.program.config.model.Configuration;
import moller.javapeg.program.config.model.GUI.GUI;
import moller.javapeg.program.gui.GUIDefaults;
import moller.javapeg.program.language.Language;
import moller.javapeg.program.logger.Logger;
import moller.util.gui.Screen;

/**
 * This class displays different kind of meta information about the content in
 * the image repositories.
 *
 * @author Fredrik
 *
 */
public class ImageRepositoryStatisticsViewer extends JFrame {

    /**
     *
     */
    private static final long serialVersionUID = -8257537751440691196L;

    private final Configuration configuration;
    private final Logger logger;
    private final Language lang;

    public ImageRepositoryStatisticsViewer() {
        configuration = Config.getInstance().get();
        logger = Logger.getInstance();
        lang   = Language.getInstance();

        this.createMainFrame();
        this.addListeners();
    }

    private void addListeners() {
        // TODO Auto-generated method stub

    }

    private void createMainFrame() {
        GUI gUI = configuration.getgUI();

        Rectangle sizeAndLocation = gUI.getImageRepositoryStatisticsViewer().getSizeAndLocation();

        this.setSize(sizeAndLocation.getSize());

        Point xyFromConfig = new Point(sizeAndLocation.getLocation());

        if (Screen.isVisibleOnScreen(sizeAndLocation)) {
            this.setLocation(xyFromConfig);
            this.setSize(sizeAndLocation.getSize());
        } else {
            JOptionPane.showMessageDialog(null, lang.get("errormessage.maingui.locationError"), lang.get("errormessage.maingui.errorMessageLabel"), JOptionPane.ERROR_MESSAGE);
            logger.logERROR("Could not set location of Image Repository Statistics GUI to: x = " + xyFromConfig.x + " and y = " + xyFromConfig.y + " since that is outside of available screen size.");

            this.setLocation(0,0);
            this.setSize(GUIDefaults.IMAGE_REPOSITORY_STATISTICS_VIEWER_WIDTH, GUIDefaults.IMAGE_REPOSITORY_STATISTICS_VIEWER_HEIGHT);
        }

        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception e){
            logger.logERROR("Could not set desired Look And Feel for Image Repository Statistics Viewer GUI");
            logger.logERROR(e);
        }


    }

}
