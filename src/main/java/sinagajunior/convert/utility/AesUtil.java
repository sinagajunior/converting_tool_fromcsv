package sinagajunior.convert.utility;


import com.google.common.io.BaseEncoding;
import org.springframework.util.Base64Utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

//@Component
//@ConfigurationProperties(prefix = "aes")
//@Configuration
//@Getter
//@Setter
//@Slf4j
public class AesUtil  {


    public static String encryptToOJKFormat(String jsonText, String encriptionKey, String encriptionIV, boolean IVrandom) {
        try {
            // System.out.println("key length is : = " + encriptionKey.length());

            // Generating IV.
            byte[] IV = new byte[16];
            SecureRandom random = new SecureRandom();
            random.nextBytes(IV);
            IvParameterSpec iv = new IvParameterSpec(IVrandom ? IV : encriptionIV.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(encriptionKey.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            byte[] encrypted = cipher.doFinal(jsonText.getBytes());

            StringBuilder resultConcathWithIV =  new StringBuilder();
            StringBuilder resultBase64 =  new StringBuilder();
            resultConcathWithIV.append(Base64Utils.encodeToString(encrypted).concat("::").concat(encriptionIV));
            resultBase64.append(Base64Utils.encodeToString(resultConcathWithIV.toString().getBytes("UTF-8")));
           return resultBase64.toString();

        } catch (Exception ex) {

            ex.printStackTrace();
            //log.error("error in encrypt OJK "+ex.getMessage());

        }
        return null;
    }




//    public static  String encryptJasipt(String src){
//        StandardPBEStringEncryptor textEncryptor = new StandardPBEStringEncryptor();
//        textEncryptor.setAlgorithm("PBEWithHmacSHA512AndAES_256");
//        textEncryptor.setPassword(ENCRYPTION_KEY);
//
//        textEncryptor.setIvGenerator(new RandomIvGenerator() {
//
//            @Override
//            public byte[] generateIv(int i) {
//                return ENCRYPTION_IV.getBytes();
//            }
//
//            @Override
//            public boolean includePlainIvInEncryptionResults() {
//                return false;
//            }
//        });
//      //  textEncryptor.setPassword(ENCRYPTION_KEY);
//        return  textEncryptor.encrypt(src);
//    }


//    public static String encrypt(String src) {
//        try {
//            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
//            cipher.init(Cipher.ENCRYPT_MODE, makeKey(), makeIv());
//            return   Base64 .encodeBase64String(cipher.doFinal(src.getBytes()));  //Base64.encodeBase64String(cipher.doFinal(src.getBytes()));
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }

//    public static String decrypt(String src) {
//        String decrypted = "";
//        try {
//            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
//            cipher.init(Cipher.DECRYPT_MODE, makeKey(), makeIv());
//            decrypted = new String(cipher.doFinal(Base64.decode(src)));
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//        return decrypted;
//    }

//    static AlgorithmParameterSpec makeIv() {
//        try {
//            return new IvParameterSpec(ENCRYPTION_IV.getBytes("UTF-8"));
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    static Key makeKey() {
//        try {
//            MessageDigest md = MessageDigest.getInstance("SHA-256");
//            byte[] key = md.digest(ENCRYPTION_KEY.getBytes("UTF-8"));
//            return new SecretKeySpec(key, "AES");
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//
//        return null;
//    }


//    public static void main(String[] args) throws Exception {
//        String str = "tes_enkripsi";
//
//        System.out.println(str);
//
//        System.out.println(" result Encrypt :  "+ AesUtil.encryptTradisional(str));
//
//
//       String ecryptStr = AesUtil.encryptTradisional(str)+"::ijzh84t1w9xa56s9";
//      String j = new String( Base64Utils.decodeFromString("NDFZZS9idk8zZW5uVHV6MTR2SWNJUT09OjppanpoODR0MXc5eGE1NnM5"));
//       System.out.println("Hasil penggabungan ::IV "+   new String(Base64Utils.encode(ecryptStr.getBytes())));
//
//    }
}