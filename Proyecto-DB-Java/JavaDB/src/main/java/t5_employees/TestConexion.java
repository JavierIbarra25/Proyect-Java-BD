package t5_employees;
import java.sql.Connection;
import java.sql.DriverManager;

public class TestConexion {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/t5_employees?useSSL=false&allowPublicKeyRetrieval=true",
                "root", 
                "ROOT2023$"
            );
            System.out.println("¡Conexión exitosa con MySQL 9.3.0!");
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}