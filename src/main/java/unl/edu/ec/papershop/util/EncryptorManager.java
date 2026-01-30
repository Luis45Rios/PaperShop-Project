package unl.edu.ec.papershop.util;

import unl.edu.ec.papershop.exception.EncryptorException;
import jakarta.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptorManager {

    public static String encrypt(String input) throws EncryptorException {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(input.getBytes());
            byte[] digest = md.digest();
            return DatatypeConverter.printHexBinary(digest).toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            throw new EncryptorException("Error al encriptar", e);
        }
    }
}