package com.epam.bookshop;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.Arrays;

public class Sample {


    public static String getSalt() throws NoSuchAlgorithmException, NoSuchProviderException {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG", "SUN");

        byte[] salt = new byte[16];

        sr.nextBytes(salt);

        return Arrays.toString(salt);
    }

    public static String getSecurePassword(String passwordToHash, String salt) {
        String generatedPassword = null;

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");

            md.update(salt.getBytes());

            byte[] bytes = md.digest(passwordToHash.getBytes());

            StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }

            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return generatedPassword;
    }

    public static void main(String[] args) throws NoSuchProviderException, NoSuchAlgorithmException {
        String passwordToHash = "myPassword";

        String salt = getSalt();

        String securePassword = getSecurePassword(passwordToHash, salt);
        System.out.println(securePassword);

        String regeneratedPasswordToVerify = getSecurePassword(passwordToHash, salt);

        System.out.println(regeneratedPasswordToVerify.length());
    }
}
