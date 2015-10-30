package com.jcrew.util;

import org.junit.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class InventoryDAOTest {

    @Test
    public void shouldReturnNumberOfUpdatedRowsForProvidedQuery() throws SQLException, ClassNotFoundException, IOException {
        final Connection connection = mock(Connection.class);
        InventoryDAO inventoryDAO = new InventoryDAO() {
            Connection createConnection(String url, Properties props) {
                assertEquals("jdbc:oracle:thin:@jdc1-scan-01.jcrew.com:1521/jcud1", url);
                assertEquals("qatester", props.getProperty("user"));
                assertEquals("qat3st", props.getProperty("password"));
                assertEquals("true", props.getProperty("ssl"));
                return connection;
            }
        };

        Statement statement = mock(Statement.class);

        when(statement.executeUpdate(InventoryDAO.UPDATE_BACKORDER_ITEM)).thenReturn(1);
        when(connection.createStatement()).thenReturn(statement);

        assertEquals("Should have updated expected number of rows", 1, inventoryDAO.addInventoryToBackOrderedItem());

        //assertEquals("Should have updated expected number of rows", 1, inventoryDAO.addInventoryToSoldOutItem("SOLD OUT"));
    }
}
