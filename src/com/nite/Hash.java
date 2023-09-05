package com.nite;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;

public class Hash {
    public static String[] createHashedPassword(String enteredPassword) throws Exception {
        SecureRandom random = new SecureRandom();
        byte[] saltPreEncoded = new byte[16];
        random.nextBytes(saltPreEncoded);

        String hashedPasswordNoSalt = hashPassword(enteredPassword, saltPreEncoded);
        String encodedSalt = Base64.getEncoder().encodeToString(saltPreEncoded);

//        System.out.println("Hashed Password (no salt): " + hashedPasswordNoSalt);
//        System.out.println("Salt: " + encodedSalt);

        String[] result = {hashedPasswordNoSalt, encodedSalt};

        return result;
    }

    public static String hashPassword(String password, byte[] salt) throws Exception {
        int iterations = 10000;
        int keyLength = 256;

        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, keyLength);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        byte[] hash = factory.generateSecret(spec).getEncoded();

        return Base64.getEncoder().encodeToString(hash);
    }

//    public static boolean checkPassword(String enteredPassword) {
//        checkPasswordHash(enteredPassword, )
//
//        return false;
//    }

    public static boolean checkPasswordHash(String enteredPassword, String storedSalt, String storedHashedPassword) throws Exception {
        int iterations = 10000;
        int keyLength = 256;

        KeySpec spec = new PBEKeySpec(enteredPassword.toCharArray(), Base64.getDecoder().decode(storedSalt), iterations, keyLength);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        byte[] hash = factory.generateSecret(spec).getEncoded();
        String enteredPasswordHash = Base64.getEncoder().encodeToString(hash);

        return enteredPasswordHash.equals(storedHashedPassword);
    }
}
