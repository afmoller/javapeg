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



import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;

public class TestFileUtil {

	private static final String FS = File.separator;
	private static final String PATH  = "src" + FS + "test" + FS + "resources" + FS + "io" + FS + "input" + FS;
	private static final String SIZE   = PATH + "size";

	@Test
	public void testCopyFileFileFile() {

	}

	@Test
	public void testCopyFileByteArrayFile() {

	}

	@Test
	public void testGetFileSizeOnDiskFileLong() {

//		@Test
//		public void testGetFileSizeOnDiskWithClusterSize1024 () {

			File file = new File(SIZE + FS + "file1.txt");

			long size = 0;

			try {
				size = FileUtil.getFileSizeOnDisk(file, 1024);
			} catch (Exception e) {
				System.out.println("TEST   : testGetDirectorySizeForFile");
				System.out.println("MESSAGE: " + e.getMessage());
			}
			Assertions.assertEquals(1024, size);
//		}
//
//		@Test
//		public void testGetFileSizeOnDiskWithClusterSize2048 () {

			file = new File(SIZE + FS + "file1.txt");

			size = 0;

			try {
				size = FileUtil.getFileSizeOnDisk(file, 2048);
			} catch (Exception e) {
				System.out.println("TEST   : testGetDirectorySizeForFile");
				System.out.println("MESSAGE: " + e.getMessage());
			}
			Assertions.assertEquals(2048, size);
//		}
//
//
//		@Test
//		public void testGetFileSizeOnDiskWithClusterSize4096 () {

			file = new File(SIZE + FS + "file1.txt");

			size = 0;

			try {
				size = FileUtil.getFileSizeOnDisk(file, 4096);
			} catch (Exception e) {
				System.out.println("TEST   : testGetDirectorySizeForFile");
				System.out.println("MESSAGE: " + e.getMessage());
			}
			Assertions.assertEquals(4096, size);
//		}





	}

	@Test
	public void testGetFileSizeOnDiskLongLong() {

	}

	@Test
	public void testCreateFiles() {

	}

	@Test
	public void testCreateFile() {

	}

//	@Test
//	public void testDownloadAndSaveFile() {
//		try {
//			FileUtil.downloadAndSaveFile(new URL("http://javapeg.sourceforge.net/PAGES/updates/JavaPEG_2.0_Install.jar"), new File("C:/JavaPEG_2.0_Install.jar"), 1407008, 4, new Point(500, 500));
//		} catch (MalformedURLException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}

//	@Test
//	public void testIsOfType() {
//		File file = new File("C:/test.jpg");
//
//		Assertions.assertTrue(FileUtil.isOfType("jpg", file));
//		Assertions.assertTrue(FileUtil.isOfType("jPg", file));
//		Assertions.assertFalse(FileUtil.isOfType("jpeg", file));
//
//		File fileTwo = new File("C:/test");
//
//		Assertions.assertFalse(FileUtil.isOfType("jpg", fileTwo));
//
//	}

	@Test
	public void testRemoveFileSuffix() {
        File testFileOne = new File("test.txt");
        File testFileTwo = new File("test");
        File testFileThree = new File("test.test.txt");

        Assertions.assertEquals("test", FileUtil.removeFileSuffix(testFileOne));
        Assertions.assertEquals("test", FileUtil.removeFileSuffix(testFileTwo));
        Assertions.assertEquals("test.test", FileUtil.removeFileSuffix(testFileThree));
	}
}
