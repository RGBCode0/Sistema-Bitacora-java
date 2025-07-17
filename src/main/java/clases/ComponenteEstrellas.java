package clases;

/**
 *
 * @author Rael
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ComponenteEstrellas {

    private JLabel[] estrellas;
    private ImageIcon estrellaVacia;
    private ImageIcon estrellaLlena;
    private int valorSeleccionado = 0;
    private boolean esEditable;
    private int anchoIcono;
    private int altoIcono;

    public ComponenteEstrellas(JLabel[] estrellas, boolean esEditable) {
    this(estrellas, esEditable, estrellas[0].getWidth(), estrellas[0].getHeight());
    }

// 🔧 Nuevo constructor que permite definir el tamaño
    public ComponenteEstrellas(JLabel[] estrellas, boolean esEditable, int ancho, int alto) {
        this.estrellas = estrellas;
        this.esEditable = esEditable;
        this.anchoIcono = ancho;
        this.altoIcono = alto;
        inicializar();
    }

    private void inicializar() {

        estrellaVacia = escalarIcono("/images/estrella_vacia.png", anchoIcono, altoIcono);
        estrellaLlena = escalarIcono("/images/estrella_llena.png", anchoIcono, altoIcono);

        for (int i = 0; i < estrellas.length; i++) {
            final int idx = i;
            estrellas[i].setIcon(estrellaVacia);

            if (esEditable) {
                estrellas[i].setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                estrellas[i].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        int clic = idx + 1;
                        if (valorSeleccionado == clic) {
                            valorSeleccionado = Math.max(0, clic - 1);
                        } else {
                            valorSeleccionado = clic;
                        }
                        refrescarEstrellas(valorSeleccionado);
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        refrescarEstrellas(idx + 1);
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        refrescarEstrellas(valorSeleccionado);
                    }
                });
            }
        }

        refrescarEstrellas(valorSeleccionado);
    }

    private ImageIcon escalarIcono(String ruta, int w, int h) {
        Image img = new ImageIcon(getClass().getResource(ruta)).getImage();
        Image scaled = img.getScaledInstance(w, h, Image.SCALE_SMOOTH);
        return new ImageIcon(scaled);
    }

    public void refrescarEstrellas(int hasta) {
        for (int i = 0; i < estrellas.length; i++) {
            estrellas[i].setIcon(i < hasta ? estrellaLlena : estrellaVacia);
        }
    }

    /**
     * Devuelve el valor actual seleccionado (0 a 5).
     */
    public int getValor() {
        return valorSeleccionado;
    }

    /*
     * Establece el valor que se debe mostrar (0 a 5).
     */
    public void setValor(int valor) {
        this.valorSeleccionado = Math.max(0, Math.min(5, valor));
        refrescarEstrellas(valorSeleccionado);
    }

    /**
     * Habilita o deshabilita la edición.
     */
    public void setEditable(boolean editable) {
        this.esEditable = editable;
        inicializar(); // reinicia listeners
    }

    public boolean isEditable() {
        return esEditable;
    }
}
