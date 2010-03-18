package moller.util.io;
/**
 * This class was created : 2009-04-06 by Fredrik M�ller
 * Latest changed         : 2010-02-17 by Fredrik M�ller
 */


import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
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
	
	public static void closeStream(Closeable closeable) throws IOException {
		if (closeable != null) {
			closeable.close();
		}
	}
	
	public static String getString(InputStream in) throws IOException {
		
		if(null == in) {
			throw new IOException("InputStream is null");
		}
		
		int length = in.available();
		
		byte [] content = new byte [length];
		
		int readLength = in.read(content);
		
		if (readLength < length) {
			throw new IOException("Entire stream not read. " + readLength + " bytes read of " + length + " bytes available");
		}
		return new String(content);
	}
}