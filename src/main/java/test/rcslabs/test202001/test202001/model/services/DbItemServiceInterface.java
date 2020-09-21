package test.rcslabs.test202001.test202001.model.services;

import java.sql.SQLException;

/**
 * Interface for business logic
 */
public interface DbItemServiceInterface {

    /**
     * Return Json string from the "source_data" table has grouped by parameters "row" and "col"
     *
     * @param row grouping parameter
     * @param column grouping parameter
     * @return JsonArray string
     * @throws SQLException on error
     * @throws IllegalArgumentException if any parameter is fault
     */
    String getPivotTableJsonString(String row, String column) throws SQLException;
}
