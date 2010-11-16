package moller.javapeg.program.gui;

import java.awt.GridBagLayout;
import java.util.Set;

import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import moller.javapeg.program.GBHelper;
import moller.javapeg.program.datatype.ImageSize;
import moller.util.datatype.ShutterSpeed;

public class MetaDataValueSelector {

	private static final long serialVersionUID = 1L;

	private JPanel mainPanel = null;
	private JPanel operatorsPanel = null;
	
	private boolean hasOperators;
	
	private JComboBox fromValues = null;
	private JComboBox toValues = null;
	
	public MetaDataValueSelector(String title, boolean hasOperators) {
		
		this.cretaMainPane(title);
		
		if (hasOperators) {
			this.createOperatorsPanel();
		}
		
		this.hasOperators = hasOperators;
	}
	
	private void cretaMainPane(String title) {
		GBHelper positionMainPanel = new GBHelper();
		mainPanel = new JPanel(new GridBagLayout());
		
		JLabel titleLabel = new JLabel(title);
		JLabel fromLabel = new JLabel("From");
		JLabel toLabel = new JLabel("To");
		fromValues = new JComboBox();
		toValues = new JComboBox();
		
		mainPanel.add(titleLabel, positionMainPanel);
		mainPanel.add(fromLabel, positionMainPanel.nextRow());
		mainPanel.add(fromValues, positionMainPanel.nextCol().expandW());
		mainPanel.add(toLabel, positionMainPanel.nextRow());
		mainPanel.add(toValues, positionMainPanel.nextCol().expandW());
	}
	
	private void createOperatorsPanel() {
		GBHelper positionOperatorsPanel = new GBHelper();
		operatorsPanel = new JPanel(new GridBagLayout());
		
		ButtonGroup group = new ButtonGroup();

		JRadioButton less = new JRadioButton("<");
		JRadioButton equal = new JRadioButton("=");
		JRadioButton more = new JRadioButton(">");

		group.add(less);
		group.add(equal);
		group.add(more);

		operatorsPanel.add(new JLabel(" "), positionOperatorsPanel);
		operatorsPanel.add(less, positionOperatorsPanel.nextRow());
		operatorsPanel.add(equal, positionOperatorsPanel.nextCol());
		operatorsPanel.add(more, positionOperatorsPanel.nextCol());
	}

	public JPanel getMainPanel() {
		return mainPanel;
	}

	public JPanel getOperatorsPanel() {
		return operatorsPanel;
	}
	
	public boolean hasOperators() {
		return hasOperators;
	}
	
	public String getSelectedIntegerValue() {
		if (this.fromValues.getSelectedItem() instanceof Integer) {
			return Integer.toString((Integer)(this.fromValues.getSelectedItem()));			
		}  else {
			return "";
		}
	}
	
	public String getSelectedShutterSpeedValue() {
		if (this.fromValues.getSelectedItem() instanceof ShutterSpeed) {
			return ((ShutterSpeed)this.fromValues.getSelectedItem()).toString();			
		}  else {
			return "";
		}
	}
	
	public String getSelectedImageSizeValue() {
		if (this.fromValues.getSelectedItem() instanceof ImageSize) {
			return ((ImageSize)this.fromValues.getSelectedItem()).toString();			
		}  else {
			return "";
		}
	}
	
	public String getSelectedStringValue() {
		return (String)this.fromValues.getSelectedItem();
	}
	
	public void setIntegerValues(Set<Integer> values) {
		this.fromValues.addItem("");
		this.toValues.addItem("");
		for (Integer value : values) {
			this.fromValues.addItem(value);
			this.toValues.addItem(value);
		}
	}
	
	public void setStringValues(Set<String> values) {
		this.fromValues.addItem("");
		this.toValues.addItem("");
		for (String value : values) {
			this.fromValues.addItem(value);	
			this.toValues.addItem(value);
		}
	}

	public void setShutterSpeedValues(Set<ShutterSpeed> values) {
		this.fromValues.addItem("");
		this.toValues.addItem("");
		for (ShutterSpeed value : values) {
			this.fromValues.addItem(value);	
			this.toValues.addItem(value);
		}
	}
	
	public void setImageSizeValues(Set<ImageSize> values) {
		this.fromValues.addItem("");
		this.toValues.addItem("");
		for (ImageSize value : values) {
			this.fromValues.addItem(value);	
			this.toValues.addItem(value);
		}
	}
}
