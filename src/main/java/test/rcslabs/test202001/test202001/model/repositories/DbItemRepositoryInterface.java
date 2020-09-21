package test.rcslabs.test202001.test202001.model.repositories;

import test.rcslabs.test202001.test202001.model.entities.DbRow;

import java.sql.SQLException;
import java.util.List;

/**
 * Interface for access to the tables in database
 */
public interface DbItemRepositoryInterface {

    /**
     * Return list of {@link DbRow} from the source_data table has grouped by row and column
     *
     * @param row grouping parameter
     * @param column grouping parameter
     * @return list of {@link DbRow}
     * @throws SQLException on error
     */
    List<DbRow> getPivotTable(String row, String column) throws SQLException;

    /**
     * Return list of column's names from the source_data table
     *
     * @return list of column's names
     * @throws SQLException on error
     */
    List<String> getColumnsNames() throws SQLException;

}
