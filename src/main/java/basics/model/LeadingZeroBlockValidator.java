package basics.model;

import org.slf4j.Logger;

import net.jmatrix.utils.ClassLogFactory;

public class LeadingZeroBlockValidator implements BlockValidator {
   private static final Logger log=ClassLogFactory.getLog();
   
   // Yes, i realize hex stringing the block hash is expensive, and 
   // foolish, but makes this code a bit more readable.  Though 
   // not algorithmically efficient, it demonstrates the concept as
   // well as anything.
   int leadingZeros;
   
   public LeadingZeroBlockValidator(int z) {
      leadingZeros=z;
   }
   
   @Override
   public boolean isValid(Block b) {
      String hash=b.hash();
      char c[]=hash.toCharArray();
      for (int i=0; i<leadingZeros; i++) {
         if (c[i] != '0') 
            return false;
      }
      return true;
   }

   @Override
   public String findNonce(Block b) {
      log.debug("Find nonce, Block: "+b);
      int nonce=0;
      b.setNonce(""+nonce);
      
      while (!isValid(b)) {
        // log.debug("Checking nonce: "+nonce);
         b.setNonce(""+(nonce++));
      }
      
      log.debug("Found nonce for Block: "+b);
      
      return b.header.nonce;
   }
}
