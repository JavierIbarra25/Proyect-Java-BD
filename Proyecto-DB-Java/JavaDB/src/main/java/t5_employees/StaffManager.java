package t5_employees;

import java.sql.*;
import java.time.LocalDate;
import java.sql.JDBCType;

public class StaffManager {

    private final String TB_STAFF_CODE = "Employee_Code";
    private final String TB_STAFF_NAME = "Name";
    private final String TB_STAFF_JOB = "Job";
    private final String TB_STAFF_SALARY = "Salary";
    private final String TB_STAFF_DEPTO = "Department_Code";
    private final String TB_STAFF_START = "Start_Date";
    private final String TB_STAF_SUPOFF = "Superior_Officer";
    private final String TB_STAFF = "staff";
    private final String TB_STAFF_SELECT = "SELECT * FROM " + TB_STAFF;

    private ResultSet getStaff(int id) {
        try {
            Statement stmt = mysqlmanager.conn.createStatement(
                ResultSet.TYPE_SCROLL_INSENSITIVE, 
                ResultSet.CONCUR_READ_ONLY);
            String sql = TB_STAFF_SELECT + " WHERE " + TB_STAFF_CODE + "=" + id;
            return stmt.executeQuery(sql);
        } catch (SQLException ex) {
            System.err.println("Error al obtener staff: " + ex.getMessage());
            return null;
        }
    }

    public void imprimeStaff(int id) {
        try {
            ResultSet rs = this.getStaff(id);
            if (rs == null || !rs.first()) {
                System.out.println("Staff " + id + " NO EXISTE");
                return;
            }
            System.out.printf("Staff: %d\t%s\t%s\t%d\t%d\t%s\t%d\n",
                rs.getInt(TB_STAFF_CODE),
                rs.getString(TB_STAFF_NAME),
                rs.getString(TB_STAFF_JOB),
                rs.getInt(TB_STAFF_SALARY),
                rs.getInt(TB_STAFF_DEPTO),
                rs.getString(TB_STAFF_START),
                rs.getInt(TB_STAF_SUPOFF));
        } catch (SQLException ex) {
            System.err.println("Error al imprimir staff: " + ex.getMessage());
        }
    }

    public void obtenerStaffSP(int id) {
        try {
            String sql = "{CALL obtener_staff(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
            CallableStatement stmt = mysqlmanager.conn.prepareCall(sql);
            stmt.setInt(1, id);

            stmt.registerOutParameter(2, JDBCType.SMALLINT);
            stmt.registerOutParameter(3, JDBCType.VARCHAR);
            stmt.registerOutParameter(4, JDBCType.VARCHAR);
            stmt.registerOutParameter(5, JDBCType.SMALLINT);
            stmt.registerOutParameter(6, JDBCType.SMALLINT);
            stmt.registerOutParameter(7, JDBCType.DATE);
            stmt.registerOutParameter(8, JDBCType.SMALLINT);
            stmt.registerOutParameter(9, JDBCType.INTEGER);
            stmt.registerOutParameter(10, JDBCType.VARCHAR);

            stmt.execute();

            int status = stmt.getInt(9);
            String msg = stmt.getString(10);

            if (status == 0) {
                System.out.printf("SP → %d\t%s\t%s\t%d\t%d\t%s\t%d\n",
                    stmt.getInt(2), stmt.getString(3), stmt.getString(4),
                    stmt.getInt(5), stmt.getInt(6), stmt.getDate(7), stmt.getInt(8));
            } else {
                System.err.println("Error: " + status + " - " + msg);
            }

        } catch (SQLException ex) {
            System.err.println("Error SP obtener: " + ex.getMessage());
        }
    }

    public void insertarStaffSP(int id, String nombre, String puesto, int salario, 
                                 Integer depto, LocalDate fechaInicio, Integer superior) {
        try {
            String sql = "{CALL insertar_staff(?, ?, ?, ?, ?, ?, ?, ?, ?)}";
            CallableStatement stmt = mysqlmanager.conn.prepareCall(sql);

            stmt.setInt(1, id);
            stmt.setString(2, nombre);
            stmt.setString(3, puesto);
            stmt.setInt(4, salario);
            if (depto != null) stmt.setInt(5, depto); else stmt.setNull(5, JDBCType.SMALLINT.getVendorTypeNumber());
            stmt.setDate(6, Date.valueOf(fechaInicio));
            if (superior != null) stmt.setInt(7, superior); else stmt.setNull(7, JDBCType.SMALLINT.getVendorTypeNumber());

            stmt.registerOutParameter(8, JDBCType.INTEGER);
            stmt.registerOutParameter(9, JDBCType.VARCHAR);

            stmt.execute();

            System.out.println("INSERTAR → Status: " + stmt.getInt(8));
            System.out.println("Mensaje: " + stmt.getString(9));

        } catch (SQLException ex) {
            System.err.println("Error SP insertar: " + ex.getMessage());
        }
    }

    public void actualizarStaffSP(int id, String nombre, String puesto, int salario, 
                                  Integer depto, LocalDate fechaInicio, Integer superior) {
        try {
            String sql = "{CALL actualizar_staff(?, ?, ?, ?, ?, ?, ?, ?, ?)}";
            CallableStatement stmt = mysqlmanager.conn.prepareCall(sql);

            stmt.setInt(1, id);
            stmt.setString(2, nombre);
            stmt.setString(3, puesto);
            stmt.setInt(4, salario);
            if (depto != null) stmt.setInt(5, depto); else stmt.setNull(5, JDBCType.SMALLINT.getVendorTypeNumber());
            stmt.setDate(6, Date.valueOf(fechaInicio));
            if (superior != null) stmt.setInt(7, superior); else stmt.setNull(7, JDBCType.SMALLINT.getVendorTypeNumber());

            stmt.registerOutParameter(8, JDBCType.INTEGER);
            stmt.registerOutParameter(9, JDBCType.VARCHAR);

            stmt.execute();

            System.out.println("ACTUALIZAR → Status: " + stmt.getInt(8));
            System.out.println("Mensaje: " + stmt.getString(9));

        } catch (SQLException ex) {
            System.err.println("Error SP actualizar: " + ex.getMessage());
        }
    }

    public void eliminarStaffSP(int id) {
        try {
            String sql = "{CALL eliminar_staff(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
            CallableStatement stmt = mysqlmanager.conn.prepareCall(sql);

            stmt.setInt(1, id);

            stmt.registerOutParameter(2, JDBCType.SMALLINT);
            stmt.registerOutParameter(3, JDBCType.VARCHAR);
            stmt.registerOutParameter(4, JDBCType.VARCHAR);
            stmt.registerOutParameter(5, JDBCType.SMALLINT);
            stmt.registerOutParameter(6, JDBCType.SMALLINT);
            stmt.registerOutParameter(7, JDBCType.DATE);
            stmt.registerOutParameter(8, JDBCType.SMALLINT);
            stmt.registerOutParameter(9, JDBCType.INTEGER);
            stmt.registerOutParameter(10, JDBCType.VARCHAR);

            stmt.execute();

            int status = stmt.getInt(9);
            String msg = stmt.getString(10);

            System.out.println("ELIMINAR → Status: " + status);
            System.out.println("Mensaje: " + msg);

            if (status == 0) {
                System.out.println("Empleado eliminado: " + stmt.getString(3));
            }

        } catch (SQLException ex) {
            System.err.println("Error SP eliminar: " + ex.getMessage());
        }
    }
}

