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
        
      // 4. Usar StaffManager
        StaffManager staffManager = new StaffManager();

        System.out.println("\n[1] ➤ Probar consulta directa (SELECT):");
        staffManager.imprimeStaff(368);

        System.out.println("\n[2] ➤ Probar procedimiento: obtener_staff");
        staffManager.obtenerStaffSP(368);

        System.out.println("\n[3] ➤ Probar procedimiento: insertar_staff");
        staffManager.insertarStaffSP(
            3001, "Laura Pérez", "tester", 4200, 5,
            LocalDate.of(2024, 6, 1), 1008
        );

        System.out.println("\n[4] ➤ Probar procedimiento: actualizar_staff");
        staffManager.actualizarStaffSP(
            3001, "Laura Pérez", "qa analyst", 4600, 5,
            LocalDate.of(2024, 6, 1), 1008
        );

        System.out.println("\n[5] ➤ Probar procedimiento: eliminar_staff");
        staffManager.eliminarStaffSP(3001);

        
        // 5. Desconectar
        System.out.println("\nDesconectando...");
        mysqlmanager.disconnect();
    }
}
