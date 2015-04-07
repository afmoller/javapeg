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

import javax.swing.*;

public class ImportedCategoryTreeAndDisplayJavaPegID {

    private String javaPegId;
    private JTree categoriesTree;

    public String getJavaPegId() {
        return javaPegId;
    }
    public JTree getCategoriesTree() {
        return categoriesTree;
    }
    public void setJavaPegId(String javaPegId) {
        this.javaPegId = javaPegId;
    }
    public void setCategoriesTree(JTree categoriesTree) {
        this.categoriesTree = categoriesTree;
    }
}
