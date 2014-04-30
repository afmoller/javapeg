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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import moller.javapeg.program.config.controller.ConfigElement;
import moller.javapeg.program.config.model.repository.Repository;
import moller.javapeg.program.config.model.repository.RepositoryExceptions;
import moller.javapeg.program.config.model.repository.RepositoryPaths;
import moller.util.string.StringUtil;
import moller.util.string.Tab;
import moller.util.xml.XMLUtil;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class RepositoryConfig {

    public static Repository getRepositoryConfig(Node repositoryNode) {
        Repository repository = new Repository();

        NodeList childNodes = repositoryNode.getChildNodes();

        for (int i = 0; i < childNodes.getLength(); i++) {
            Node node = childNodes.item(i);

            switch (node.getNodeName()) {
            case ConfigElement.EXCEPTIONS:
                repository.setExceptions(getRepositoryExceptions(node));
                break;
            case ConfigElement.PATHS:
                repository.setPaths(getRepositoryPaths(node));
                break;
            default:
                break;
            }
        }
        return repository;
    }

    private static RepositoryPaths getRepositoryPaths(Node pathsNode) {
        List<File> paths = new ArrayList<File>();

        NodeList childNodes = pathsNode.getChildNodes();

        for (int i = 0; i < childNodes.getLength(); i++) {
            Node node = childNodes.item(i);

            switch (node.getNodeName()) {
            case ConfigElement.PATH:
                String path = node.getTextContent();
                if (StringUtil.isNotBlank(path)) {
                    paths.add(new File(path));
                }
                break;
            default:
                break;
            }
        }

        RepositoryPaths repositoryPaths = new RepositoryPaths();
        repositoryPaths.setPaths(paths);

        return repositoryPaths;
    }

    private static RepositoryExceptions getRepositoryExceptions(Node exceptionsNode) {
        RepositoryExceptions repositoryExceptions = new RepositoryExceptions();

        List<File> allwaysAdd = new ArrayList<File>();
        List<File> neverAdd = new ArrayList<File>();

        NodeList childNodes = exceptionsNode.getChildNodes();

        for (int i = 0; i < childNodes.getLength(); i++) {
            Node node = childNodes.item(i);

            switch (node.getNodeName()) {
            case ConfigElement.ALLWAYS_ADD:

                NodeList allwaysAddChildNodes= node.getChildNodes();

                for (int j = 0; j < allwaysAddChildNodes.getLength(); j++) {
                    Node allwaysAddChildNode = allwaysAddChildNodes.item(j);

                    switch (allwaysAddChildNode.getNodeName()) {
                    case ConfigElement.PATH:
                        String path = allwaysAddChildNode.getTextContent();
                        if (StringUtil.isNotBlank(path)) {
                            allwaysAdd.add(new File(path));
                        }
                        break;
                    default:
                        break;
                    }
                }
                repositoryExceptions.setAllwaysAdd(allwaysAdd);
                break;

            case ConfigElement.NEVER_ADD:

                NodeList neverAddChildNodes= node.getChildNodes();

                for (int j = 0; j < neverAddChildNodes.getLength(); j++) {
                    Node neverAddChildNode = neverAddChildNodes.item(j);

                    switch (neverAddChildNode.getNodeName()) {
                    case ConfigElement.PATH:
                        String path = neverAddChildNode.getTextContent();
                        if (StringUtil.isNotBlank(path)) {
                            neverAdd.add(new File(path));
                        }
                        break;
                    default:
                        break;
                    }
                }
                repositoryExceptions.setNeverAdd(neverAdd);
                break;
            default:
                break;
            }
        }
        return repositoryExceptions;
    }

    public static void writeRepositoryConfig(Repository repository, Tab baseIndent, XMLStreamWriter xmlsw) throws XMLStreamException {

        // REPOSITORY start
        XMLUtil.writeElementStartWithLineBreak(ConfigElement.REPOSITORY, baseIndent, xmlsw);

        RepositoryPaths repositoryPaths = repository.getPaths();

        // PATHS start
        XMLUtil.writeElementStartWithLineBreak(ConfigElement.PATHS, Tab.FOUR, xmlsw);

        for (File repositoryPath : repositoryPaths.getPaths()) {
            XMLUtil.writeElementWithIndentAndLineBreak(ConfigElement.PATH, Tab.SIX, repositoryPath.getAbsolutePath(), xmlsw);
        }

        XMLUtil.writeElementEndWithLineBreak(xmlsw, Tab.FOUR);

        // PATHS end

        // EXCEPTIONS start
        XMLUtil.writeElementStartWithLineBreak(ConfigElement.EXCEPTIONS, Tab.FOUR, xmlsw);


        RepositoryExceptions repositoryExceptions = repository.getExceptions();

        if (repositoryExceptions.getAllwaysAdd().isEmpty()) {
            XMLUtil.writeEmptyElementWithIndentAndLineBreak(ConfigElement.ALLWAYS_ADD, xmlsw, Tab.SIX);
        } else {
            // ALLWAYS ADD start
            XMLUtil.writeElementStartWithLineBreak(ConfigElement.ALLWAYS_ADD, Tab.SIX, xmlsw);

            for (File allwaysAdd : repositoryExceptions.getAllwaysAdd()) {
                XMLUtil.writeElementWithIndentAndLineBreak(ConfigElement.PATH, Tab.EIGHT, allwaysAdd.getAbsolutePath(), xmlsw);
            }

            // ALLWAYS ADD end
            XMLUtil.writeElementEndWithLineBreak(xmlsw, Tab.SIX);
        }

        if (repositoryExceptions.getNeverAdd().isEmpty()) {
            XMLUtil.writeEmptyElementWithIndentAndLineBreak(ConfigElement.NEVER_ADD, xmlsw, Tab.SIX);
        } else {
            // NEVER ADD start
            XMLUtil.writeElementStartWithLineBreak(ConfigElement.NEVER_ADD, Tab.SIX, xmlsw);

            for (File neverAdd : repositoryExceptions.getNeverAdd()) {
                XMLUtil.writeElementWithIndentAndLineBreak(ConfigElement.PATH, Tab.EIGHT, neverAdd.getAbsolutePath(), xmlsw);
            }

            // NEVER ADD end
            XMLUtil.writeElementEndWithLineBreak(xmlsw, Tab.SIX);
        }

        // EXCEPTIONS end
        XMLUtil.writeElementEndWithLineBreak(xmlsw, Tab.FOUR);

        //  REPOSITORY end
        XMLUtil.writeElementEndWithLineBreak(xmlsw, baseIndent);
    }
}
