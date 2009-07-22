package moller.javapeg.program.gui;
/**
* This class was created : 2009-05-26 by Fredrik Möller
* Latest changed         : 2009-05-27 by Fredrik Möller
*                        : 2009-07-22 by Fredrik Möller
*/

import java.awt.Color;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import moller.javapeg.program.GBHelper;
import moller.javapeg.program.Gap;
import moller.javapeg.program.language.Language;

public class VariablesPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private Language lang = Language.getInstance();
	
	public VariablesPanel() {
		
		this.setLayout(new GridBagLayout());
		
		JPanel variablesAndInformationBackground = new JPanel(new GridBagLayout());
		variablesAndInformationBackground.setBackground(Color.WHITE);
		variablesAndInformationBackground.setBorder(BorderFactory.createLineBorder(new Color(127,157,185)));
		
		JPanel variables = new JPanel(new GridBagLayout());
		variables.setBackground(Color.WHITE);
				
		JPanel information = new JPanel(new GridBagLayout());
		information.setBackground(Color.WHITE);
				
		JLabel variablesLabel = new JLabel(lang.get("labels.variables"));
		variablesLabel.setForeground(Color.GRAY);

		JLabel dateLabelA         = new JLabel("%" + lang.get("variable.pictureDateVariable") + "%");
		JLabel dateLabelB         = new JLabel("=  " + lang.get("variable.pictureDate") + " (*)");
		JLabel timeLabelA         = new JLabel("%" + lang.get("variable.pictureTimeVariable") + "%");
		JLabel timeLabelB         = new JLabel("=  " + lang.get("variable.pictureTime"));
		JLabel modelLabelA        = new JLabel("%" + lang.get("variable.cameraModelVariable") + "%");
		JLabel modelLabelB        = new JLabel("=  " + lang.get("variable.cameraModel") + " (*)");
		JLabel shutterSpeedLabelA = new JLabel("%" + lang.get("variable.shutterSpeedVariable") + "%");
		JLabel shutterSpeedLabelB = new JLabel("=  " + lang.get("variable.shutterSpeed"));
		JLabel isoLabelA          = new JLabel("%" + lang.get("variable.isoValueVariable") + "%");
		JLabel isoLabelB          = new JLabel("=  " + lang.get("variable.isoValue"));
		JLabel widthLabelA        = new JLabel("%" + lang.get("variable.pictureWidthVariable") + "%");
		JLabel widthLabelB        = new JLabel("=  " + lang.get("variable.pictureWidth"));
		JLabel heigthLabelA       = new JLabel("%" + lang.get("variable.pictureHeightVariable") + "%");
		JLabel heigthLabelB       = new JLabel("=  " + lang.get("variable.pictureHeight"));
		JLabel apertureLabelA     = new JLabel("%" + lang.get("variable.apertureValueVariable") + "%");
		JLabel apertureLabelB     = new JLabel("=  " + lang.get("variable.apertureValue"));
		JLabel todaysDateLabelA   = new JLabel("%" + lang.get("variable.dateOftodayVariable") + "%");
		JLabel todaysDateLabelB   = new JLabel("=  " + lang.get("variable.dateOftoday") + " (*)");
		JLabel sourceNameLabelA   = new JLabel("%" + lang.get("variable.sourceNameVariable") + "%");
		JLabel sourceNameLabelB   = new JLabel("=  " + lang.get("variable.sourceName"));

		JLabel infoLabelA = new JLabel(lang.get("variable.comment.infoLabelA"));
		JLabel infoLabelB = new JLabel(lang.get("variable.comment.infoLabelB"));
		
		GBHelper posVariables = new GBHelper();
		variables.add(new Gap(2), posVariables.nextRow());
		variables.add(dateLabelA, posVariables.nextRow());
		variables.add(dateLabelB, posVariables.nextCol());
		variables.add(timeLabelA, posVariables.nextRow());
		variables.add(timeLabelB, posVariables.nextCol());
		variables.add(modelLabelA, posVariables.nextRow());
		variables.add(modelLabelB, posVariables.nextCol());
		variables.add(shutterSpeedLabelA, posVariables.nextRow());
		variables.add(shutterSpeedLabelB, posVariables.nextCol());
		variables.add(isoLabelA, posVariables.nextRow());
		variables.add(isoLabelB, posVariables.nextCol());
		variables.add(widthLabelA, posVariables.nextRow());
		variables.add(widthLabelB, posVariables.nextCol());
		variables.add(heigthLabelA, posVariables.nextRow());
		variables.add(heigthLabelB, posVariables.nextCol());
		variables.add(apertureLabelA, posVariables.nextRow());
		variables.add(apertureLabelB, posVariables.nextCol());
		variables.add(todaysDateLabelA, posVariables.nextRow());
		variables.add(todaysDateLabelB, posVariables.nextCol());
		variables.add(sourceNameLabelA, posVariables.nextRow());
		variables.add(sourceNameLabelB, posVariables.nextCol());
				
		GBHelper posInformation = new GBHelper();
		information.add(infoLabelA, posInformation);
		information.add(infoLabelB, posInformation.nextRow());
		
		GBHelper posVariablesAndInformation = new GBHelper();
		variablesAndInformationBackground.add(new Gap(5), posVariablesAndInformation);
		variablesAndInformationBackground.add(new Gap(5), posVariablesAndInformation.nextCol());
		variablesAndInformationBackground.add(variables, posVariablesAndInformation.nextRow().nextCol());
		variablesAndInformationBackground.add(new Gap(5), posVariablesAndInformation.nextRow());
		variablesAndInformationBackground.add(information, posVariablesAndInformation.nextRow().nextCol());
		variablesAndInformationBackground.add(new Gap(5), posVariablesAndInformation.nextRow());
		variablesAndInformationBackground.add(new Gap(5), posVariablesAndInformation.nextCol().nextCol());
			
		GBHelper posMain = new GBHelper();
		this.add(variablesLabel, posMain);
		this.add(variablesAndInformationBackground, posMain.nextRow());
	}
}