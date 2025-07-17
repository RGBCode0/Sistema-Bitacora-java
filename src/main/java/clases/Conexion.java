package clases;

import java.sql.*;

public class Conexion {

    public static Connection conectar() {
        try {
            // Ruta relativa o absoluta al archivo .sqlite o .db
            String url = "jdbc:sqlite:bd/sistema_progreso.db"; // puede ser "./mi_base.sqlite"
            Connection cn = DriverManager.getConnection(url);
            return cn;
        } catch (SQLException e) {
            System.out.println("❌ Error al conectar a SQLite: " + e.getMessage());
        }
        return null;
    }
}


