import java.sql.*;

/**
 * This program displays the Reference Number Column
 * from all the rows in the CoffeeDB
 */
public class ShowReferenceNum {
    public static void main(String[] args){
        final String DB_URL = "jdbc:derby:ChocolateDB";

        try{
            //creates connection to DB
            Connection conn = DriverManager.getConnection(DB_URL);

            //Statement object
            Statement stmt = conn.createStatement();

            //Create a string with a SELECT statement
            String sqlStatement = "SELECT ReferenceNumber FROM Chocolates";

            //Sends statement to DBMS
            ResultSet result = stmt.executeQuery(sqlStatement);


            //Displays contents of the result set
            while (result.next()){
                System.out.println(result.getString("ReferenceNumber"));
            }

            conn.close();
        } catch(Exception ex){
            System.out.println("ERROR: " + ex.getMessage());
        }
    }
}
