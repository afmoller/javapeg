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
package moller.javapeg.program.categories;

import java.util.Set;
import java.util.TreeSet;

import moller.util.string.StringUtil;

/**
 * This class
 *
 * @author Fredrik
 *
 */
public class Categories {

    private Set<String> categories;

    public Categories() {
        super();
        this.categories = new TreeSet<String>();
    }

    public Categories(String[] categories) {
        super();
        this.categories = new TreeSet<String>();
        for (String category : categories) {
            addCategory(category);
        }
    }


    public void addCategories(String categoriesString) {
        String[] categories = categoriesString.split(",");
        for (String category : categories) {
            if (StringUtil.isInt(category)) {
                addCategory(category);
            }
        }
    }

    public void addCategory(String category) {
        this.categories.add(category);
    }

    public Set<String> getCategories() {
        return categories;
    }

    public void setCategories(Set<String> categories) {
        this.categories = categories;
    }

    public int size() {
        return categories.size();
    }

    public boolean contains(String category) {
        return categories.contains(category);
    }

    @Override
    public String toString() {
        String categoryString= null;

        if (categories != null && categories.size() > 0) {
            StringBuilder sb = new StringBuilder();

            for (String category : categories) {
                sb.append(category);
                sb.append(",");
            }
            categoryString = sb.toString();
            categoryString = StringUtil.removeLastCharacter(categoryString);
        }
        return categoryString;
    }

    @Override
    public boolean equals(Object anObject) {
        if (this == anObject) {
            return true;
        }
        if (anObject instanceof Categories) {
            Categories anotherCategories = (Categories)anObject;
            if (this.getCategories().equals(anotherCategories.getCategories())) {
                return true;
            }
        }
        return false;
    }
}
