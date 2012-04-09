package moller.javapeg.program.gui;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import moller.javapeg.program.imagerepository.ImageRepositoryItem;
import moller.javapeg.program.language.Language;
import moller.util.io.Status;

public class CustomCellRenderer	extends	JLabel implements ListCellRenderer<Object> {

	private static final long serialVersionUID = 1L;

	public CustomCellRenderer() {
		this.setOpaque(true);
	}

	public Component getListCellRendererComponent(JList<? extends Object> list, Object value, int index,
			                                      boolean isSelected, boolean cellHasFocus) {

		Status directoryStatus = ((ImageRepositoryItem)value).getPathStatus();
		String directoryPathValue = ((ImageRepositoryItem)value).getPath();

		this.setText(directoryPathValue);

		Language lang = Language.getInstance();

		switch (directoryStatus) {
		case DOES_NOT_EXIST:
			this.setBackground(new Color(255,127,127));
			this.setToolTipText(lang.get("category.categoriesModel.repositoryNotExists"));
			break;
		case EXISTS:
			this.setBackground(new Color(127,255,127));
			this.setToolTipText(lang.get("category.categoriesModel.repositoryExists"));
			break;
		case NOT_AVAILABLE:
			this.setBackground(new Color(251,231,128));
			this.setToolTipText(lang.get("category.categoriesModel.repositoryNotAvailable"));
			break;
		}

		if(isSelected) {
			this.setBorder(BorderFactory.createLineBorder(Color.GRAY));

		} else {
			this.setBorder(null);

		}
		return this;
	}
}
