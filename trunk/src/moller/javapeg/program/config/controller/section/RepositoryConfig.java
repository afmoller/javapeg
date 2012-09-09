package moller.javapeg.program.config.controller.section;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

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

    public static Repository getRepositoryConfig(Node repositoryNode, XPath xPath) {
        Repository repository = new Repository();

        try {
            repository.setExceptions(getRepositoryExceptions((Node)xPath.evaluate(ConfigElement.EXCEPTIONS, repositoryNode, XPathConstants.NODE), xPath));
            repository.setPaths(getRepositoryPaths((Node)xPath.evaluate(ConfigElement.PATHS, repositoryNode, XPathConstants.NODE), xPath));
        } catch (XPathExpressionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return repository;
    }

    private static RepositoryPaths getRepositoryPaths(Node repositoryNode, XPath xPath) {
        RepositoryPaths repositoryPaths = new RepositoryPaths();

        List<File> paths = new ArrayList<File>();

        try {
            NodeList pathNodeList = (NodeList)xPath.evaluate(ConfigElement.PATH, repositoryNode, XPathConstants.NODESET);

            for (int index = 0; index < pathNodeList.getLength(); index++) {
                String path = pathNodeList.item(index).getTextContent();

                if (StringUtil.isNotBlank(path)) {
                    paths.add(new File(path));
                }
            }
            repositoryPaths.setPaths(paths);
        } catch (XPathExpressionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return repositoryPaths;
    }

    private static RepositoryExceptions getRepositoryExceptions(Node exceptionsNode, XPath xPath) {
        RepositoryExceptions repositoryExceptions = new RepositoryExceptions();

        List<File> allwaysAdd = new ArrayList<File>();
        List<File> neverAdd = new ArrayList<File>();

        try {
            NodeList allwaysAddNodeList = (NodeList)xPath.evaluate(ConfigElement.ALLWAYS_ADD, exceptionsNode, XPathConstants.NODESET);

            for (int index = 0; index < allwaysAddNodeList.getLength(); index++) {
                String path = allwaysAddNodeList.item(index).getTextContent();

                if (StringUtil.isNotBlank(path)) {
                    allwaysAdd.add(new File(path));
                }
            }
            repositoryExceptions.setAllwaysAdd(allwaysAdd);

        } catch (XPathExpressionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        try {
            NodeList neverAddNodeList = (NodeList)xPath.evaluate(ConfigElement.NEVER_ADD, exceptionsNode, XPathConstants.NODESET);

            for (int index = 0; index < neverAddNodeList.getLength(); index++) {
                String path = neverAddNodeList.item(index).getTextContent();

                if (StringUtil.isNotBlank(path)) {
                    neverAdd.add(new File(path));
                }
            }
            repositoryExceptions.setNeverAdd(neverAdd);

        } catch (XPathExpressionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
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
