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
package moller.util.string;

public class ParseVMArguments {

    public static long parseXmxToLong(String xmxString) {
        int beginIndex = -1;
        int endIndex = -1;

        for (int i = 0; i < xmxString.length(); i++)  {
            if (StringUtil.isInt(xmxString.substring(i, i + 1))) {
                beginIndex = i;
                break;
            }
        }

        if (beginIndex > -1) {
            for (int i = beginIndex; i < xmxString.length(); i++) {
                if (!StringUtil.isInt(xmxString.substring(i, i + 1))) {
                    endIndex = i;
                    break;
                }
            }
        }

        int intValue = Integer.parseInt(xmxString.substring(beginIndex, endIndex));

        String suffix = xmxString.substring(endIndex);

        if ("k".equalsIgnoreCase(suffix)) {
            return intValue * 1024;
        } else if ("m".equalsIgnoreCase(suffix)) {
            return intValue * 1024 * 1024;
        } else {
            return intValue;
        }
    }
}
