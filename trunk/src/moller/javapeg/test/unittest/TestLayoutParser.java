package moller.javapeg.test.unittest;

import moller.javapeg.program.thumbnailoverview.LayoutMetaItem;
import moller.javapeg.program.thumbnailoverview.LayoutParser;
import org.junit.Test;


public class TestLayoutParser {

	@Test
	public void testParse() {
		LayoutParser lp = LayoutParser.getInstance();
		lp.parse();
		
		System.out.println(lp.getColumnAmount());
		System.out.println(lp.getColumnClass());
		System.out.println(lp.getImageClass());
		System.out.println(lp.getMetaClass());
		
		for(LayoutMetaItem metaItem : lp.getMetaItems()) {
			System.out.println(metaItem.getClassString());
			System.out.println(metaItem.getLabelString());
			System.out.println(metaItem.getMetaString());
		}
		System.out.println(lp.getRowClass());
	}
}