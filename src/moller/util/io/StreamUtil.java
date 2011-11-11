package moller.util.io;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;

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

	public static void closeStream(Closeable closeable) throws IOException {
		if (closeable != null) {
			closeable.close();
		}
	}

	public static String getString(InputStream in) throws IOException {
		return new String(getByteArray(in));
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

	public static String getString(InputStream is, String charSet) throws IOException {
	    final char[] buffer = new char[0x10000];
	    StringBuilder out = new StringBuilder();
	    Reader reader = new InputStreamReader(is, charSet);
	    int read;

	    do {
	      read = reader.read(buffer, 0, buffer.length);
	      if (read>0) {
	        out.append(buffer, 0, read);
	      }
	    } while (read>=0);

	    return out.toString();
	}
}
