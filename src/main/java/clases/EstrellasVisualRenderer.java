
package clases;

/**
 *
 * @author Rael
 */
import clases.ComponenteEstrellas;
import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;


public class EstrellasVisualRenderer extends JPanel implements TableCellRenderer {
    private final JLabel[] labels;
    private final ComponenteEstrellas componenteEstrellas;
    private final JPanel panelEstrellas;

    public EstrellasVisualRenderer() {
        setLayout(new GridBagLayout()); // 🔄 Centrado vertical y horizontal
        setOpaque(true);                // Acepta fondo personalizado

        // Subpanel con FlowLayout centrado para las estrellas
        panelEstrellas = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        panelEstrellas.setOpaque(true); // También acepta fondo

        labels = new JLabel[5];
        for (int i = 0; i < 5; i++) {
            labels[i] = new JLabel();
            labels[i].setPreferredSize(new Dimension(16, 16));
            labels[i].setOpaque(false); // 👈 Importante: transparencia de los JLabel
            panelEstrellas.add(labels[i]);
        }

        componenteEstrellas = new ComponenteEstrellas(labels, false, 16, 16);

        add(panelEstrellas); // agrega el subpanel centrado dentro del renderer
    }

    @Override
    public Component getTableCellRendererComponent(
            JTable table, Object value, boolean isSelected, boolean hasFocus,
            int row, int column) {

        int valor = (value instanceof Integer) ? (Integer) value : 0;
        componenteEstrellas.setValor(valor);

        // 👇 Determina el color de fondo según si está seleccionada la fila
        Color fondo = isSelected ? table.getSelectionBackground() : table.getBackground();

        setBackground(fondo);             // Fondo del panel principal
        panelEstrellas.setBackground(fondo); // Fondo del subpanel de estrellas

        return this;
    }
}

