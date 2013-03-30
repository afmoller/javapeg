package moller.util.xml;

public class XMLAttribute {

	private String attributeName;
	private Object attributeValue;

	public XMLAttribute(String attributeName, Object attributeValue) {
		super();
		this.attributeName = attributeName;
		this.attributeValue = attributeValue;
	}

	public String getName() {
		return attributeName;
	}

	public String getValue() {
		return attributeValue.toString();
	}

	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	public void setAttributeValue(Object attributeValue) {
		this.attributeValue = attributeValue;
	}
}
