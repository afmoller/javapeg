package moller.javapeg.program.gui;

import java.awt.GridBagLayout;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import moller.javapeg.program.GBHelper;
import moller.javapeg.program.Gap;

public class MetaDataValueSelectorComplex {

	private static final long serialVersionUID = 1L;

	private JPanel mainPanel = null;
	private JPanel operatorsPanel = null;
	
	private boolean hasOperators;
	
	private JComboBox firstValues = null;
	private JComboBox secondValues = null;
	private JComboBox thirdValues = null;
	
	public MetaDataValueSelectorComplex(String firstTitle, String secondTitle, String thirdTitle, boolean hasOperators) {
		
		this.cretaMainPane(firstTitle, secondTitle, thirdTitle);
		
		if (hasOperators) {
			this.createOperatorsPanel();
		}
		
		this.hasOperators = hasOperators;
	}
	
	private void cretaMainPane(String firstTitle, String secondTitle, String thirdTitle) {
		GBHelper positionMainPanel = new GBHelper();
		mainPanel = new JPanel(new GridBagLayout());
		
		JLabel firstLabel = new JLabel(firstTitle);
		JLabel secondLabel = new JLabel(secondTitle);
		JLabel thirdLabel = new JLabel(thirdTitle);
		firstValues = new JComboBox();
		secondValues = new JComboBox();
		thirdValues = new JComboBox();
		
		mainPanel.add(firstLabel, positionMainPanel);
		mainPanel.add(new Gap(2), positionMainPanel.nextCol());
		mainPanel.add(secondLabel, positionMainPanel.nextCol());
		mainPanel.add(new Gap(2), positionMainPanel.nextCol());
		mainPanel.add(thirdLabel, positionMainPanel.nextCol());
		mainPanel.add(firstValues, positionMainPanel.nextRow());
		mainPanel.add(new Gap(2), positionMainPanel.nextCol());
		mainPanel.add(secondValues, positionMainPanel.nextCol());
		mainPanel.add(new Gap(2), positionMainPanel.nextCol());
		mainPanel.add(thirdValues, positionMainPanel.nextCol().expandW());
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
	
	public void setFirstValues(Set<String> values) {
		this.firstValues.addItem("");
		for (String value : values) {
			this.firstValues.addItem(value);	
		}
	}
	
	public void setSecondValues(Set<String> values) {
		this.secondValues.addItem("");
		for (String value : values) {
			this.secondValues.addItem(value);	
		}
	}
	
	public void setThirdValues(Set<String> values) {
		this.thirdValues.addItem("");
		for (String value : values) {
			this.thirdValues.addItem(value);	
		}
	}
	
	public Set<String> initiateContinuousSet(int start, int end) {
		Set<String> set = new LinkedHashSet<String>();
		for (int i = start; i <= end; i++) {
			if (i < 10) {
				set.add("0" + i);
			} else {
				set.add(Integer.toString(i));
			}
		}
		return set;
	}
}
