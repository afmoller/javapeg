package moller.javapeg.test.unittest;

import junit.framework.Assert;
import moller.javapeg.program.categories.Categories;

import org.junit.Test;

public class TestCategories {

    @Test
    public void testToString() {
        Categories categories = new Categories();
        categories.addCategory("10");
        categories.addCategory("20");
        categories.addCategory("30");
        categories.addCategory("40");
        categories.addCategory("50");
        categories.addCategory("60");

        Assert.assertEquals("10,20,30,40,50,60", categories.toString());
    }

    @Test
    public void testEqualsObjectEqual() {
        Categories categoriesOne = new Categories();
        Categories categoriesTwo = new Categories();

        categoriesOne.addCategory("1");
        categoriesOne.addCategory("2");
        categoriesOne.addCategory("3");

        categoriesTwo.addCategory("1");
        categoriesTwo.addCategory("2");
        categoriesTwo.addCategory("3");

        Assert.assertTrue(categoriesOne.equals(categoriesTwo));
    }

    @Test
    public void testEqualsObjectNotEqualOne() {
        Categories categoriesOne = new Categories();
        Categories categoriesTwo = new Categories();

        categoriesOne.addCategory("1");
        categoriesOne.addCategory("2");
        categoriesOne.addCategory("3");

        categoriesTwo.addCategory("4");
        categoriesTwo.addCategory("5");
        categoriesTwo.addCategory("6");

        Assert.assertFalse(categoriesOne.equals(categoriesTwo));
    }

    @Test
    public void testEqualsObjectNotEqualTwo() {
        Categories categoriesOne = new Categories();
        Categories categoriesTwo = new Categories();

        categoriesOne.addCategory("1");
        categoriesOne.addCategory("2");
        categoriesOne.addCategory("3");

        categoriesTwo.addCategory("4");
        categoriesTwo.addCategory("5");

        Assert.assertFalse(categoriesOne.equals(categoriesTwo));
    }
}
