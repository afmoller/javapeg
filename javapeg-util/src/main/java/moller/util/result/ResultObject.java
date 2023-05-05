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
package moller.util.result;

public class ResultObject<T extends Object> {

    private Boolean result;
    private T object;

    public ResultObject(Boolean result, T object) {
        super();
        this.result = result;
        this.object = object;
    }

    public Boolean getResult() {
        return result;
    }
    public T getObject() {
        return object;
    }
    public void setResult(Boolean result) {
        this.result = result;
    }
    public void setObject(T object) {
        this.object = object;
    }
}
