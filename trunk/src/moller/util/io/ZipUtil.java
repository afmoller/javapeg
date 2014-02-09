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

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipUtil {

    /**
     * @param theFileToZip
     * @throws IOException
     */
    public static void zip(File theFileToZip) throws IOException {

        byte[] buf = new byte[8096];

        File zipFile = new File(theFileToZip.getParent(), theFileToZip.getName() + ".zip");
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFile))) {

            try(FileInputStream fis = new FileInputStream(theFileToZip)) {

                zos.putNextEntry(new ZipEntry(theFileToZip.getName()));

                int lenght;
                while ((lenght = fis.read(buf)) > 0) {
                    zos.write(buf, 0, lenght);
                }
                zos.closeEntry();
            }
        }
    }

    public static void unzip(File fileToUnzip, File destinationDirectory) throws IOException {

        //create output directory is not exists
        if (!destinationDirectory.exists()) {
            destinationDirectory.mkdirs();
        } else {
            if (destinationDirectory.isFile()) {
                destinationDirectory = destinationDirectory.getParentFile();
            }
        }

        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(fileToUnzip))){

            //get the zipped file list entry
            ZipEntry ze = zis.getNextEntry();

            while(ze != null){

                File newFile = new File(destinationDirectory, ze.getName());

                //create all non exists folders
                //else you will hit FileNotFoundException for compressed folder
                newFile.getParentFile().mkdirs();

                try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(newFile))) {

                    byte[] buffer = new byte[2048];
                    int len;
                    while ((len = zis.read(buffer)) > 0) {
                        bos.write(buffer, 0, len);
                    }
                }

                zis.closeEntry();
                ze = zis.getNextEntry();
            }
        }
    }
}
