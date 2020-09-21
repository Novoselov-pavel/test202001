package test.rcslabs.test202001.test202001.model.Enties;


import java.util.*;

public class DbRow {
   private final Map<String,Object> cellMap = new LinkedHashMap<>();

   public void addCell(final String columnName, final Object value) {
       cellMap.put(columnName,value);
   }

   public Set<Map.Entry<String,Object>> getEntities() {
       return cellMap.entrySet();
   }
}
