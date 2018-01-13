package basics.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class BlockHeader {
   String prevHash;
   
   // A valid nonce is expensive to compute, and cheap to validate.
   // this is proof of work, dependent on BlockValidtor
   String nonce;
   
   Block prev;
   
   public BlockHeader(Block prev, String nonce) {
      this.prev=prev;
      
      if (prev != null)
         prevHash=prev.hash();
      else
         prevHash="null";
      
      this.nonce=nonce;
   }
   
   public BlockHeader(Block prev) {
      this.prev=prev;
      prevHash=prev.hash();
   }
   
   @JsonIgnore
   public Block getPrev() {
      return prev;
   }
   
   public String getNonce() {return nonce;}
   public String getPrevHash() {return prevHash;}
}
