package test.rcslabs.test202001.test202001.model.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test.rcslabs.test202001.test202001.model.entities.DbRow;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides access to the tables in database
 */
@Service
public class DbItemRepository implements DbItemRepositoryInterface {

    private DataSource dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Return list of {@link DbRow} from the source_data table has grouped by row and column
     *
     * @param row grouping parameter
     * @param column grouping parameter
     * @return list of {@link DbRow}
     * @throws SQLException on error
     */
    @Override
    public List<DbRow> getPivotTable(final String row, final String column) throws SQLException {
        List<DbRow> retVal = new ArrayList<>();
        @SuppressWarnings("StringBufferReplaceableByString") StringBuilder builder = new StringBuilder();
        builder.append("Select ")
                .append(row)
                .append(" as 'row', ")
                .append(column)
                .append(" as 'column', ")
                .append("count(*) as 'val' ")
                .append("from source_data group by ")
                .append(row)
                .append(", ")
                .append(column)
                .append(";");

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(builder.toString());
             ResultSet set = statement.executeQuery()) {

            while (set.next()) {
                DbRow dbRow = new DbRow();
                ResultSetMetaData metaData = set.getMetaData();
                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    dbRow.addCell(metaData.getColumnName(i),getValue(i,set));
                }
                retVal.add(dbRow);
            }
        }
        return retVal;
    }

    /**
     * Return list of column's names from the source_data table
     *
     * @return list of column's names
     * @throws SQLException on error
     */
    @Override
    public List<String> getColumnsNames() throws SQLException {
        List<String> retVal = new ArrayList<>();
        String sql = "select * from source_data limit 1;";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet set = statement.executeQuery()) {

            ResultSetMetaData metaData = set.getMetaData();
            for (int i = 1; i <=metaData.getColumnCount(); i++) {
                retVal.add(metaData.getColumnName(i));
            }
        }
        return retVal;
    }

    private Object getValue(final int columnIndex, final ResultSet resultSet) throws SQLException {
        String columnType = resultSet.getMetaData().getColumnTypeName(columnIndex);
        switch (columnType) {
            case "TEXT":
                //noinspection DuplicateBranchesInSwitch
                return resultSet.getString(columnIndex);
            case "INTEGER":
                return resultSet.getInt(columnIndex);
            case "NUMERIC":
                return resultSet.getDouble(columnIndex);
            default:
                return resultSet.getString(columnIndex);
        }
    }


}
