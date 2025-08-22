import java.sql.*;

public class TestDatabaseConnection {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/ciempies_sgi?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
        String username = "root";
        String password = "";
        
        try {
            System.out.println("🔍 Probando conexión a la base de datos...");
            
            // Cargar el driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("✅ Driver MySQL cargado correctamente");
            
            // Establecer conexión
            Connection connection = DriverManager.getConnection(url, username, password);
            System.out.println("✅ Conexión establecida correctamente");
            
            // Probar consulta
            String sql = "SELECT id, nombre, correo, rol, activo FROM usuarios WHERE correo = 'admin@ciempies.com'";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            
            if (resultSet.next()) {
                System.out.println("✅ Usuario encontrado:");
                System.out.println("   ID: " + resultSet.getLong("id"));
                System.out.println("   Nombre: " + resultSet.getString("nombre"));
                System.out.println("   Correo: " + resultSet.getString("correo"));
                System.out.println("   Rol: " + resultSet.getString("rol"));
                System.out.println("   Activo: " + resultSet.getBoolean("activo"));
            } else {
                System.out.println("❌ Usuario no encontrado");
            }
            
            // Cerrar conexión
            resultSet.close();
            statement.close();
            connection.close();
            System.out.println("✅ Conexión cerrada correctamente");
            
        } catch (ClassNotFoundException e) {
            System.out.println("❌ Error: Driver MySQL no encontrado");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("❌ Error de SQL: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("❌ Error inesperado: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
