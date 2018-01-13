package basics.model;

import basics.util.JSON;

public class Block {
   static Hasher hasher=new SHA256Hasher();
   
   BlockHeader header;
   String data;
   
   public Block(Block prev, String nonce, String data) {
      header=new BlockHeader(prev, nonce);
      this.data=data;
   }
   
   public Block(Block prev, String data) {
      header=new BlockHeader(prev);
      this.data=data;
   }
   
   public void setNonce(String s){
      header.nonce=s;
   }
   
   public String toString() {
      return JSON.toJson(this);
   }
   
   public String hash() {
      if (header.prev == null) {
         // root block.
         return "root.block";
      }
      String s=data+header.prevHash+header.nonce;
      return hasher.hashHex(s);
   }
   
   public String getData() {
      return data;
   }
   
   public void setData(String s) {
      // this is used in tests to show that if the data
      // is tampered with after construction, the BlockChain
      // will become invalid.
      data=s;
   }
   
   public BlockHeader getHeader() {
      return header;
   }
}
