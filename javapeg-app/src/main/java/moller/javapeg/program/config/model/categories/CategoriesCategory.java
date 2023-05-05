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
package moller.javapeg.program.config.model.categories;

import java.util.List;

public class CategoriesCategory {

    private CategoriesCategory parentCategory;
    private List<CategoriesCategory> childCategories;

    private String name;
    private Integer id;

    public CategoriesCategory getParentCategory() {
        return parentCategory;
    }
    public List<CategoriesCategory> getChildCategories() {
        return childCategories;
    }
    public String getName() {
        return name;
    }
    public Integer getId() {
        return id;
    }
    public void setParentCategory(CategoriesCategory parentCategory) {
        this.parentCategory = parentCategory;
    }
    public void setChildCategories(List<CategoriesCategory> childCategories) {
        this.childCategories = childCategories;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setId(Integer id) {
        this.id = id;
    }
}
