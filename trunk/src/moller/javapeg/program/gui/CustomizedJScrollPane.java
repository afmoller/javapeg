package moller.javapeg.program.gui;

import java.awt.Component;

import javax.swing.JScrollPane;

public class CustomizedJScrollPane extends JScrollPane {

	private static final long serialVersionUID = 1L;

	public CustomizedJScrollPane(Component view) {

		this.setViewportView(view);

		this.horizontalScrollBar.setUnitIncrement(10);
		this.verticalScrollBar.setUnitIncrement(10);

		this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		this.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	}
}
