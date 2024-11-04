package Libraries;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DatabaseSetup {
    public static void main(String[] args) throws SQLException {
        String url = "jdbc:sqlite:wedo_bdd.db";

        try (Connection conn = DriverManager.getConnection(url)){
            if (conn != null) {
                System.out.println("Se ha establecido una conexión con la base de datos.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
}
