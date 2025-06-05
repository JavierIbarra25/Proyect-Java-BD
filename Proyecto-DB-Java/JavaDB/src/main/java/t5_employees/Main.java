package t5_employees;

public class Main {
    public static void main(String[] args) {
        // 1. Cargar el driver
        if (!mysqlmanager.loadDriver()) {
            System.err.println("Error al cargar el driver");
            return;
        }
        
        // 2. Conectar a la base de datos
        if (!mysqlmanager.connect()) {
            System.err.println("Error al conectar a la base de datos");
            return;
        }
        
        // 3. Verificar la conexión
        if (!mysqlmanager.isConnected()) {
            System.err.println("No hay conexión activa");
            return;
        }
        
        System.out.println("¡Conexión exitosa!");
        
        // 4. Probar consultas con StaffManager
        StaffManager staffManager = new StaffManager();
        System.out.println("\nProbando consulta directa:");
        staffManager.imprimeStaff(368); // Cambia el ID por uno que exista en tu DB
        
        System.out.println("\nProbando stored procedure:");
        staffManager.obtenerStaffSP(368); // Cambia el ID por uno que exista en tu DB
        
        // 5. Desconectar
        System.out.println("\nDesconectando...");
        mysqlmanager.disconnect();
    }
}