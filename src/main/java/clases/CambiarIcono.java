
package clases;
import java.awt.*;
/**
 *
 * @author Rael
 */
public class CambiarIcono {


    public static void aplicarIcono(Window ventana) {
        Image icono = Toolkit.getDefaultToolkit().getImage(
            ClassLoader.getSystemResource("images/icono.png")
        );
        ventana.setIconImage(icono);
    }
}