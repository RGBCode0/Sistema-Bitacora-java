
package ventanas;

import clases.Conexion;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author Rael
 */
public class VentanaDescripcion extends javax.swing.JDialog {
   
    private final int idRegistroBD;
    private String descripcionBD;
    private String tituloBD;
    private boolean abrioVentanaEdicion = false;

    private boolean eliminado=false;      //indica que no se a eliminado
    public VentanaDescripcion(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.idRegistroBD = 0;
    }
    
    public VentanaDescripcion(java.awt.Window owner, ModalityType modalityType,int idRegistroBD,String descripcionBD, String tituloBD) {
    super(owner, modalityType);
    this.idRegistroBD=idRegistroBD;
    this.descripcionBD=descripcionBD;
    this.tituloBD=tituloBD;
    this.setTitle("Descripcion Aprendizaje");

    initComponents();
    setLocationRelativeTo(owner);
    areaDescripcion.setText(descripcionBD);
    labelTitulo.setText(tituloBD);
    botonEliminar.addActionListener(e -> eliminarRegistro());
    botonCerrar.addActionListener(e -> dispose());
    areaDescripcion.setLineWrap(true); // Activar salto automático
    areaDescripcion.setWrapStyleWord(true); // Ajuste por palabra, no por carácter
}
    
    public boolean seAbrioEdicion() {
    return abrioVentanaEdicion;
    }

public void eliminarRegistro(){
        int respuesta = JOptionPane.showConfirmDialog(
                this,
                "¿Seguro que deseas eliminar este registro?\nEsta acción no se puede deshacer.",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
        
        if (respuesta != JOptionPane.YES_OPTION) return;
        
        String sql = "DELETE FROM registros WHERE id_registro = ?";

        try (Connection cn = Conexion.conectar();
             PreparedStatement pst = cn.prepareStatement(sql)) {

            pst.setInt(1, idRegistroBD);
            int filas = pst.executeUpdate();

            if (filas > 0) {
                eliminado = true;                // marca que se borró
                dispose();                       // cierra el diálogo
            } else {
                JOptionPane.showMessageDialog(
                        this, "No se encontró el registro.",
                        "Sin cambios", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Error al eliminar:\n" + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
}
    
    public boolean seElimino() {
        return eliminado;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelDialog = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        areaDescripcion = new javax.swing.JTextArea();
        botonCerrar = new javax.swing.JButton();
        botonActualizar = new javax.swing.JButton();
        botonEliminar = new javax.swing.JButton();
        labelTitulo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        panelDialog.setBackground(new java.awt.Color(13, 17, 23));

        areaDescripcion.setBackground(new java.awt.Color(20, 24, 32));
        areaDescripcion.setColumns(20);
        areaDescripcion.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        areaDescripcion.setForeground(new java.awt.Color(255, 255, 255));
        areaDescripcion.setRows(5);
        areaDescripcion.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.lightGray, java.awt.Color.lightGray, java.awt.Color.lightGray));
        jScrollPane1.setViewportView(areaDescripcion);

        botonCerrar.setBackground(new java.awt.Color(34, 147, 248));
        botonCerrar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        botonCerrar.setForeground(new java.awt.Color(255, 255, 255));
        botonCerrar.setText("Cerrar");
        botonCerrar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botonCerrar.setPreferredSize(new java.awt.Dimension(95, 30));

        botonActualizar.setBackground(new java.awt.Color(38, 76, 125));
        botonActualizar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        botonActualizar.setForeground(new java.awt.Color(255, 255, 255));
        botonActualizar.setText("Actualizar");
        botonActualizar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botonActualizar.setPreferredSize(new java.awt.Dimension(95, 30));
        botonActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonActualizarActionPerformed(evt);
            }
        });

        botonEliminar.setBackground(new java.awt.Color(184, 54, 68));
        botonEliminar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        botonEliminar.setForeground(new java.awt.Color(255, 255, 255));
        botonEliminar.setText("Eliminar");
        botonEliminar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botonEliminar.setPreferredSize(new java.awt.Dimension(95, 30));

        labelTitulo.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        labelTitulo.setForeground(new java.awt.Color(201, 209, 217));
        labelTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout panelDialogLayout = new javax.swing.GroupLayout(panelDialog);
        panelDialog.setLayout(panelDialogLayout);
        panelDialogLayout.setHorizontalGroup(
            panelDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDialogLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelDialogLayout.createSequentialGroup()
                        .addComponent(botonEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36)
                        .addComponent(botonActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)
                        .addComponent(botonCerrar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(117, 117, 117))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelDialogLayout.createSequentialGroup()
                        .addComponent(labelTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 454, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(69, 69, 69))))
            .addGroup(panelDialogLayout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 477, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 58, Short.MAX_VALUE))
        );
        panelDialogLayout.setVerticalGroup(
            panelDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDialogLayout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(labelTitulo)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panelDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botonEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonCerrar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(52, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelDialog, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelDialog, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonActualizarActionPerformed
    abrioVentanaEdicion = true;
    dispose(); // cierra el JDialog primero
        SwingUtilities.invokeLater(() -> {
        VentanaRegistro edicion = new VentanaRegistro(idRegistroBD);

        // Al cerrar edición, volver a mostrar la principal
        edicion.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                // Mostrar siempre la ventana principal
                getOwner().setVisible(true);
                if (edicion.seActualizoRegistro()) {
                    ((Principal) getOwner()).cargarRegistros();
                    ((Principal) getOwner()).mostrarMensaje("Registro actualizado correctamente");
                }


            }
        });

        edicion.setVisible(true);
    });
    
    }//GEN-LAST:event_botonActualizarActionPerformed

    public static void main(String args[]) {
     
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VentanaDescripcion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VentanaDescripcion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VentanaDescripcion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VentanaDescripcion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
   
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                VentanaDescripcion dialog = new VentanaDescripcion(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea areaDescripcion;
    private javax.swing.JButton botonActualizar;
    private javax.swing.JButton botonCerrar;
    private javax.swing.JButton botonEliminar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelTitulo;
    private javax.swing.JPanel panelDialog;
    // End of variables declaration//GEN-END:variables
}
