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
package moller.javapeg.program.rename;

import moller.javapeg.program.enumerations.Type;

import java.io.File;

public class FileAndType {

    private File file;
    private Type type;

    public FileAndType(File file) {
        this.file = file;
        this.type = null;
    }

    public FileAndType(Type type) {
        this.file = null;
        this.type = type;
    }

    public FileAndType(File file, Type type) {
        this.file = file;
        this.type = type;
    }

    public File getFile() {
        return file;
    }

    public Type getType() {
        return type;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public void setType(Type type) {
        this.type = type;
    }
}