package moller.javapeg.program.config.controller.section;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import moller.javapeg.program.config.controller.ConfigElement;
import moller.javapeg.program.config.model.repository.Repository;
import moller.javapeg.program.config.model.repository.RepositoryExceptions;
import moller.javapeg.program.config.model.repository.RepositoryPaths;

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
                paths.add(new File(pathNodeList.item(index).getTextContent()));
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
                allwaysAdd.add(new File(allwaysAddNodeList.item(index).getTextContent()));
            }
            repositoryExceptions.setAllwaysAdd(allwaysAdd);

        } catch (XPathExpressionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        try {
            NodeList neverAddNodeList = (NodeList)xPath.evaluate(ConfigElement.NEVER_ADD, exceptionsNode, XPathConstants.NODESET);

            for (int index = 0; index < neverAddNodeList.getLength(); index++) {
                neverAdd.add(new File(neverAddNodeList.item(index).getTextContent()));
            }
            repositoryExceptions.setNeverAdd(neverAdd);

        } catch (XPathExpressionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return repositoryExceptions;
    }
}
