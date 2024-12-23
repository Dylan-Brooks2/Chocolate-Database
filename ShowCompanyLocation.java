import java.sql.*;
/**
 * This program displays the Reference Number,
 * Country of Bean Origin, Review Date, and Company Location
 */
public class ShowCompanyLocation {
    public static void main(String[] args){
        final String DB_URL = "jdbc:derby:ChocolateDB";

        try{
            //creates connection to DB
            Connection conn = DriverManager.getConnection(DB_URL);

            //creates a statement object
            Statement stmt = conn.createStatement();

            //Creates a string with a SELECT statement
            String sqlStatement = "SELECT ReferenceNumber, CountryOfBeanOrigin, ReviewDate," +
                    "CompanyLocation FROM Chocolates";

            //sends statement to DBMS
            ResultSet result = stmt.executeQuery(sqlStatement);

            //Displays contents
            while (result.next()){
                System.out.printf("%.2f %25s %.2f %25s\n",
                        result.getDouble("ReferenceNumber"),
                        result.getString("CountryOfBeanOrigin"),
                        result.getDouble("ReviewDate"),
                        result.getString("CompanyLocation"));
            }

            conn.close();
        }
        catch(Exception ex){
            System.out.println("ERROR: " + ex.getMessage());
        }
    }
}
