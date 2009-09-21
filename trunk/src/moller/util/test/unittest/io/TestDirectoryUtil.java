package moller.util.test.unittest.io;

import java.io.File;

import moller.util.io.DirectoryUtil;

import org.junit.Assert;
import org.junit.Test;

public class TestDirectoryUtil {

	private static final String FS = File.separator;
	private static final String DELETE = FS + "src" + FS + "moller" + FS + "util" + FS + "test"+ FS + "unittest" + FS + "io" + FS + "input" + FS + "delete";
	private static final String SIZE   = FS + "src" + FS + "moller" + FS + "util" + FS + "test"+ FS + "unittest" + FS + "io" + FS + "input" + FS + "size";
	private static final String PATHLENGTH   = FS + "src" + FS + "moller" + FS + "util" + FS + "test"+ FS + "unittest" + FS + "io" + FS + "input" + FS + "pathlength";
	
//	@Test
//	public void testDeleteDirectory() {
//		
////		@Test
////		public void testDeleteDirectoryWithoutFiles() {
//				
//			File folder = new File(System.getProperty("user.dir") + DELETE + FS + "test1");
//			folder.mkdir();
//					
//			DirectoryUtil.deleteDirectory(folder);
//					
//			Assert.assertEquals(0, new File(System.getProperty("user.dir") + DELETE).list().length);
////		}
//		
//		
////		@Test
////		public void testDeleteDirectoryWithOnlyFiles() {
//				
//			folder = new File(System.getProperty("user.dir") + DELETE + FS + "test1");
//			folder.mkdir();
//			
//			for(int i = 0; i < 10; i++) {
//				File file = new File(System.getProperty("user.dir") + DELETE + FS + "test1" + FS + "file" + i + ".txt");
//				try {
//					file.createNewFile();
//				} catch (IOException e) {
//				}
//			}
//			
//			DirectoryUtil.deleteDirectory(folder);
//					
//			Assert.assertEquals(0, new File(System.getProperty("user.dir") + DELETE).list().length);
////		}
//		
////		@Test
////		public void testDeleteDirectoryWithFilesAndSubFolders() {
//			
//			File folderOne = new File(System.getProperty("user.dir") + DELETE + FS + "test1");
//			folderOne.mkdir();
//			
//			File folderTwo = new File(System.getProperty("user.dir") + DELETE + FS + "test1" + FS + "test1");
//			folderTwo.mkdir();
//			
//			for(int i = 0; i < 10; i++) {
//				File file = new File(System.getProperty("user.dir") + DELETE + FS + "test1" + FS + "file" + i + ".txt");
//				try {
//					file.createNewFile();
//				} catch (IOException e) {
//				}
//			}
//			
//			for(int i = 0; i < 10; i++) {
//				File file = new File(System.getProperty("user.dir") + DELETE + FS + "test1" + FS + "test1" + FS + "file" + i + ".txt");
//				try {
//					file.createNewFile();
//				} catch (IOException e) {
//				}
//			}
//			
//			DirectoryUtil.deleteDirectory(folderOne);
//					
//			Assert.assertEquals(0, new File(System.getProperty("user.dir") + DELETE).list().length);	
////		}
//	}

	@Test
	public void testGetDirectorySizeOnDiskFile() {
//		@Test
//		public void testGetDirectorySizeEmptyDirectory() {
				
			File folder = new File(System.getProperty("user.dir") + SIZE + FS + "folder1");
			
			long size = 0;
			
			try {
				size = DirectoryUtil.getDirectorySizeOnDisk(folder);
			} catch (Exception e) {
				System.out.println("TEST   : testGetDirectorySizeForFile");
				System.out.println("MESSAGE: " + e.getMessage());
			}
			Assert.assertEquals(0, size);
//		}
//		
//		@Test
//		public void testGetDirectorySizeForFile (){
			
			boolean shouldBeException = true;
			boolean isException = false;
			
			File file = new File(System.getProperty("user.dir") + SIZE + FS + "file1.txt");
					
			try {
				DirectoryUtil.getDirectorySizeOnDisk(file);
			} catch (Exception e) {
				System.out.println("TEST   : testGetDirectorySizeForFile");
				System.out.println("MESSAGE: " + e.getMessage());
				isException = true;
			}
			Assert.assertEquals(shouldBeException, isException);
//		}
//		
//		@Test
//		public void testGetDirectorySizeDirectoryWithFiles () {
			
			folder = new File(System.getProperty("user.dir") + SIZE + FS + "folder2");
			
			size = 0;
			
			try {
				size = DirectoryUtil.getDirectorySizeOnDisk(folder);
			} catch (Exception e) {
				System.out.println("TEST   : testGetDirectorySizeForFile");
				System.out.println("MESSAGE: " + e.getMessage());
			}
			Assert.assertEquals(8192, size);
//		}
//		
//		@Test
//		public void testGetDirectorySizeDirectoryWithFilesAndSubDirectoyWithFiles () {
			
			folder = new File(System.getProperty("user.dir") + SIZE);
			
			size = 0;
			
			try {
				size = DirectoryUtil.getDirectorySizeOnDisk(folder);
			} catch (Exception e) {
				System.out.println("TEST   : testGetDirectorySizeForFile");
				System.out.println("MESSAGE: " + e.getMessage());
			}
			Assert.assertEquals(16384, size);
//		}
//		
//		@Test
//		public void testGetDirectorySizeDirectoryWithFilesAndSubDirectoyWithFilesAsStringParameter () {
			
			size = 0;
			
			try {
				size = DirectoryUtil.getDirectorySizeOnDisk(System.getProperty("user.dir") + SIZE);
			} catch (Exception e) {
				System.out.println("TEST   : testGetDirectorySizeForFile");
				System.out.println("MESSAGE: " + e.getMessage());
			}
			Assert.assertEquals(16384, size);
//		}
//

		
		
	}

	@Test
	public void testGetDirectorySizeOnDiskString() {
		
	}

	@Test
	public void testGetLengthOfLongestSubDirectoryPath() {
		
	}

	@Test
	public void testGetLongestSubDirectoryPath() {
		
		
		
//		@Test
//		public void getLengthOfLongestSubDirectoryPath() {
			
			int length = 0;
			
			try{
				File directory = new File(System.getProperty("user.dir") + PATHLENGTH);	
				length = DirectoryUtil.getLengthOfLongestSubDirectoryPath(directory, directory);			
			} catch (Exception e) {
				e.printStackTrace();
			}	
			Assert.assertEquals(48, length);
//		}
//		
//		@Test
//		public void testGetLongestSubDirectoryPath() {
			
			String path = "";
			
			try{
				File directory = new File(System.getProperty("user.dir") + PATHLENGTH);	
				path = DirectoryUtil.getLongestSubDirectoryPath(directory, directory);			
			} catch (Exception e) {
				e.printStackTrace();
			}	
			Assert.assertEquals(FS + "folder2" + FS + "folder3" + FS + "folder4" + FS + "reallyLongDirectoryName", path);
//		}
		
		
	}

	@Test
	public void testGetDirectoryContent() {
		
//		@Test
//		public void testGetDirectoryContent() {
			
			File directory = new File(System.getProperty("user.dir") + SIZE);
			
			try {
				for (File file : DirectoryUtil.getDirectoryContent(directory)) {
					System.out.println(file.getAbsolutePath());
				}
			} catch (Exception e) {
			}
		}
		
		
//	}

}
