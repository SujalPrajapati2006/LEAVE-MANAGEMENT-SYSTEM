package com.example.authservice.security;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;

public class JwtKeyGenerator {
    public static void main(String[] args) {

        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        String base64Key = Encoders.BASE64.encode(key.getEncoded());
        System.out.println(base64Key);

    }
}

//import java.security.KeyPair;
//import java.security.KeyPairGenerator;
//import java.security.PrivateKey;
//import java.security.PublicKey;
//import java.util.Base64;
//
//public class JwtKeyGenerator {
//
//    public static void main(String[] args) throws Exception {
//        // Generate RSA key pair
//        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
//        keyPairGenerator.initialize(2048); // Key size
//        KeyPair keyPair = keyPairGenerator.generateKeyPair();
//
//        PrivateKey privateKey = keyPair.getPrivate();
//        PublicKey publicKey = keyPair.getPublic();
//
//        // Encode keys to Base64 for PEM format
//        String privateKeyPEM = "-----BEGIN PRIVATE KEY-----\n"
//                + encodeToPEM(privateKey.getEncoded())
//                + "-----END PRIVATE KEY-----\n";
//
//        String publicKeyPEM = "-----BEGIN PUBLIC KEY-----\n"
//                + encodeToPEM(publicKey.getEncoded())
//                + "-----END PUBLIC KEY-----\n";
//
//        System.out.println("Private Key:");
//        System.out.println(privateKeyPEM);
//
//        System.out.println("Public Key:");
//        System.out.println(publicKeyPEM);
//    }
//
//    private static String encodeToPEM(byte[] bytes) {
//        String encoded = Base64.getMimeEncoder(64, "\n".getBytes()).encodeToString(bytes);
//        return encoded + "\n";
//    }
//}

