package moller.javapeg.program.gui;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JToggleButton;


/**
 * This class is an extension of an typed {@link ArrayList}. It contains
 * {@link JToggleButton} objects and it has methods that returns a {@link List}
 * of {@link File} type for all thumbnails ({@link JToggleButton}) that are
 * selected.
 *
 * It also offers methods to make an {@link JToggleButton} selected or
 * de-selected. This means that the {@link JToggleButton} will have it's state
 * of {@link JToggleButton#setSelected(boolean)} set to true or false, and the
 * displayed thumbnail will be grayed out if selected and not grayed out if
 * de-selected.
 *
 * @author Fredrik
 *
 */
public class LoadedThumbnails extends ArrayList<JToggleButton> {

    /**
     *
     */
    private static final long serialVersionUID = 6281196769790574197L;

    /**
     * Sets an {@link JToggleButton} as selected, and clears the selection of
     * any other already selected {@link JToggleButton} objects.
     *
     * @param jToggleButton
     *            is the {@link JToggleButton} to select.
     * @param brightened
     * @param percentage
     */
    public void set(JToggleButton jToggleButton, boolean brightened, int percentage) {
        clearSelections();
        addSelection(jToggleButton, brightened, percentage);
    }

    /**
     * Clears the selection of any selected {@link JToggleButton}. That means
     * every button will be de-selected.
     */
    public void clearSelections() {
        if (size() > 0) {
            Iterator<JToggleButton> iterator = iterator();
            while ( iterator.hasNext()) {
                JToggleButton jToggleButton = iterator.next();
                if (jToggleButton.isSelected()) {
                    ButtonIconUtil.setDeSelectedThumbNailImage(jToggleButton);
                    jToggleButton.setSelected(false);
                }
            }
        }
    }

    /**
     * Adds a {@link JToggleButton} to an potentially already set list of
     * {@link JToggleButton} objects.
     *
     * @param jToggleButton
     *            is the {@link JToggleButton} to set as selected.
     * @param brightened
     * @param percentage
     */
    public void addSelection(JToggleButton jToggleButton, boolean brightened, int percentage) {
        ButtonIconUtil.setSelectedThumbNailImage(jToggleButton, brightened, percentage);
        jToggleButton.setSelected(true);
    }

    /**
     * De-selects a specific {@link JToggleButton}
     *
     * @param jToggleButton
     *            is the {@link JToggleButton} to de-select.
     */
    public void removeSelection(JToggleButton jToggleButton) {
        ButtonIconUtil.setDeSelectedThumbNailImage(jToggleButton);
        jToggleButton.setSelected(false);
    }

    /**
     * Returns the action command for an {@link JToggleButton} as an {@link File}
     * object for all selected {@link JToggleButton}
     *
     * @return a {@link List} of {@link File} objects for the action commands of
     *         all selected {@link JToggleButton}s.
     */
    public List<File> getSelectedAsFileObjects() {
        Iterator<JToggleButton> iterator = iterator();
        List<File> files = new ArrayList<File>();
        while (iterator.hasNext()) {
            JToggleButton toggleButton = iterator.next();

            if (toggleButton.isSelected()) {
                String actionCommand = toggleButton.getActionCommand();
                files.add(new File(actionCommand));
            }
        }
        return files;
    }

    /**
     * Returns the action command for an {@link JToggleButton} as an {@link File}
     * object for all {@link JToggleButton}
     *
     * @return a {@link List} of {@link File} objects for the action commands of
     *         all selected {@link JToggleButton}s.
     */
    public List<File> getAllAsFileObjects() {
        Iterator<JToggleButton> iterator = iterator();
        List<File> files = new ArrayList<File>();
        while (iterator.hasNext()) {
            String actionCommand = iterator.next().getActionCommand();
            files.add(new File(actionCommand));
        }
        return files;
    }

    /**
     * Returns the {@link File} object of an {@link JToggleButton}
     *
     * @param jToggleButton
     *            is the {@link JToggleButton} to return the associated
     *            {@link File} object for.
     * @return the for an {@link JToggleButton} associated {@link File} object
     *         or null if the {@link JToggleButton} cannot be found.
     */
    public File getFileObject(JToggleButton jToggleButton) {
        int index = indexOf(jToggleButton);
        if (index == -1) {
            return null;
        } else {
            return new File(get(index).getActionCommand());
        }
    }

    public void addActionListener(ActionListener actionListener) {
        Iterator<JToggleButton> iterator = iterator();
        while (iterator.hasNext()) {
            iterator.next().addActionListener(actionListener);
        }
    }

    public void addMouseListener(MouseAdapter mouseAdapter) {
        Iterator<JToggleButton> iterator = iterator();
        while (iterator.hasNext()) {
            iterator.next().addMouseListener(mouseAdapter);
        }
    }


}
