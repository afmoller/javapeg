package moller.util.hash;
/**
 * This class was created : 2009-04-10 by Fredrik Möller
 * Latest changed         : 2009-04-13 by Fredrik Möller
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {

	public static String calculate(File theFileToCalculate) {
			
		String hashValue = "";
		
		if(theFileToCalculate.isFile()) {
			try {
				MessageDigest digest = MessageDigest.getInstance("MD5");
				InputStream is = new FileInputStream(theFileToCalculate);
				byte[] buffer = new byte[8192];
				int read = 0;
				try {
					while( (read = is.read(buffer)) > 0) {
						digest.update(buffer, 0, read);
					}
					hashValue = convertToHexString(digest.digest());
				} catch(IOException e) {
					throw new RuntimeException("Unable to process file for MD5", e);
				} finally {
					try {
						is.close();
					}
					catch(IOException e) {
						throw new RuntimeException("Unable to close input stream for MD5 calculation", e);
					}
				}	
			} catch (NoSuchAlgorithmException e1) {
				e1.printStackTrace();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}	
		}
		return hashValue;
	}
	
	private static String convertToHexString(byte[] data) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
        	int halfbyte = (data[i] >>> 4) & 0x0F;
        	int two_halfs = 0;
        	do {
	        	if ((0 <= halfbyte) && (halfbyte <= 9))
	                buf.append((char) ('0' + halfbyte));
	            else
	            	buf.append((char) ('a' + (halfbyte - 10)));
	        	halfbyte = data[i] & 0x0F;
        	} while(two_halfs++ < 1);
        }
        return buf.toString();
    }
}