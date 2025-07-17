
package clases;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Rael
 */

public class CategoriaServicio {

    public static List<CategoriaItem> obtenerCategorias() {
        List<CategoriaItem> lista = new ArrayList<>();
        String sql = "SELECT id_categoria, nombre FROM categorias ORDER BY nombre";

        try (Connection cn = Conexion.conectar(); PreparedStatement pst = cn.prepareStatement(sql); ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                lista.add(new CategoriaItem(
                        rs.getInt("id_categoria"),
                        rs.getString("nombre")));
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al obtener categorías:\n" + ex.getMessage());
        }

        return lista;
    }
}

