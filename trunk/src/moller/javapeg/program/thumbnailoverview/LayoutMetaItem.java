package moller.javapeg.program.thumbnailoverview;
/**
 * This class was created : 2009-03-20 by Fredrik Möller
 * Latest changed         : 
 */

public class LayoutMetaItem {
	
	private String classString;
	private String labelString;
	private String metaString;
	
	public LayoutMetaItem(String classString, String labelString, String metaString) {
		super();
		this.classString = classString;
		this.labelString = labelString;
		this.metaString = metaString;
	}

	public String getClassString() {
		return classString;
	}

	public String getLabelString() {
		return labelString;
	}

	public String getMetaString() {
		return metaString;
	}

	public void setClassString(String classString) {
		this.classString = classString;
	}

	public void setLabelString(String labelString) {
		this.labelString = labelString;
	}

	public void setMetaString(String metaString) {
		this.metaString = metaString;
	}
}