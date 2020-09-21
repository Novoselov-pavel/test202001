package test.rcslabs.test202001.test202001.model.services;

import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import test.rcslabs.test202001.test202001.model.entities.DbRow;
import test.rcslabs.test202001.test202001.model.repositories.DbItemRepository;
import test.rcslabs.test202001.test202001.model.repositories.DbItemRepositoryInterface;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Provides business logic
 */
@Service
public class DbItemService implements DbItemServiceInterface {

    private DbItemRepositoryInterface dbItemRepository;

    @Autowired
    public void setDbItemRepository(DbItemRepository dbItemRepository) {
        this.dbItemRepository = dbItemRepository;
    }

    /**
     * Return Json string from the "source_data" table has grouped by parameters "row" and "col"
     *
     * @param row grouping parameter
     * @param column grouping parameter
     * @return JsonArray string
     * @throws SQLException on error
     * @throws IllegalArgumentException if any parameter is fault
     */
    @Override
    public String getPivotTableJsonString(final String row, final String column) throws SQLException {
        Assert.notNull(row,"Row can't be null");
        Assert.notNull(column,"Column can't be null");
        List<String> columnList = dbItemRepository.getColumnsNames();
        Assert.isTrue(columnList.contains(row),"Row "+row + " doesn't exist");
        Assert.isTrue(columnList.contains(column),"Column "+column + " doesn't exist");

        List<DbRow> dataRows = dbItemRepository.getPivotTable(row, column);
        JSONArray array = new JSONArray();
        //noinspection unchecked
        array.addAll(dataRows
                .stream()
                .map(DbRow::getJsonObject)
                .collect(Collectors.toList()));
        return array.toJSONString();
    }
}
