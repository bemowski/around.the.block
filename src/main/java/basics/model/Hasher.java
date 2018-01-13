package basics.model;

public interface Hasher {
   public byte[] hash(String s);
   public String hashHex(String s);
}
