// File   : layoutDemos/layoutDemoGB/Gap.java
// Purpose: Gaps for use in GridBagLayout (or any other).
//          Library alternatives are available in the Box class.
// Author : Fred Swartz - January 30, 2007 - Placed in public domain.

package moller.javapeg.program;

import java.awt.Dimension;

import javax.swing.JComponent;

public class Gap extends JComponent {

    private static final long serialVersionUID = 1L;

    //============================================================== constructor
    /* Creates filler with minimum size, but expandable infinitely. */
    public Gap() {
        Dimension min = new Dimension(0, 0);
        Dimension max = new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
        setMinimumSize(min);
        setPreferredSize(min);
        setMaximumSize(max);
    }

    //============================================================== constructor
    /* Creates rigid filler. */
    public Gap(int size) {
        Dimension dim = new Dimension(size, size);
        setMinimumSize(dim);
        setPreferredSize(dim);
        setMaximumSize(dim);
    }
}
