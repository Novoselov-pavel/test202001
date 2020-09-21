package test.rcslabs.test202001.test202001.model.entities;


import org.json.simple.JSONObject;
import org.springframework.util.Assert;

import java.util.*;

/**
 * Provides the representation of the database row
 */
public class DbRow {
   private final Map<String,Object> cellMap = new HashMap<>();

    /**
     * Add cell into DbRow
     *
     * @param columnName name of column, not Null
     * @param value value of cell
     * @return current DbRow object
     * @throws IllegalArgumentException if column is Null
     */
   public DbRow addCell(final String columnName, final Object value) {
       Assert.notNull(columnName,"ColumnName can't be null");
       cellMap.put(columnName,value);
       return this;
   }

    /**
     * Return entry set of row cell, where key - is column's name and value - is cell's value
     *
     * @return Set<Map.Entry<String,Object>>
     */
   public Set<Map.Entry<String,Object>> getEntities() {
       return cellMap.entrySet();
   }

    /**
     * Return JSONObject by this row
     *
     * @return {@link JSONObject}
     */
   @SuppressWarnings("unchecked")
   public JSONObject getJsonObject() {
       JSONObject jsonObject = new JSONObject();
       cellMap.forEach((key, value) -> jsonObject.put(key, value));
       return jsonObject;
   }

}
