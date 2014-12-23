/*******************************************************************************
 * Copyright (c) JavaPEG developers
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package moller.javapeg.program.config.controller.section;

import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import moller.javapeg.program.config.controller.ConfigElement;
import moller.javapeg.program.config.model.metadata.ExposureTimeFilter;
import moller.javapeg.program.config.model.metadata.ISOFilter;
import moller.javapeg.program.config.model.metadata.MetaData;
import moller.javapeg.program.enumerations.ExposureTimeFilterMask;
import moller.javapeg.program.enumerations.ISOFilterMask;
import moller.util.string.Tab;
import moller.util.xml.XMLUtil;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class MetaDataConfig {

    public static MetaData getMetaDataConfig(Node metaDataNode) {
        MetaData metaData = new MetaData();

        NodeList childNodes = metaDataNode.getChildNodes();

        for (int i = 0; i < childNodes.getLength(); i++) {
            Node node = childNodes.item(i);

            switch (node.getNodeName()) {
            case ConfigElement.ISO_FILTERS:
                metaData.setIsoFilters(getIsoFilters(node));
                break;
            case ConfigElement.EXPOSURETIME_FILTERS:
                metaData.setExposureTimeFilters(getExposureTimeFilters(node));
                break;
            }
        }
        return metaData;
    }

    private static List<ExposureTimeFilter> getExposureTimeFilters(Node exposureTimesFiltersNode) {
        List<ExposureTimeFilter> exposureTimeFilters = new ArrayList<ExposureTimeFilter>();

        NodeList childNodes = exposureTimesFiltersNode.getChildNodes();

        for (int i = 0; i < childNodes.getLength(); i++) {
            Node node = childNodes.item(i);

            switch (node.getNodeName()) {
            case ConfigElement.EXPOSURETIME_FILTER:
                exposureTimeFilters.add(createExposureTimeFilter(node));
                break;
            default:
                break;
            }
        }
        return exposureTimeFilters;
    }

    private static List<ISOFilter> getIsoFilters(Node isoFiltersNode) {
        List<ISOFilter> isoFilters = new ArrayList<ISOFilter>();

        NodeList childNodes = isoFiltersNode.getChildNodes();

        for (int i = 0; i < childNodes.getLength(); i++) {
            Node node = childNodes.item(i);

            switch (node.getNodeName()) {
            case ConfigElement.ISO_FILTER:
                isoFilters.add(createIsoFilter(node));
                break;
            default:
                break;
            }
        }
        return isoFilters;
    }

    private static ISOFilter createIsoFilter(Node isoFilterNode) {
        NodeList childNodes = isoFilterNode.getChildNodes();

        String cameraModel = "";
        ISOFilterMask isoFilterMask = ISOFilterMask.NO_MASK;

        for (int i = 0; i < childNodes.getLength(); i++) {
            Node node = childNodes.item(i);

            switch (node.getNodeName()) {
            case ConfigElement.CAMERA_MODEL:
                cameraModel = node.getTextContent();
                break;
            case ConfigElement.ISO_MASK:
                isoFilterMask = ISOFilterMask.valueOf(node.getTextContent());
                break;
            default:
                break;
            }
        }

        ISOFilter isoFilter = new ISOFilter();
        isoFilter.setCameraModel(cameraModel);
        isoFilter.setIsoFilter(isoFilterMask);

        return isoFilter;
    }

    private static ExposureTimeFilter createExposureTimeFilter(Node exposureTimeFilterNode) {
        NodeList childNodes = exposureTimeFilterNode.getChildNodes();

        String cameraModel = "";
        ExposureTimeFilterMask exposureTimeFilterMask = ExposureTimeFilterMask.NO_MASK;

        for (int i = 0; i < childNodes.getLength(); i++) {
            Node node = childNodes.item(i);

            switch (node.getNodeName()) {
            case ConfigElement.CAMERA_MODEL:
                cameraModel = node.getTextContent();
                break;
            case ConfigElement.EXPOSURETIME_MASK:
                exposureTimeFilterMask = ExposureTimeFilterMask.valueOf(node.getTextContent());
                break;
            default:
                break;
            }
        }

        ExposureTimeFilter exposureTimeFilter = new ExposureTimeFilter();
        exposureTimeFilter.setCameraModel(cameraModel);
        exposureTimeFilter.setExposureTimeFilterMask(exposureTimeFilterMask);

        return exposureTimeFilter;
    }

    public static void writeLanguageConfig(MetaData metaData, Tab baseIndent, XMLStreamWriter xmlsw) throws XMLStreamException {
        //  METADATA start
        XMLUtil.writeElementStartWithLineBreak(ConfigElement.METADATA, baseIndent, xmlsw);

        // ISO FILTERS start
        XMLUtil.writeElementStartWithLineBreak(ConfigElement.ISO_FILTERS, Tab.FOUR, xmlsw);

        writeIsoFilters(metaData.getIsoFilters(), xmlsw);

        // ISO FILTERS end
        XMLUtil.writeElementEndWithLineBreak(xmlsw, Tab.FOUR);

        // EXPOSURETIME FILTERS start
        XMLUtil.writeElementStartWithLineBreak(ConfigElement.EXPOSURETIME_FILTERS, Tab.FOUR, xmlsw);

        writeExposureTimeFilters(metaData.getExposureTimeFilters(), xmlsw);

        // EXPOSURETIME FILTERS end
        XMLUtil.writeElementEndWithLineBreak(xmlsw, Tab.FOUR);


        //  METADATA end
        XMLUtil.writeElementEndWithLineBreak(xmlsw, baseIndent);
    }

    private static void writeExposureTimeFilters(List<ExposureTimeFilter> exposureTimeFilters, XMLStreamWriter xmlsw) throws XMLStreamException {
        for (ExposureTimeFilter exposureTimeFilter : exposureTimeFilters) {
            // EXPOSURETIME FILTER start
            XMLUtil.writeElementStartWithLineBreak(ConfigElement.EXPOSURETIME_FILTER, Tab.SIX, xmlsw);

            XMLUtil.writeElementWithIndentAndLineBreak(ConfigElement.CAMERA_MODEL, Tab.EIGHT, exposureTimeFilter.getCameraModel(), xmlsw);
            XMLUtil.writeElementWithIndentAndLineBreak(ConfigElement.EXPOSURETIME_MASK, Tab.EIGHT, exposureTimeFilter.getExposureTimeFilterMask().name(), xmlsw);

            // EXPOSURETIME FILTER end
            XMLUtil.writeElementEndWithLineBreak(xmlsw, Tab.SIX);
        }
    }

    private static void writeIsoFilters(List<ISOFilter> isoFilters, XMLStreamWriter xmlsw) throws XMLStreamException {
        for (ISOFilter isoFilter : isoFilters) {
            // ISO FILTER start
            XMLUtil.writeElementStartWithLineBreak(ConfigElement.ISO_FILTER, Tab.SIX, xmlsw);

            XMLUtil.writeElementWithIndentAndLineBreak(ConfigElement.CAMERA_MODEL, Tab.EIGHT, isoFilter.getCameraModel(), xmlsw);
            XMLUtil.writeElementWithIndentAndLineBreak(ConfigElement.ISO_MASK, Tab.EIGHT, isoFilter.getIsoFilterMask().name(), xmlsw);

            // ISO FILTER end
            XMLUtil.writeElementEndWithLineBreak(xmlsw, Tab.SIX);
        }
    }
}
