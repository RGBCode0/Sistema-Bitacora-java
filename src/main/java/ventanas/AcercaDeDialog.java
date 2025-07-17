package ventanas;

import clases.CambiarIcono;
import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author Rael
 */
public class AcercaDeDialog extends javax.swing.JDialog {

    public AcercaDeDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        // Heredar el ícono de la ventana padre
        CambiarIcono.aplicarIcono(this);
        setLocationRelativeTo(parent);
        ImageIcon icono = new ImageIcon(getClass().getResource("/images/logo.png"));
        Image imagenEscalada = icono.getImage().getScaledInstance(170, 88, Image.SCALE_SMOOTH);
        labelImagen.setIcon(new ImageIcon(imagenEscalada));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        areaTexto = new javax.swing.JTextArea();
        botonCerrar = new javax.swing.JButton();
        labelImagen = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Acerca De");
        setBackground(new java.awt.Color(13, 17, 23));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panel.setBackground(new java.awt.Color(22, 27, 34));
        panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        areaTexto.setEditable(false);
        areaTexto.setBackground(new java.awt.Color(33, 37, 43));
        areaTexto.setColumns(20);
        areaTexto.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        areaTexto.setForeground(new java.awt.Color(255, 255, 255));
        areaTexto.setRows(5);
        areaTexto.setText("Sistema Bitacora\nVersión: 1.0.0\nDesarrollado por: Rael Gabriel Bautista\nDescripción: Herramienta de utilidad personal\npara llevar un registro de aprendizaje.\nAño: 2025\nContacto: raelgbcode@gmail.com\nGitHub: github.com/RGBCode0");
        jScrollPane1.setViewportView(areaTexto);

        panel.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 80, 360, 200));

        botonCerrar.setBackground(new java.awt.Color(34, 147, 248));
        botonCerrar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        botonCerrar.setForeground(new java.awt.Color(255, 255, 255));
        botonCerrar.setText("Cerrar");
        botonCerrar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botonCerrar.setPreferredSize(new java.awt.Dimension(95, 30));
        botonCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonCerrarActionPerformed(evt);
            }
        });
        panel.add(botonCerrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 290, -1, 40));
        panel.add(labelImagen, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 0, 170, 88));

        getContentPane().add(panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 451, 360));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCerrarActionPerformed
        dispose();
    }//GEN-LAST:event_botonCerrarActionPerformed

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AcercaDeDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AcercaDeDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AcercaDeDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AcercaDeDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                AcercaDeDialog dialog = new AcercaDeDialog(new javax.swing.JFrame(), true);
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
    private javax.swing.JTextArea areaTexto;
    private javax.swing.JButton botonCerrar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelImagen;
    private javax.swing.JPanel panel;
    // End of variables declaration//GEN-END:variables
}
