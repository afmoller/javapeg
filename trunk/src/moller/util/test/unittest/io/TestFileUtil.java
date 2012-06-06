package moller.util.test.unittest.io;

import java.io.File;

import moller.util.io.FileUtil;

import org.junit.Assert;
import org.junit.Test;

public class TestFileUtil {

	private static final String FS = File.separator;
	private static final String SIZE   = FS + "src" + FS + "moller" + FS + "util" + FS + "test"+ FS + "unittest" + FS + "io" + FS + "input" + FS + "size";

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

			File file = new File(System.getProperty("user.dir") + SIZE + FS + "file1.txt");

			long size = 0;

			try {
				size = FileUtil.getFileSizeOnDisk(file, 1024);
			} catch (Exception e) {
				System.out.println("TEST   : testGetDirectorySizeForFile");
				System.out.println("MESSAGE: " + e.getMessage());
			}
			Assert.assertEquals(1024, size);
//		}
//
//		@Test
//		public void testGetFileSizeOnDiskWithClusterSize2048 () {

			file = new File(System.getProperty("user.dir") + SIZE + FS + "file1.txt");

			size = 0;

			try {
				size = FileUtil.getFileSizeOnDisk(file, 2048);
			} catch (Exception e) {
				System.out.println("TEST   : testGetDirectorySizeForFile");
				System.out.println("MESSAGE: " + e.getMessage());
			}
			Assert.assertEquals(2048, size);
//		}
//
//
//		@Test
//		public void testGetFileSizeOnDiskWithClusterSize4096 () {

			file = new File(System.getProperty("user.dir") + SIZE + FS + "file1.txt");

			size = 0;

			try {
				size = FileUtil.getFileSizeOnDisk(file, 4096);
			} catch (Exception e) {
				System.out.println("TEST   : testGetDirectorySizeForFile");
				System.out.println("MESSAGE: " + e.getMessage());
			}
			Assert.assertEquals(4096, size);
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
//		Assert.assertTrue(FileUtil.isOfType("jpg", file));
//		Assert.assertTrue(FileUtil.isOfType("jPg", file));
//		Assert.assertFalse(FileUtil.isOfType("jpeg", file));
//
//		File fileTwo = new File("C:/test");
//
//		Assert.assertFalse(FileUtil.isOfType("jpg", fileTwo));
//
//	}

	@Test
	public void testRemoveFileSuffix() {
        File testFileOne = new File("test.txt");
        File testFileTwo = new File("test");
        File testFileThree = new File("test.test.txt");

        Assert.assertEquals("test", FileUtil.removeFileSuffix(testFileOne));
        Assert.assertEquals("test", FileUtil.removeFileSuffix(testFileTwo));
        Assert.assertEquals("test.test", FileUtil.removeFileSuffix(testFileThree));
	}
}
