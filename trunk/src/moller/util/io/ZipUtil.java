package moller.util.io;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipUtil {

    public static void zip(File theFileToZip) throws IOException {
        zip(theFileToZip, false);
    }

    /**
     * @param theFileToZip
     * @throws IOException
     */
    public static void zip(File theFileToZip, boolean hide) throws IOException {

        byte[] buf = new byte[8096];

        File zipFile = new File(theFileToZip.getParent(), theFileToZip.getName() + ".zip");
        ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFile));

        FileInputStream fis = new FileInputStream(theFileToZip);

        zos.putNextEntry(new ZipEntry(theFileToZip.getName()));

        int lenght;
        while ((lenght = fis.read(buf)) > 0) {
        	zos.write(buf, 0, lenght);
        }
        zos.closeEntry();
        fis.close();
        zos.close();

        if (hide) {
            Path path = zipFile.toPath();
            Files.setAttribute(path, "dos:hidden", true);
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

                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(newFile));

                byte[] buffer = new byte[2048];
                int len;
                while ((len = zis.read(buffer)) > 0) {
                    bos.write(buffer, 0, len);
                }

                bos.close();
                zis.closeEntry();
                ze = zis.getNextEntry();
            }
        }
    }
}
