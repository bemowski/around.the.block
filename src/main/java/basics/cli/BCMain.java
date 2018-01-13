package basics.cli;

import java.util.Properties;

import org.slf4j.Logger;

import basics.exception.InvalidBlockException;
import basics.model.Block;
import basics.model.BlockChain;
import basics.model.BlockValidator;
import basics.model.LeadingZeroBlockValidator;
import net.jmatrix.console.log.ColorConsoleConfig;
import net.jmatrix.jproperties.util.ArgParser;
import net.jmatrix.utils.ClassLogFactory;

public class BCMain {
   private static final Logger log=ClassLogFactory.getLog();

   
   public static void main(String args[]) throws InvalidBlockException {
      ArgParser ap=new ArgParser(args);
      
      initLog(ap);
      
      // create a valid chain of 3
      
      Block root=new Block(null, null, "Block-0");
      log.debug("Root Block: "+root);
      
      int zeros=ap.getIntArg("-z", 1);
      BlockValidator bv=new LeadingZeroBlockValidator(zeros);
      
      BlockChain bc=new BlockChain(root, bv);
      
      int size=4;
      size=ap.getIntArg("-s", size);
      
      log.debug("Building BlockChain, size = "+size);
      for (int i=1; i<size; i++) {
         String data="Block-"+i;
         
         Block b=new Block(bc.getLast(), data);
         
         String nonce=bv.findNonce(b);
         
         bc.add(b);
      }
      
      System.out.println("\n\n");
      
      // print the chain.
      Block b=bc.getLast();
      while (b != null) {
         log.debug("   Block: "+b);
         b=b.getHeader().getPrev();
      }
   }
   
   static void initLog(ArgParser ap) {
      Properties p=System.getProperties();
      p.put("slf.cc.level","DEBUG");
      p.put("slf.cc.formatter","ANSIColorFormatter");
      
      ColorConsoleConfig.configure(p);
   }
}
