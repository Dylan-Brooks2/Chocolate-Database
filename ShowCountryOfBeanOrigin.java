import java.sql.*;

public class ShowCountryOfBeanOrigin {
    public static void main(String[] args){
        final String DB_URL = "jdbc:derby:ChocolateDB";

        try{
            //creates connection to DB
            Connection conn = DriverManager.getConnection(DB_URL);

            //creates a statement object
            Statement stmt = conn.createStatement();

            //Creates a string with a SELECT statement
            String sqlStatement = "SELECT ReferenceNumber, CountryOfBeanOrigin FROM Chocolates";

            //sends statement to DBMS
            ResultSet result = stmt.executeQuery(sqlStatement);

            //Displays contents
            while (result.next()){
                System.out.printf("%s %25s\n",
                        result.getString("ReferenceNumber"),
                        result.getString("CountryOfBeanOrigin"));
            }

            conn.close();
        }
        catch(Exception ex){
            System.out.println("ERROR: " + ex.getMessage());
        }
    }
}
