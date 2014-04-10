package moller.javapeg.program.gui.main;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JToggleButton;

import moller.javapeg.program.gui.ButtonIconUtil;

public class SelectedThumbnails extends ArrayList<JToggleButton> {

    /**
     *
     */
    private static final long serialVersionUID = 6281196769790574197L;

    public void set(JToggleButton jToggleButton, boolean brightened, int percentage) {
        clearSelections();
        addSelection(jToggleButton, brightened, percentage);
    }

    public void clearSelections() {
        if (size() > 0) {
            Iterator<JToggleButton> iterator = iterator();
            while ( iterator.hasNext()) {
                JToggleButton jToggleButton = iterator.next();
                ButtonIconUtil.setDeSelectedThumbNailImage(jToggleButton);
                jToggleButton.setSelected(false);
            }
        }
        clear();
    }

    public void addSelection(JToggleButton jToggleButton, boolean brightened, int percentage) {
        ButtonIconUtil.setSelectedThumbNailImage(jToggleButton, brightened, percentage);
        add(jToggleButton);
    }

    public void removeSelection(JToggleButton jToggleButton) {
        remove(jToggleButton);
        ButtonIconUtil.setDeSelectedThumbNailImage(jToggleButton);
        jToggleButton.setSelected(false);
    }

    public List<File> getAsFiles() {
        Iterator<JToggleButton> iterator = iterator();
        List<File> files = new ArrayList<File>();
        while (iterator.hasNext()) {
            String actionCommand = iterator.next().getActionCommand();
            files.add(new File(actionCommand));
        }
        return files;
    }
}
