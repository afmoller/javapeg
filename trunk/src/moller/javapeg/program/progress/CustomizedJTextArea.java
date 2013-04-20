package moller.javapeg.program.progress;

import javax.swing.JTextArea;

public class CustomizedJTextArea extends JTextArea {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public void appendAndScroll(String str) {
        super.append(str);
        this.setCaretPosition(this.getCaretPosition() + str.length());
    }
}
