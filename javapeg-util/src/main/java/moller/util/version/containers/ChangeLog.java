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
package moller.util.version.containers;

public class ChangeLog {

	private String changeLog;

	public ChangeLog() {
		super();
		this.changeLog = "";
	}

	public ChangeLog(long timeStamp, String changeLog) {
		super();
		this.changeLog = changeLog;
	}

	public String getChangeLog() {
		return changeLog;
	}

	public void setChangeLog(String changeLog) {
		this.changeLog = changeLog;
	}
}
