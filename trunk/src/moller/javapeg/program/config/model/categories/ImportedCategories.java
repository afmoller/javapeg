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

import javax.swing.tree.DefaultMutableTreeNode;

public class ImportedCategories {

    private String javaPegId;
    private String displayName;
    private DefaultMutableTreeNode root;
    private Integer highestUsedId;

    public String getJavaPegId() {
        return javaPegId;
    }
    public Integer getHighestUsedId() {
        return highestUsedId;
    }
    public void setJavaPegId(String javaPegId) {
        this.javaPegId = javaPegId;
    }
    public void setHighestUsedId(Integer highestUsedId) {
        this.highestUsedId = highestUsedId;
    }
    public String getDisplayName() {
        return displayName;
    }
    public DefaultMutableTreeNode getRoot() {
        return root;
    }
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
    public void setRoot(DefaultMutableTreeNode root) {
        this.root = root;
    }
    @Override
    public String toString() {
        return displayName;
    }
}
