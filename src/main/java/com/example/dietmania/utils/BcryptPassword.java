//package com.example.dietmania.utils;
//
//public class BcryptPassword {
//
//    private static final int COST = 12;
//
//    private static final BCryptPasswordEncoder encoder =
//            new BCryptPasswordEncoder(COST);
//
//    private BcryptPassword() {}
//
//
//
//    public static String encrypt(String plainText) {
//        if (plainText == null || plainText.isEmpty()) {
//            throw new IllegalArgumentException("Password cannot be null or empty");
//        }
//        return encoder.encode(plainText);
//    }
//
//
//
//    public static boolean verify(String plainText, String hash) {
//        if (plainText == null || hash == null) {
//            return false;
//        }
//        return encoder.matches(plainText, hash);
//    }
//}
//
//
//
////<dependency>
////    <groupId>org.springframework.security</groupId>
////    <artifactId>spring-security-crypto</artifactId>
////</dependency>
