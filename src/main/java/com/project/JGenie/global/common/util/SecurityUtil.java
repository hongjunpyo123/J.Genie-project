package com.project.JGenie.global.common.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
public class SecurityUtil {

    private @Value("${encrypt.key}") String key;

    public String encrypt(String text) { //문자열 암호화
        if (text == null || text.isEmpty()) {
            return null; // 또는 빈 문자열 반환
        }
        try {
            // 키를 16바이트로 포맷팅 (AES-128용)
            key = String.format("%-16s", key).substring(0, 16);

            // 비밀 키 생성
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");

            // "AES/CBC/PKCS5Padding" 모드 사용 (ECB 대신 CBC 사용)
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

            // 랜덤 초기화 벡터(IV) 생성 - AES 블록 크기는 16바이트
            byte[] iv = new byte[16];
            new java.security.SecureRandom().nextBytes(iv);
            javax.crypto.spec.IvParameterSpec ivspec = new javax.crypto.spec.IvParameterSpec(iv);

            // IV와 함께 암호화 모드 초기화
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivspec);

            // 암호화 수행
            byte[] encrypted = cipher.doFinal(text.getBytes(StandardCharsets.UTF_8));

            // IV와 암호화된 데이터를 합침
            byte[] combined = new byte[iv.length + encrypted.length];
            System.arraycopy(iv, 0, combined, 0, iv.length);
            System.arraycopy(encrypted, 0, combined, iv.length, encrypted.length);

            // URL 안전 Base64 인코딩
            return Base64.getUrlEncoder().withoutPadding().encodeToString(combined);

        } catch (Exception e) {
            e.printStackTrace(); // 실제 환경에서는 적절한 로깅 사용
            return "error";
        }
    }

    public String decrypt(String encryptedText) { //문자열 복호화
        if (encryptedText == null || encryptedText.isEmpty()) {
            return null; // 또는 빈 문자열 반환
        }
        try {
            // 키를 16바이트로 포맷팅
            key = String.format("%-16s", key).substring(0, 16);

            // 비밀 키 생성
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");

            // Base64 디코딩
            byte[] combined = Base64.getUrlDecoder().decode(encryptedText);

            // IV 추출 (처음 16바이트)
            byte[] iv = new byte[16];
            System.arraycopy(combined, 0, iv, 0, iv.length);
            javax.crypto.spec.IvParameterSpec ivspec = new javax.crypto.spec.IvParameterSpec(iv);

            // 암호화된 데이터 추출 (나머지 바이트)
            byte[] encrypted = new byte[combined.length - iv.length];
            System.arraycopy(combined, iv.length, encrypted, 0, encrypted.length);

            // "AES/CBC/PKCS5Padding" 모드로 복호화 객체 생성
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivspec);

            // 복호화 수행
            byte[] decrypted = cipher.doFinal(encrypted);

            // 복호화된 바이트 배열을 문자열로 변환
            return new String(decrypted, StandardCharsets.UTF_8);

        } catch (Exception e) {
            e.printStackTrace(); // 실제 환경에서는 적절한 로깅 사용
            return "error";
        }
    }
}
