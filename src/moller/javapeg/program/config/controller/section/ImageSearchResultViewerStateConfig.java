package moller.javapeg.program.config.controller.section;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import moller.javapeg.program.config.controller.ConfigElement;
import moller.javapeg.program.config.model.ImageSearchResultViewerState;
import moller.util.string.Tab;
import moller.util.xml.XMLUtil;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ImageSearchResultViewerStateConfig {

    public static ImageSearchResultViewerState getImageSearchResultViewerStateConfig(Node imageSearchResultViewerStateNode) {

        ImageSearchResultViewerState imageSearchResultViewerState = new ImageSearchResultViewerState();

        NodeList childNodes = imageSearchResultViewerStateNode.getChildNodes();

        for (int i = 0; i < childNodes.getLength(); i++) {
            Node node = childNodes.item(i);

            switch (node.getNodeName()) {
            case ConfigElement.NUMBER_OF_IMAGES_TO_DISPLAY:
                imageSearchResultViewerState.setNumberOfImagesToDisplay(Integer.valueOf(node.getTextContent()));
                break;
            default:
                break;
            }
        }
        return imageSearchResultViewerState;
    }

    public static void writeImageSearchResultViewerStateConfig(ImageSearchResultViewerState imageSearchResultViewerState, Tab baseIndent, XMLStreamWriter xmlsw) throws XMLStreamException {
        //  IMAGE SEARCH RESULT VIEWER STATE start
        XMLUtil.writeElementStartWithLineBreak(ConfigElement.IMAGE_SEARCH_RESULT_VIEWER_STATE, baseIndent, xmlsw);

        XMLUtil.writeElementWithIndentAndLineBreak(ConfigElement.NUMBER_OF_IMAGES_TO_DISPLAY, Tab.FOUR, Integer.toString(imageSearchResultViewerState.getNumberOfImagesToDisplay()), xmlsw);

        //  IMAGE SEARCH RESULT VIEWER STATE end
        XMLUtil.writeElementEndWithLineBreak(xmlsw, baseIndent);
    }

}
