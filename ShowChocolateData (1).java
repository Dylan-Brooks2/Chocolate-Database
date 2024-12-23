import javax.swing.table.DefaultTableModel;
import java.sql.*;

/**
 * This class allows us to show our data in the program as a whole
 */
public class ShowChocolateData {
    private static final String DB_URL = "jdbc:derby:ChocolateDB";

    public DefaultTableModel fetchChocolates() throws SQLException {
        Connection conn = null;
        Statement stmt = null;
        ResultSet result = null;

        try {
            conn = DriverManager.getConnection(DB_URL);
            stmt = conn.createStatement();
            String sqlStatement = "SELECT * FROM Chocolates";
            result = stmt.executeQuery(sqlStatement);

            ResultSetMetaData metaData = result.getMetaData();
            int numColumns = metaData.getColumnCount();

            // Create DefaultTableModel to hold the data
            DefaultTableModel model = new DefaultTableModel();

            // Add column names to the model
            for (int i = 1; i <= numColumns; i++) {
                model.addColumn(metaData.getColumnName(i));
            }

            // Add rows to the model
            while (result.next()) {
                Object[] row = new Object[numColumns];
                for (int i = 1; i <= numColumns; i++) {
                    row[i - 1] = result.getObject(i);
                }
                model.addRow(row);
            }

            return model;
        } finally {
            // Close resources in finally block
            if (result != null) {
                try {
                    result.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
