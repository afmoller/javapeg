package moller.javapeg.program.gui;

import java.awt.GridBagLayout;
import java.util.Set;

import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import moller.javapeg.program.GBHelper;
import moller.util.datatype.ShutterSpeed;

public class MetaDataValueSelector {

	private static final long serialVersionUID = 1L;

	private JPanel mainPanel = null;
	private JPanel operatorsPanel = null;
	
	private boolean hasOperators;
	
	private JComboBox values = null;
	
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
		values = new JComboBox();
		
		mainPanel.add(titleLabel, positionMainPanel);
		mainPanel.add(values, positionMainPanel.nextRow().expandW());
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
	
	public void setIntegerValues(Set<Integer> values) {
		this.values.addItem("");
		for (Integer value : values) {
			this.values.addItem(value);	
		}
	}
	
	public void setStringValues(Set<String> values) {
		this.values.addItem("");
		for (String value : values) {
			this.values.addItem(value);	
		}
	}

	public void setShutterSpeedValues(Set<ShutterSpeed> values) {
		this.values.addItem("");
		for (ShutterSpeed value : values) {
			this.values.addItem(value);	
		}
	}
}
