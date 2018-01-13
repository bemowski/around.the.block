package basics.model;

public interface BlockValidator {
   public boolean isValid(Block b);
   
   public String findNonce(Block b);
}
