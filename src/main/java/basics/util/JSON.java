package basics.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JSON {
   static ObjectMapper om=new ObjectMapper();
   
   public static final String toJson(Object o) {
      if (o==null)return null;
      try {
         return om.writeValueAsString(o);
      } catch (Exception ex) {
         return "jackson error";
      }
   }
}
