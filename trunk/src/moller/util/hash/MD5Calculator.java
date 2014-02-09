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
package moller.util.hash;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import moller.util.string.StringUtil;

public class MD5Calculator {

	public static String calculate(File theFileToCalculate) {

		String hashValue = "";

		if(theFileToCalculate.isFile()) {
			try {
				MessageDigest digest = MessageDigest.getInstance("MD5");
				try (InputStream is = new FileInputStream(theFileToCalculate)) {
    				byte[] buffer = new byte[8192];
    				int read = 0;
    				try {
    					while( (read = is.read(buffer)) > 0) {
    						digest.update(buffer, 0, read);
    					}
    					hashValue = StringUtil.convertToHexString(digest.digest());
    				} catch(IOException e) {
    					throw new RuntimeException("Unable to process file for MD5", e);
    				}
				} catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
			} catch (NoSuchAlgorithmException e1) {
				e1.printStackTrace();
			}
		}
		return hashValue;
	}
}
