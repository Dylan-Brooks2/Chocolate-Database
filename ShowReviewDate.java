import java.sql.*;
import java.text.SimpleDateFormat;

public class ShowReviewDate {
    public static void main(String[] args){
        final String DB_URL = "jdbc:derby:ChocolateDB";

        try{
            //creates connection to DB
            Connection conn = DriverManager.getConnection(DB_URL);

            //creates a statement object
            Statement stmt = conn.createStatement();

            //Creates a string with a SELECT statement
            String sqlStatement = "SELECT ReferenceNumber, CountryOfBeanOrigin, ReviewDate FROM Chocolates";

            //sends statement to DBMS
            ResultSet result = stmt.executeQuery(sqlStatement);

            //Displays contents
            while (result.next()){
                String referenceNumber = result.getString("ReferenceNumber");
                String countryOfBeanOrigin = result.getString("CountryOfBeanOrigin");
                Date reviewDate = result.getDate("ReviewDate");

                // Format the date for display
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String formattedDate = dateFormat.format(reviewDate);

                System.out.printf("%s %25s %s\n",
                        referenceNumber,
                        countryOfBeanOrigin,
                        formattedDate);
            }

            conn.close();
        }
        catch(Exception ex){
            System.out.println("ERROR: " + ex.getMessage());
        }
    }
}
