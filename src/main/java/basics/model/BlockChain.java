package basics.model;

import org.slf4j.Logger;

import basics.exception.InvalidBlockException;
import net.jmatrix.utils.ClassLogFactory;

public class BlockChain {
   private static final Logger log=ClassLogFactory.getLog();

   BlockValidator blockValidator=null;
   
   Block first;
   Block last;
   
   int blockCount;
   
   public BlockChain(Block root, BlockValidator bv) {
      first=root;
      last=root;
      blockValidator=bv;
   }
   
   public Block getLast() {
      return last;
   }
   
   public synchronized void add(Block b) throws InvalidBlockException {
      if (!blockValidator.isValid(b)) {
         throw new InvalidBlockException(b+" is not valid.");
      }
      b.header.prev=last;
      last=b;
   }
   
   public synchronized boolean validate() throws InvalidBlockException {
      Block b=last;
      
      while (b != null) {
         boolean valid=blockValidator.isValid(b);
         if (!valid) {
            throw new InvalidBlockException(b+" is not valid. "+
                  "This BlockChain has been altered.");
         }
         b=b.header.prev;
      }
      
      log.debug("BlockChain is valid.");
      return true;
   }
}
