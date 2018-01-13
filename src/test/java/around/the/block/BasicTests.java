package around.the.block;

import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;

import basics.exception.InvalidBlockException;
import basics.model.Block;
import basics.model.BlockChain;
import basics.model.BlockValidator;
import basics.model.LeadingZeroBlockValidator;
import net.jmatrix.console.log.ColorConsoleConfig;
import net.jmatrix.utils.ClassLogFactory;

public class BasicTests {
   private static final Logger log=ClassLogFactory.getLog();
   
   static {
      Properties p=System.getProperties();
      p.put("slf.cc.level","DEBUG");
      p.put("slf.cc.formatter","ANSIColorFormatter");
      
      ColorConsoleConfig.configure(p);
   }
   
   @Test
   public void testTamperEvident() throws InvalidBlockException {
      
      BlockChain bc=createTestChain(2, 5);
      
      Assert.assertTrue("Block Chain should be valid.", bc.validate());
      
      Block b=bc.getLast().getHeader().getPrev();
      
      b.setData("foo");
      
      try {
         bc.validate(); // thousl throw
         Assert.fail("Block chain should be invalid after tamper.");
      } catch (InvalidBlockException ex) {
         log.debug("Ex: "+ex);
      }
   }
   
   
   BlockChain createTestChain(int zeros, int size) throws InvalidBlockException {
      Block root=new Block(null, null, "Block-0");
      log.debug("Root Block: "+root);
      
      BlockValidator bv=new LeadingZeroBlockValidator(zeros);
      
      BlockChain bc=new BlockChain(root, bv);
      
      log.debug("Building BlockChain, size = "+size);
      for (int i=1; i<size; i++) {
         String data="Block-"+i;
         
         Block b=new Block(bc.getLast(), data);
         
         String nonce=bv.findNonce(b);
         
         bc.add(b);
      }
      return bc;
   }
}
