package moller.util.io;
/**
 * This class was created : 2009-04-06 by Fredrik Möller
 * Latest changed         :
 */


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class StreamUtil {

	public static void closeStream(InputStream in) {
		try {
			in.close();
		} catch (Exception e) {
		}
	}

	public static void closeStream(OutputStream out) {
		try {
			out.close();
		} catch (Exception e) {
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