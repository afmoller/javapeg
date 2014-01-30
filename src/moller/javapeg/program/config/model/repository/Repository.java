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

public class Repository {

    private RepositoryPaths paths;
    private RepositoryExceptions exceptions;

    public RepositoryPaths getPaths() {
        return paths;
    }
    public RepositoryExceptions getExceptions() {
        return exceptions;
    }
    public void setPaths(RepositoryPaths paths) {
        this.paths = paths;
    }
    public void setExceptions(RepositoryExceptions exceptions) {
        this.exceptions = exceptions;
    }
}
