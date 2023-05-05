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

public class TestDirectoryUtil {

	private static final String FS = File.separator;
//	private static final String DELETE = FS + "src" + FS + "moller" + FS + "util" + FS + "test"+ FS + "unittest" + FS + "io" + FS + "input" + FS + "delete";

	private static final String PATH  = "src" + FS + "test" + FS + "resources" + FS + "io" + FS + "input" + FS;

	private static final String SIZE   = PATH + "size";
	private static final String PATHLENGTH   = PATH + "pathlength";

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
//			Assertions.assertEquals(0, new File(System.getProperty("user.dir") + DELETE).list().length);
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
//			Assertions.assertEquals(0, new File(System.getProperty("user.dir") + DELETE).list().length);
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
//			Assertions.assertEquals(0, new File(System.getProperty("user.dir") + DELETE).list().length);
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
			Assertions.assertEquals(0, size);
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
			Assertions.assertEquals(shouldBeException, isException);
//		}
//
//		@Test
//		public void testGetDirectorySizeDirectoryWithFiles () {

			folder = new File(SIZE + FS + "folder2");

			size = 0;

			try {
				size = DirectoryUtil.getDirectorySizeOnDisk(folder);
			} catch (Exception e) {
				System.out.println("TEST   : testGetDirectorySizeForFile");
				System.out.println("MESSAGE: " + e.getMessage());
			}
			Assertions.assertEquals(8192, size);
//		}
//
//		@Test
//		public void testGetDirectorySizeDirectoryWithFilesAndSubDirectoyWithFiles () {

			folder = new File(SIZE);

			size = 0;

			try {
				size = DirectoryUtil.getDirectorySizeOnDisk(folder);
			} catch (Exception e) {
				System.out.println("TEST   : testGetDirectorySizeForFile");
				System.out.println("MESSAGE: " + e.getMessage());
			}
			Assertions.assertEquals(16384, size);
//		}
//
//		@Test
//		public void testGetDirectorySizeDirectoryWithFilesAndSubDirectoyWithFilesAsStringParameter () {

			size = 0;

			try {
				size = DirectoryUtil.getDirectorySizeOnDisk(SIZE);
			} catch (Exception e) {
				System.out.println("TEST   : testGetDirectorySizeForFile");
				System.out.println("MESSAGE: " + e.getMessage());
			}
			Assertions.assertEquals(16384, size);
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
				File directory = new File(PATHLENGTH);
				length = DirectoryUtil.getLengthOfLongestSubDirectoryPath(directory, directory);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Assertions.assertEquals(48, length);
//		}
//
//		@Test
//		public void testGetLongestSubDirectoryPath() {

			String path = "";

			try{
				File directory = new File(PATHLENGTH);
				path = DirectoryUtil.getLongestSubDirectoryPath(directory, directory);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Assertions.assertEquals(FS + "folder2" + FS + "folder3" + FS + "folder4" + FS + "reallyLongDirectoryName", path);
//		}


	}

	@Test
	public void testGetDirectoryContent() {

//		@Test
//		public void testGetDirectoryContent() {

			File directory = new File(SIZE);

			try {
				for (File file : DirectoryUtil.getDirectoryContent(directory)) {
					System.out.println(file.getAbsolutePath());
				}
			} catch (Exception e) {
			}
		}


//	}

	@Test
	public void testGetStatus() {
		File exists = new File(System.getProperty("user.dir"));
		File doesNotExist = new File(System.getProperty("user.dir") + File.separator + System.currentTimeMillis());
		File notAvailable = new File("G:\\test");

		Assertions.assertEquals(Status.EXISTS, DirectoryUtil.getStatus(exists));
		Assertions.assertEquals(Status.DOES_NOT_EXIST, DirectoryUtil.getStatus(doesNotExist));
		Assertions.assertEquals(Status.NOT_AVAILABLE, DirectoryUtil.getStatus(notAvailable));

	}
}
