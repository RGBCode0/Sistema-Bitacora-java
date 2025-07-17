
package ventanas;
import clases.RecargarCategoria;
import clases.CambiarIcono;
import clases.CategoriaItem;
import clases.CategoriaServicio;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import clases.Conexion;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.JTableHeader;

/**
 *
 * @author Rael
 */
public class VentanaCategoria extends javax.swing.JFrame {
    private RecargarCategoria ventanaPadre;
    
    DefaultTableModel modeloTablaCat;
    public VentanaCategoria() {
        initComponents();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        CambiarIcono.aplicarIcono(this);
        this.setTitle("Categorias");
        this.setLocationRelativeTo(null);
        modeloTablaCat = new DefaultTableModel(new Object[]{"ID", "CATEGORIAS DISPONIBLES"}, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    tablaCategoria.setModel(modeloTablaCat);
    tablaCategoria.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    
     // 3️⃣  ⬇️ Ocultas la primera columna (ID) —
    //     esto debe ir DESPUÉS de setModel, porque ahí es cuando
   //     el JTable crea su TableColumnModel.
    tablaCategoria.getColumnModel().getColumn(0).setMinWidth(0);
    tablaCategoria.getColumnModel().getColumn(0).setMaxWidth(0);
    tablaCategoria.getColumnModel().getColumn(0).setWidth(0);

     cargarCategoriasEnTabla();
    }
    
    public VentanaCategoria(RecargarCategoria ventanaPadre){
    this();
    this.ventanaPadre=ventanaPadre;
     addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                if (ventanaPadre != null) {
                    ventanaPadre.recargarCategorias();
                }
                   if (ventanaPadre instanceof JFrame frame) {
                    frame.setVisible(true);
                }
            }
        });
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelCategoria = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaCategoria = new javax.swing.JTable();
        botonCerrar = new javax.swing.JButton();
        botonAgregar = new javax.swing.JButton();
        botonEditar = new javax.swing.JButton();
        botonEliminar = new javax.swing.JButton();
        labelMensajeCat = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        panelCategoria.setBackground(new java.awt.Color(13, 17, 23));

        tablaCategoria.setBackground(new java.awt.Color(22, 27, 34));
        tablaCategoria.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        tablaCategoria.setForeground(new java.awt.Color(201, 209, 217));
        tablaCategoria.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tablaCategoria.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tablaCategoria.setRowHeight(30);
        tablaCategoria.setSelectionBackground(new java.awt.Color(40, 80, 120));
        tablaCategoria.setSelectionForeground(new java.awt.Color(255, 255, 255));
        tablaCategoria.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        //Aplicar estilos al encabezado
        JTableHeader header = tablaCategoria.getTableHeader();
        header.setPreferredSize(new Dimension(0, 35));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setBackground(new Color(40, 48, 60));
        jScrollPane1.setViewportView(tablaCategoria);

        botonCerrar.setBackground(new java.awt.Color(34, 147, 248));
        botonCerrar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        botonCerrar.setForeground(new java.awt.Color(255, 255, 255));
        botonCerrar.setText("Cerrar");
        botonCerrar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botonCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonCerrarActionPerformed(evt);
            }
        });

        botonAgregar.setBackground(new java.awt.Color(38, 125, 76));
        botonAgregar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        botonAgregar.setForeground(new java.awt.Color(255, 255, 255));
        botonAgregar.setText("Agregar");
        botonAgregar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botonAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAgregarActionPerformed(evt);
            }
        });

        botonEditar.setBackground(new java.awt.Color(38, 76, 125));
        botonEditar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        botonEditar.setForeground(new java.awt.Color(255, 255, 255));
        botonEditar.setText("Editar");
        botonEditar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botonEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonEditarActionPerformed(evt);
            }
        });

        botonEliminar.setBackground(new java.awt.Color(184, 54, 68));
        botonEliminar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        botonEliminar.setForeground(new java.awt.Color(255, 255, 255));
        botonEliminar.setText("Eliminar");
        botonEliminar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botonEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonEliminarActionPerformed(evt);
            }
        });

        labelMensajeCat.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        labelMensajeCat.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout panelCategoriaLayout = new javax.swing.GroupLayout(panelCategoria);
        panelCategoria.setLayout(panelCategoriaLayout);
        panelCategoriaLayout.setHorizontalGroup(
            panelCategoriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCategoriaLayout.createSequentialGroup()
                .addGroup(panelCategoriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelCategoriaLayout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addGroup(panelCategoriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addGroup(panelCategoriaLayout.createSequentialGroup()
                                .addComponent(botonAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(botonEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(botonEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(botonCerrar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(panelCategoriaLayout.createSequentialGroup()
                        .addGap(175, 175, 175)
                        .addComponent(labelMensajeCat)))
                .addContainerGap(32, Short.MAX_VALUE))
        );
        panelCategoriaLayout.setVerticalGroup(
            panelCategoriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCategoriaLayout.createSequentialGroup()
                .addContainerGap(67, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelMensajeCat)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelCategoriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botonEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonCerrar, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelCategoria, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelCategoria, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCerrarActionPerformed
        dispose();
           if (ventanaPadre instanceof JFrame frame) {
                frame.setVisible(true);
           }
        
    }//GEN-LAST:event_botonCerrarActionPerformed

    private void botonAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAgregarActionPerformed
        agregarCategoria();
    }//GEN-LAST:event_botonAgregarActionPerformed

    private void botonEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonEditarActionPerformed
       editarCategoria();
    }//GEN-LAST:event_botonEditarActionPerformed

    private void botonEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonEliminarActionPerformed
       eliminarCategoria();
    }//GEN-LAST:event_botonEliminarActionPerformed

private void cargarCategoriasEnTabla() {
    modeloTablaCat.setRowCount(0); // Limpia la tabla
    boolean hayCategorias = false;

    for (CategoriaItem item : CategoriaServicio.obtenerCategorias()) {
        modeloTablaCat.addRow(new Object[]{
            item.getId(),
            item.getNombre()
        });
        hayCategorias = true;
    }

    if (hayCategorias) {
        labelMensajeCat.setText("");
    } else {
        labelMensajeCat.setText("Sin categorias registradas.");
    }
      // Seleccionar la primera fila si hay al menos una
     if (modeloTablaCat.getRowCount() > 0) {
     tablaCategoria.setRowSelectionInterval(0, 0);
    }
}


    
   private void agregarCategoria() {
    String nombre = JOptionPane.showInputDialog(this, "Nombre de la nueva categoría:");
    if (nombre == null || nombre.trim().isEmpty()) return;

    String sql = "INSERT INTO categorias(nombre) VALUES(?)";
    try (Connection cn = Conexion.conectar();
         PreparedStatement pst = cn.prepareStatement(sql)) {

        pst.setString(1, nombre.trim());
        pst.executeUpdate();
        cargarCategoriasEnTabla();
        JOptionPane.showMessageDialog(this, "Categoría agregada.");

    } catch (SQLException ex) {
        if (ex.getMessage().contains("UNIQUE")) {
            JOptionPane.showMessageDialog(this, "❌ Ya existe una categoría con ese nombre.");
        } else {
            JOptionPane.showMessageDialog(this, "❌ Error:\n" + ex.getMessage());
        }
    }
}

private void editarCategoria() {
    int fila = tablaCategoria.getSelectedRow();
    if (fila == -1) return;

    int id = (int) modeloTablaCat.getValueAt(fila, 0);
    String actual = (String) modeloTablaCat.getValueAt(fila, 1);

    String nuevo = JOptionPane.showInputDialog(this, "Editar nombre:", actual);
    if (nuevo == null || nuevo.trim().isEmpty()) return;

    String sql = "UPDATE categorias SET nombre = ? WHERE id_categoria = ?";
    try (Connection cn = Conexion.conectar();
         PreparedStatement pst = cn.prepareStatement(sql)) {

        pst.setString(1, nuevo.trim());
        pst.setInt(2, id);
        pst.executeUpdate();
        cargarCategoriasEnTabla();
        JOptionPane.showMessageDialog(this, "Categoría actualizada.");

    } catch (SQLException ex) {
        if (ex.getMessage().contains("UNIQUE")) {
            JOptionPane.showMessageDialog(this, "❌ Ya existe una categoría con ese nombre.");
        } else {
            JOptionPane.showMessageDialog(this, "❌ Error:\n" + ex.getMessage());
        }
    }
}

private void eliminarCategoria() {
    int fila = tablaCategoria.getSelectedRow();
    if (fila == -1) return;

    int id = (int) modeloTablaCat.getValueAt(fila, 0);
    String nombre = (String) modeloTablaCat.getValueAt(fila, 1);

    int respuesta = JOptionPane.showConfirmDialog(
        this,
        "¿Eliminar la categoría '" + nombre + "'?",
        "Confirmar",
        JOptionPane.YES_NO_OPTION
    );

    if (respuesta != JOptionPane.YES_OPTION) return;

    // Validar que no esté en uso
    String validarSql = "SELECT COUNT(*) FROM registros WHERE id_categoria = ?";
    try (Connection cn = Conexion.conectar();
         PreparedStatement check = cn.prepareStatement(validarSql)) {

        check.setInt(1, id);
        ResultSet rs = check.executeQuery();
        if (rs.next() && rs.getInt(1) > 0) {
            JOptionPane.showMessageDialog(this, "❌ No se puede eliminar: categoría en uso.");
            return;
        }
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, "❌ Error al verificar uso:\n" + ex.getMessage());
        return;
    }

    // Si no está en uso, eliminar
    String sql = "DELETE FROM categorias WHERE id_categoria = ?";
    try (Connection cn = Conexion.conectar();
         PreparedStatement pst = cn.prepareStatement(sql)) {

        pst.setInt(1, id);
        pst.executeUpdate();
        cargarCategoriasEnTabla();
        JOptionPane.showMessageDialog(this, "✅ Categoría eliminada.");

    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, "❌ Error:\n" + ex.getMessage());
    }
}



 
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VentanaCategoria().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonAgregar;
    private javax.swing.JButton botonCerrar;
    private javax.swing.JButton botonEditar;
    private javax.swing.JButton botonEliminar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelMensajeCat;
    private javax.swing.JPanel panelCategoria;
    private javax.swing.JTable tablaCategoria;
    // End of variables declaration//GEN-END:variables
}
