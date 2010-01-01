package moller.javapeg.program.gui;
/**
 * This class was created : 2009-12-25 by Fredrik Möller
 * Latest changed         : 2009-12-28 by Fredrik Möller
 */

import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import moller.javapeg.program.C;
import moller.util.io.Status;
import moller.util.string.StringUtil;

public class CustomCellRenderer	extends	JLabel implements ListCellRenderer {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public CustomCellRenderer() {
		this.setOpaque(true);
	}
     
	public Component getListCellRendererComponent(JList list, Object value, int index, 
			                                      boolean isSelected, boolean cellHasFocus) {
		
		String stringValue = value.toString();
		
		stringValue =  StringUtil.reverse(stringValue);
						
		String [] stringValueParts = stringValue.split(C.DIRECTORY_STATUS_DELIMITER, 2);
		
		String directoryStatus    = StringUtil.reverse(stringValueParts[0]);
		String directoryPathValue = StringUtil.reverse(stringValueParts[1]);
		
		this.setText(directoryPathValue);
		
		if (directoryStatus.equals(Status.DOES_NOT_EXIST.toString())) {
			this.setBackground(new Color(255,127,127));
			this.setToolTipText("Image Repository path does not exist.");
		} else if (directoryStatus.equals(Status.EXISTS.toString())) {
			this.setBackground(new Color(127,255,127));
			this.setToolTipText("Image Repository path exists.");
		} else if (directoryStatus.equals(Status.NOT_AVAILABLE.toString())) {
			this.setBackground(new Color(251,231,128));
			this.setToolTipText("Image Repository is not available.");
		}
		
		if(isSelected) {
			this.setBorder(BorderFactory.createLineBorder(Color.GRAY));
			
		} else {
			this.setBorder(null);
			
		}
		return this;
	}
}