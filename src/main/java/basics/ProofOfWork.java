package basics;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.concurrent.atomic.AtomicInteger;

import net.jmatrix.jproperties.util.ArgParser;

public class ProofOfWork {
   
   static MessageDigest digest = null; 
   
   static AtomicInteger hashCount=new AtomicInteger(0);
   
   static {
      try {
         digest=MessageDigest.getInstance("SHA-256");
      } catch (Exception ex) {
         throw new RuntimeException("Error getting digest", ex);
      }
   }

   public static void main(String args[]) {
      // find a hash with 2 leading zeros
      // based on the input
      
      ArgParser ap=new ArgParser(args);
      
      String work=ap.getStringArg("-w");
      int zeros=ap.getIntArg("-z", 0);
      
      
      long start=System.currentTimeMillis();
      String nonce=findNonce(work, zeros);
      
      long end=System.currentTimeMillis();
      
      double hashesPerSecond=((double)hashCount.get())/((double)(end-start)/1000);
      
      System.out.println("nonce for '"+work+"' is "+nonce+", hash is "+hash(work, nonce));
      System.out.println("Took "+(((double)(end-start))/1000)+"s");
      System.out.println("Hashes/second: "+((int)hashesPerSecond));
   }
   
   static String findNonce(String x, int zeros) {
      // this is the work - the return is the proof of work
      
      int nonce=0;
      while (!validate(x, ""+nonce, zeros))
         nonce++;
      
      return ""+nonce;
   }
   
   static boolean validate(String x, String nonce, int zeros) {
      String hash=hash(x, nonce);
      char c[]=hash.toCharArray();
      for (int i=0; i<zeros; i++) {
         if (c[i] != '0') 
            return false;
      }
      return true;
   }
   
   
   public static final String hash(String x, String nonce) {
      byte[] encodedhash = digest.digest(
        (x+nonce).getBytes(StandardCharsets.UTF_8));
      
      hashCount.getAndIncrement();
      
      return bytesToHex(encodedhash);
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
