package clases;

import java.sql.*;

public class Conexion {

    public static Connection conectar() {
        try {
            String url = "jdbc:sqlite:bd/sistema_progreso.db"; 
            Connection cn = DriverManager.getConnection(url);
            return cn;
        } catch (SQLException e) {
            System.out.println("❌ Error al conectar a SQLite: " + e.getMessage());
        }
        return null;
    }
}


