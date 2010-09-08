package moller.util.xml;

public class XMLAttribute {
	
	private String attributeName;
	private String attributeValue;
	
	public XMLAttribute(String attributeName, String attributeValue) {
		super();
		this.attributeName = attributeName;
		this.attributeValue = attributeValue;
	}

	public String getName() {
		return attributeName;
	}

	public String getValue() {
		return attributeValue;
	}

	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	public void setAttributeValue(String attributeValue) {
		this.attributeValue = attributeValue;
	}
}
