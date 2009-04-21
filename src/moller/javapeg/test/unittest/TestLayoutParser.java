package moller.javapeg.test.unittest;

import org.junit.Test;

import moller.javapeg.program.thumbnailoverview.LayoutParser;
import moller.javapeg.program.thumbnailoverview.LayoutMetaItem;


public class TestLayoutParser {
	
	LayoutParser lp = LayoutParser.getInstance();
	
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