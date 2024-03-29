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

import moller.javapeg.program.config.model.metadata.MetaData;
import moller.javapeg.program.config.model.metadata.MetaDataFilter;
import moller.javapeg.program.enumerations.filter.ExposureTimeFilterMask;
import moller.javapeg.program.enumerations.filter.IFilterMask;
import moller.javapeg.program.enumerations.filter.ISOFilterMask;
import moller.javapeg.program.enumerations.xml.ConfigElement;
import moller.util.string.Tab;
import moller.util.xml.XMLUtil;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class MetaDataConfig {

    public static MetaData getMetaDataConfig(Node metaDataNode) {
        MetaData metaData = new MetaData();

        NodeList childNodes = metaDataNode.getChildNodes();

        for (int i = 0; i < childNodes.getLength(); i++) {
            Node node = childNodes.item(i);

            switch (ConfigElement.getEnum(node.getNodeName())) {
            case ISO_FILTERS:
                metaData.setIsoFilters(getIsoFilters(node));
                break;
            case EXPOSURETIME_FILTERS:
                metaData.setExposureTimeFilters(getExposureTimeFilters(node));
                break;
            default:
                break;
            }
        }
        return metaData;
    }

    private static List<MetaDataFilter<ExposureTimeFilterMask>> getExposureTimeFilters(Node exposureTimesFiltersNode) {
        List<MetaDataFilter<ExposureTimeFilterMask>> exposureTimeFilters = new ArrayList<>();

        NodeList childNodes = exposureTimesFiltersNode.getChildNodes();

        for (int i = 0; i < childNodes.getLength(); i++) {
            Node node = childNodes.item(i);

            switch (ConfigElement.getEnum(node.getNodeName())) {
            case EXPOSURETIME_FILTER:
                exposureTimeFilters.add(createExposureTimeFilter(node));
                break;
            default:
                break;
            }
        }
        return exposureTimeFilters;
    }

    private static List<MetaDataFilter<ISOFilterMask>> getIsoFilters(Node isoFiltersNode) {
        List<MetaDataFilter<ISOFilterMask>> isoFilters = new ArrayList<>();

        NodeList childNodes = isoFiltersNode.getChildNodes();

        for (int i = 0; i < childNodes.getLength(); i++) {
            Node node = childNodes.item(i);

            switch (ConfigElement.getEnum(node.getNodeName())) {
            case ISO_FILTER:
                isoFilters.add(createIsoFilter(node));
                break;
            default:
                break;
            }
        }
        return isoFilters;
    }

    private static MetaDataFilter<ISOFilterMask> createIsoFilter(Node isoFilterNode) {
        NodeList childNodes = isoFilterNode.getChildNodes();

        String cameraModel = "";
        ISOFilterMask isoFilterMask = ISOFilterMask.NO_MASK;

        for (int i = 0; i < childNodes.getLength(); i++) {
            Node node = childNodes.item(i);

            switch (ConfigElement.getEnum(node.getNodeName())) {
            case CAMERA_MODEL:
                cameraModel = node.getTextContent();
                break;
            case FILTER_MASK:
                isoFilterMask = ISOFilterMask.valueOf(node.getTextContent());
                break;
            default:
                break;
            }
        }

        MetaDataFilter<ISOFilterMask> isoFilter = new MetaDataFilter<>();
        isoFilter.setCameraModel(cameraModel);
        isoFilter.setFilterMask(isoFilterMask);

        return isoFilter;
    }

    private static MetaDataFilter<ExposureTimeFilterMask> createExposureTimeFilter(Node exposureTimeFilterNode) {
        NodeList childNodes = exposureTimeFilterNode.getChildNodes();

        String cameraModel = "";
        ExposureTimeFilterMask exposureTimeFilterMask = ExposureTimeFilterMask.NO_MASK;

        for (int i = 0; i < childNodes.getLength(); i++) {
            Node node = childNodes.item(i);

            switch (ConfigElement.getEnum(node.getNodeName())) {
            case CAMERA_MODEL:
                cameraModel = node.getTextContent();
                break;
            case FILTER_MASK:
                exposureTimeFilterMask = ExposureTimeFilterMask.valueOf(node.getTextContent());
                break;
            default:
                break;
            }
        }

        MetaDataFilter<ExposureTimeFilterMask> exposureTimeFilter = new MetaDataFilter<>();
        exposureTimeFilter.setCameraModel(cameraModel);
        exposureTimeFilter.setFilterMask(exposureTimeFilterMask);

        return exposureTimeFilter;
    }

    public static void writeLanguageConfig(MetaData metaData, Tab baseIndent, XMLStreamWriter xmlsw) throws XMLStreamException {
        //  METADATA start
        XMLUtil.writeElementStartWithLineBreak(ConfigElement.METADATA, baseIndent, xmlsw);

        // ISO FILTERS start
        XMLUtil.writeElementStartWithLineBreak(ConfigElement.ISO_FILTERS, Tab.FOUR, xmlsw);

        writeFilters(ConfigElement.ISO_FILTER, metaData.getIsoFilters(), xmlsw);

        // ISO FILTERS end
        XMLUtil.writeElementEndWithLineBreak(xmlsw, Tab.FOUR);

        // EXPOSURETIME FILTERS start
        XMLUtil.writeElementStartWithLineBreak(ConfigElement.EXPOSURETIME_FILTERS, Tab.FOUR, xmlsw);

        writeFilters(ConfigElement.EXPOSURETIME_FILTER, metaData.getExposureTimeFilters(), xmlsw);

        // EXPOSURETIME FILTERS end
        XMLUtil.writeElementEndWithLineBreak(xmlsw, Tab.FOUR);

        //  METADATA end
        XMLUtil.writeElementEndWithLineBreak(xmlsw, baseIndent);
    }

    private static <T extends IFilterMask> void writeFilters(ConfigElement tagName, List<MetaDataFilter<T>> filters, XMLStreamWriter xmlsw) throws XMLStreamException {
        for (MetaDataFilter<T> filter : filters) {
            // FILTER start
            XMLUtil.writeElementStartWithLineBreak(tagName, Tab.SIX, xmlsw);

            XMLUtil.writeElementWithIndentAndLineBreak(ConfigElement.CAMERA_MODEL, Tab.EIGHT, filter.getCameraModel(), xmlsw);
            XMLUtil.writeElementWithIndentAndLineBreak(ConfigElement.FILTER_MASK, Tab.EIGHT, filter.getFilterMask().name(), xmlsw);

            // FILTER end
            XMLUtil.writeElementEndWithLineBreak(xmlsw, Tab.SIX);
        }
    }
}
