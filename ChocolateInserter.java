import java.sql.*;

public class ChocolateInserter {
    public static void main(String[] args) {
        final String DB_URL = "jdbc:derby:ChocolateDB";
        int batchesExecuted = 0; // Stores the number of batches executed

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Chocolates " +
                     "(ReferenceNumber, CountryOfBeanOrigin, ReviewDate, CompanyLocation, PercentCocoa, Rating) " +
                     "VALUES (?, ?, ?, ?, ?, ?)")) {

            // Inserting rows with batch processing
            insertChocolate(pstmt, "2542", "India", "2021-01-01", "USA", 68, 3.5);
            insertChocolate(pstmt, "233", "Madagascar", "2008-01-01", "USA", 71, 3.0);
            insertChocolate(pstmt, "1315", "Cuba", "2014-01-01", "France", 70, 3.5);
            insertChocolate(pstmt, "1676", "Togo", "2015-01-01", "France", 70, 2.75);
            insertChocolate(pstmt, "2114", "Guatemala", "2018-01-01", "USA", 70, 3.0);
            insertChocolate(pstmt, "341", "Ecuador", "2009-01-01", "Ecuador", 100, 1.5);

            // Execute batch
            int[] rowsInsertedArray = pstmt.executeBatch();

            // Count the number of rows inserted
            for (int count : rowsInsertedArray) {
                batchesExecuted += count;
            }

        } catch (Exception ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }

        // Display the number of rows inserted
        System.out.println(batchesExecuted + " row(s) inserted.");
    }

    private static void insertChocolate(PreparedStatement pstmt, String referenceNumber, String countryOfBeanOrigin,
                                        String reviewDate, String companyLocation, int percentCocoa, double rating)
            throws SQLException {
        pstmt.setString(1, referenceNumber);
        pstmt.setString(2, countryOfBeanOrigin);
        pstmt.setDate(3, java.sql.Date.valueOf(reviewDate));
        pstmt.setString(4, companyLocation);
        pstmt.setInt(5, percentCocoa);
        pstmt.setDouble(6, rating);
        pstmt.addBatch(); // Add this row to the batch
        pstmt.executeBatch(); // Execute the batch
    }
}
