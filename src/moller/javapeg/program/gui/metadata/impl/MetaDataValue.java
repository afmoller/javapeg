package moller.javapeg.program.gui.metadata.impl;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

import moller.javapeg.program.GBHelper;

public class MetaDataValue extends JPanel {

	private static final long serialVersionUID = 1L;

	private JTextField textField;
	
	private JButton clearTextFieldButton;
	
	public MetaDataValue(MouseListener mouseListener, String name) {
		super();
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.addMouseListener(mouseListener);
		textField.setName(name);
		textField.setMinimumSize(new Dimension(50, textField.getSize().height));
		textField.setPreferredSize(new Dimension(200, textField.getSize().height));
		
		Color c = UIManager.getDefaults().getColor("TextField.background");
		if (null != c) {
			Color color = new Color(c.getRed(), c.getGreen(), c.getBlue());
			textField.setBackground(color);
		}
		
		clearTextFieldButton = new JButton("x");
		clearTextFieldButton.addActionListener(new ActionListener() {
               
            public void actionPerformed(ActionEvent e) {
            	textField.setText("");
            	textField.setToolTipText("");
            }
        });
		
		this.setLayout(new GridBagLayout());
		
		GBHelper posBackground = new GBHelper();
		
		this.add(textField, posBackground.expandW());
		this.add(clearTextFieldButton, posBackground.nextCol());
	}
	
	public String getValue() {
		return textField.getText();
	}
}
