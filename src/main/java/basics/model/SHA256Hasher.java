package basics.model;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class SHA256Hasher implements Hasher {
   static class ThreadLocalMD extends ThreadLocal<MessageDigest> {
      @Override
      public MessageDigest initialValue() {
         try {
            return MessageDigest.getInstance("SHA-256");
         } catch (Exception ex) {
            throw new RuntimeException("Error getting digest", ex);
         }
      }
   }
   
   static ThreadLocalMD threadLocalMD=new ThreadLocalMD();

   @Override
   public byte[] hash(String s) {
      return threadLocalMD.get().digest(s.getBytes(StandardCharsets.UTF_8));
   }

   @Override
   public String hashHex(String s) {
      return bytesToHex(hash(s));
   }
   
   private static final String bytesToHex(byte[] hash) {
      StringBuilder hexString = new StringBuilder();
      for (int i = 0; i < hash.length; i++) {
      String hex = Integer.toHexString(0xff & hash[i]);
      if(hex.length() == 1) hexString.append('0');
          hexString.append(hex);
      }
      return hexString.toString();
   }
}
