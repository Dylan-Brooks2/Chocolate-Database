import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This class contains the database connection
 */
public class ChocolateDB {
    public static void main(String[] args) {
        int choc;
        final String CONNECTION = "jdbc:derby:ChocolateDB;create=true";
        try (Connection conn = DriverManager.getConnection(CONNECTION);
             Statement statement = conn.createStatement()){
            choc = statement.executeUpdate("drop table Chocolates" );

            System.out.println("Chocolates table dropped.");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try (Connection conn = DriverManager.getConnection(CONNECTION);
             Statement statement = conn.createStatement()){
            statement.executeUpdate("CREATE TABLE Chocolates (" +
                    "ReferenceNumber VARCHAR(50) NOT NULL," +
                    "CountryOfBeanOrigin VARCHAR(50) NOT NULL," +
                    "ReviewDate VARCHAR(50)," +
                    "CompanyLocation VARCHAR(100)," +
                    "PercentCocoa VARCHAR(50)," +
                    "Rating VARCHAR(50)," +
                    "PRIMARY KEY (ReferenceNumber, CountryOfBeanOrigin, ReviewDate)" +
                    ")");
            System.out.println("Chocolates table created.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
