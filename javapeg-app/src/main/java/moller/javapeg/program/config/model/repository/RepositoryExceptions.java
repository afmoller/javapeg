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
package moller.javapeg.program.config.model.repository;

import java.io.File;
import java.util.List;

public class RepositoryExceptions {

    private List<File> allwaysAdd;
    private List<File> neverAdd;

    public List<File> getAllwaysAdd() {
        return allwaysAdd;
    }
    public List<File> getNeverAdd() {
        return neverAdd;
    }
    public void setAllwaysAdd(List<File> allwaysAdd) {
        this.allwaysAdd = allwaysAdd;
    }
    public void setNeverAdd(List<File> neverAdd) {
        this.neverAdd = neverAdd;
    }
}
