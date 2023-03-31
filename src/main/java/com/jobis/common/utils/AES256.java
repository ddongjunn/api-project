package com.jobis.common.utils;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

@Slf4j
@NoArgsConstructor
@Component
public class AES256 {


    //TODO 암호화 에러 (IllegalBlockSizeException)

    @Value("${aes256.alg}")
    public String ALG;

    @Value("${aes256.key}")
    private String KEY; // 32byte

    @Value("${aes256.iv}")
    private String IV; // 16byte

    // 암호화
    // 암호화
    public String encryptAES256(String text){
        try {
            Cipher cipher = Cipher.getInstance(ALG);
            SecretKeySpec keySpec = new SecretKeySpec(KEY.getBytes(), "AES");
            IvParameterSpec ivParamSpec = new IvParameterSpec(IV.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParamSpec);

            byte[] encrypted = cipher.doFinal(text.getBytes("UTF-8"));
            return Base64.getEncoder().encodeToString(encrypted);
        }catch (Exception e){
            e.getMessage();
        }
        return null;
    }

    // 복호화
    // 복호화
    public String decryptAES256(String cipherText) {
        byte[] decrypted = null;
        try {
            Cipher cipher = Cipher.getInstance(ALG);
            SecretKeySpec keySpec = new SecretKeySpec(KEY.getBytes(), "AES");
            IvParameterSpec ivParamSpec = new IvParameterSpec(IV.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParamSpec);

            byte[] decodedBytes = Base64.getDecoder().decode(cipherText);
            decrypted = cipher.doFinal(decodedBytes);

            return new String(decrypted, "UTF-8");
        }catch (Exception e){
            e.getMessage();
        }
        return null;
    }

    public String decryptAES256RegNo(String cipherText){
        Cipher cipher = initCipher();
        byte[] decodedBytes = Base64.getDecoder().decode(cipherText);
        byte[] decrypted;

        try {
            decrypted = cipher.doFinal(decodedBytes);
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException(e);
        }
        return new String(decrypted, StandardCharsets.UTF_8).split("-")[0];
    }

    public Cipher initCipher() {
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance(ALG);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new RuntimeException(e);
        }
        SecretKeySpec keySpec = new SecretKeySpec(KEY.getBytes(), "AES");
        IvParameterSpec ivParamSpec = new IvParameterSpec(IV.getBytes());

        try {
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParamSpec);
        } catch (InvalidKeyException | InvalidAlgorithmParameterException e) {
            throw new RuntimeException(e);
        }

        return cipher;
    }

}
