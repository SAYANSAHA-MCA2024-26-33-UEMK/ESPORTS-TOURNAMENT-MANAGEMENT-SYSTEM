import java.sql.Connection;

public class con {
    public static void main(String[] args) {

        Connection con = DBConnection.getConnection();

        if(con != null){
            System.out.println("Connection is WORKING ✅");
        } else {
            System.out.println("Connection is FAILED ❌");
        }
    }
}