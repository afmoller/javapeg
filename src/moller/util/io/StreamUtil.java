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
package moller.util.io;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class StreamUtil {

	public static void closeStream(InputStream in) throws IOException {
		if (in != null) {
			in.close();
		}
	}

	public static void close(OutputStream out, boolean silent) {
		if (out != null) {
			try {
				out.close();
			} catch (IOException e) {
				if(!silent) {
					throw new RuntimeException(e);
				}
			}
		}
	}

	public static void close(InputStream in, boolean silent) {
		if (in != null) {
			try {
				in.close();
			} catch (IOException e) {
				if(!silent) {
					throw new RuntimeException(e);
				}
			}
		}
	}

	public static void close(InputStreamReader in, boolean silent) {
	    if (in != null) {
            try {
                in.close();
            } catch (IOException e) {
                if(!silent) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

	public static void closeStream(Closeable closeable) throws IOException {
		if (closeable != null) {
			closeable.close();
		}
	}

	public static String getString(InputStream in, String characterSet) throws IOException {
		return new String(getByteArray(in), characterSet);
	}

	public static byte[] getByteArray(InputStream in) throws IOException {
	    ByteArrayOutputStream buffer = new ByteArrayOutputStream();

	    int nRead;
	    byte[] data = new byte[16384];

	    while ((nRead = in.read(data, 0, data.length)) != -1) {
	      buffer.write(data, 0, nRead);
	    }

	    buffer.flush();

	    return buffer.toByteArray();
	}
}
