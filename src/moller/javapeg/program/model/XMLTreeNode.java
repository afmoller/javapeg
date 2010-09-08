package moller.javapeg.program.model;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

public class XMLTreeNode extends Object {

	Element element;
	public XMLTreeNode(Element element) {
		this.element = element;
	}
	public Element getElement() {
		return element;
	}
	public String toString() {
		return element.getAttribute("value");
	}
	public String getText() {
		NodeList list = element.getChildNodes();
		for (int i=0 ; i<list.getLength() ; i++) {
			if (list.item(i) instanceof Text) {
				return ((Text)list.item(i)).getTextContent();
			}
		}
		return "";
	}
	
	public String getAttribute(String attribute) {
		return element.getAttribute(attribute);
	}
	
	public void setAttribute(String name, String value) {
		element.setAttribute(name, value);
	}
	
	public void remove() {
		Node parent = element.getParentNode();
		parent.removeChild(element);
		
	}
}
