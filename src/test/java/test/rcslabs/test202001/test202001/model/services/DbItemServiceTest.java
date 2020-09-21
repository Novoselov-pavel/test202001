package test.rcslabs.test202001.test202001.model.services;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import test.rcslabs.test202001.test202001.model.entities.DbRow;
import test.rcslabs.test202001.test202001.model.repositories.DbItemRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class DbItemServiceTest {
    @Mock
    private DbItemRepository dbItemRepository;

    @BeforeEach
    void init() throws SQLException {
        List<String> columnName = new ArrayList<>(Arrays.asList("a", "b", "c"));
        List<DbRow> dbRows = new ArrayList<>();
        DbRow row1 = new DbRow();
        row1
            .addCell("a","val1")
            .addCell("c", "val2")
            .addCell("count",Integer.valueOf(100));
        row1.addCell("number",Double.valueOf(100));
        dbRows.add(row1);
        Mockito.when(dbItemRepository.getColumnsNames()).thenReturn(columnName);
        Mockito.when(dbItemRepository.getPivotTable("a","c")).thenReturn(dbRows);
    }

    @Test
    void getPivotTableJsonString() {
        DbItemService service = new DbItemService();
        service.setDbItemRepository(dbItemRepository);

        Assert.assertThrows(IllegalArgumentException.class,()->service.getPivotTableJsonString(null,null));
        Assert.assertThrows(IllegalArgumentException.class,()->service.getPivotTableJsonString("a",null));
        Assert.assertThrows(IllegalArgumentException.class,()->service.getPivotTableJsonString(null,"a"));
        try {
            String s = service.getPivotTableJsonString("a","c");
            Assert.assertTrue("unexpected output",s.length()>0);
            Assert.assertTrue("\"a\":\"val1\" doesn't contained in result",s.contains("\"a\":\"val1\""));
            Assert.assertTrue("\"number\":100.0 doesn't contained in result",s.contains("\"number\":100.0"));
            Assert.assertTrue("\"count\":100 doesn't contained in result",s.contains("\"count\":100"));
        } catch (SQLException exception) {
            exception.printStackTrace();
            fail();
        }
    }
}