package model.util;

import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by satyan on 11/9/17.
 *
 */
public class Checksum {
    private static final String METHOD = "SHA-256";

    /**
     * Get the checksum of a file
     * @param file the file
     * @return the checksum (SHA-256)
     */
    public static String checksum(File file){
        try (InputStream in = new FileInputStream(file)){
            MessageDigest digest = MessageDigest.getInstance(METHOD);
            byte[] data = new byte[4096];
            int length;
            while ((length = in.read(data)) > 0) {
                digest.update(data, 0, length);
            }
            return DatatypeConverter.printHexBinary(digest.digest());
        } catch (IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Get the checksum for bytes
     * @param bytes the bytes
     * @return the checksum (SHA-256)
     */
    public static String checksum(byte[] bytes){
        try {
            MessageDigest digest = MessageDigest.getInstance(METHOD);
            return DatatypeConverter.printHexBinary(digest.digest(bytes));
        } catch (NoSuchAlgorithmException ignored) {
        }
        return null;
    }
}
