package ventanas;

import clases.CategoriaItem;
import clases.CategoriaServicio;
import clases.CambiarIcono;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import clases.Conexion;
import clases.EstrellasVisualRenderer;
import clases.RecargarCategoria;
import java.awt.Color;
import java.awt.Dialog.ModalityType;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Rael
 */
public final class Principal extends javax.swing.JFrame implements RecargarCategoria{

    DefaultTableModel modelo;
    private TableRowSorter<DefaultTableModel> sorter;
    private boolean columnasOcultas = false;

    public Principal() {
        initComponents();
        CambiarIcono.aplicarIcono(this);
        modelo = (DefaultTableModel) tabla.getModel();
        cargarRegistros();
        cargarCategoriasEnCombo();
        agregarEventoFilaTabla();
        setResizable(false);
        setLocationRelativeTo(null);
        setTitle("Seguimiento Progreso");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        sorter = new TableRowSorter<>(modelo);
        tabla.setRowSorter(sorter);
        tabla.getColumnModel().getColumn(3).setCellRenderer(new EstrellasVisualRenderer());


        // Eventos
        cajaTitulo.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                aplicarFiltros();
            }

            public void removeUpdate(DocumentEvent e) {
                aplicarFiltros();
            }

            public void changedUpdate(DocumentEvent e) {
                aplicarFiltros();
            }
        });

        comboCategoria.addActionListener(e -> aplicarFiltros());
        botonAgregarRegistro.addActionListener(e->{
        this.setVisible(false);
         new VentanaRegistro(this).setVisible(true);
        });
        
        botonCategoria.addActionListener(e ->{
        this.setVisible(false);
        new VentanaCategoria(this).setVisible(true);
        });
        
        botonAcercaDe.addActionListener(e -> {
            java.awt.Frame parent = (java.awt.Frame) SwingUtilities.getWindowAncestor(this);
            AcercaDeDialog dialogo = new AcercaDeDialog(parent, true);
            dialogo.setVisible(true);
        });
        
    }

    DateTimeFormatter salida = DateTimeFormatter.ofPattern("dd-MMMM-yyyy", new Locale("es", "ES"));

    private void cargarCategoriasEnCombo() {
    comboCategoria.removeAllItems();
    comboCategoria.addItem(new CategoriaItem(0, "Todos")); // ← Selección por defecto

    for (CategoriaItem item : CategoriaServicio.obtenerCategorias()) {
        comboCategoria.addItem(item);
    }
    comboCategoria.setSelectedIndex(0);
}   
    
    @Override
    public void recargarCategorias() {
    cargarCategoriasEnCombo(); // método que actualiza el JComboBox
    }


    private void aplicarFiltros() {
    String titulo = cajaTitulo.getText().trim();
    CategoriaItem seleccion = (CategoriaItem) comboCategoria.getSelectedItem();
    List<RowFilter<DefaultTableModel, Object>> filtros = new ArrayList<>();

    if (!titulo.isEmpty()) {
        filtros.add(RowFilter.regexFilter("(?i)" + Pattern.quote(titulo), 4)); // ✅ columna 4 = Título
    }

    if (seleccion != null && seleccion.getId() != 0) {
        filtros.add(RowFilter.regexFilter("(?i)" + Pattern.quote(seleccion.getNombre()), 3)); // ✅ columna 3 = Categoría
    }

    if (filtros.isEmpty()) {
        sorter.setRowFilter(null);
    } else {
        sorter.setRowFilter(RowFilter.andFilter(filtros));
    }
}


    public void cargarRegistros() {
    modelo.setRowCount(0); // Limpia la tabla
    boolean hayFilas = false;

    String sql = """
        SELECT r.id_registro, r.fecha, c.nombre AS categoria, r.titulo, r.descripcion, r.satisfaccion
        FROM registros r
        JOIN categorias c ON r.id_categoria = c.id_categoria
        ORDER BY r.fecha DESC
    """;

    try (
        Connection cn = Conexion.conectar();
        PreparedStatement pst = cn.prepareStatement(sql);
        ResultSet rs = pst.executeQuery()
    ) {
        while (rs.next()) {
            hayFilas = true;
            labelMensaje.setText("");

            // En SQLite la fecha puede venir como String
            String fechaTexto = rs.getString("fecha"); // Formato: "YYYY-MM-DD"
            LocalDate fecha = LocalDate.parse(fechaTexto); // convierte a LocalDate
            String fechaStr = fecha.format(salida); // salida es un DateTimeFormatter

            modelo.addRow(new Object[]{
                rs.getInt("id_registro"),
                rs.getString("descripcion"),
                fechaStr,
                rs.getString("categoria"),
                rs.getString("titulo"),
                rs.getInt("satisfaccion")
            });
        }

    } catch (Exception ex) {
        mostrarMensaje("Error al cargar los datos:\n" + ex.getMessage());
    }

    ocultarColumnas();

    if (!hayFilas) {
        labelMensaje.setText("No hay ningún Registro");
    }
}


    /**
     * Ocultar las dos primeras columnas (ID y descripción)
     */
    private void ocultarColumnas() {
        if (!columnasOcultas) {
            TableColumnModel tcm = tabla.getColumnModel();
            tcm.removeColumn(tcm.getColumn(0)); // id
            tcm.removeColumn(tcm.getColumn(0)); // descripción (ahora es el 0)
            columnasOcultas = true;
        }
    }

    //UNIFICAR MENSAJES
    public void mostrarMensaje(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Información",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void agregarEventoFilaTabla() {
        //Agregar evento a las filas
        tabla.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //Doble clic opcional: e.getClickCount() == 2 && 
                if (SwingUtilities.isLeftMouseButton(e)) {
                    int vistaFila = tabla.rowAtPoint(e.getPoint());
                    if (vistaFila == -1) {
                        return;
                    }

                    int modeloFila = tabla.convertRowIndexToModel(vistaFila);

                    int id_registro = (int) modelo.getValueAt(modeloFila, 0); // PK oculta
                    String descripcion = (String) modelo.getValueAt(modeloFila, 1); // columna oculta “descripcion”
                    String titulo = (String) modelo.getValueAt(modeloFila, 4); // columna visible “titulo”

                    // 1. Referencia a la ventana principal (padre)
                    Window owner = SwingUtilities.getWindowAncestor(tabla);

                    //ocultar la ventana principal
                    owner.setVisible(false);

                    // 2) abrir el diálogo
                    VentanaDescripcion ventanaDialogo = new VentanaDescripcion(owner, ModalityType.APPLICATION_MODAL, id_registro, descripcion, titulo);
                    ventanaDialogo.setVisible(true);
                    boolean edicionAbierta = ventanaDialogo.seAbrioEdicion();

                    ventanaDialogo.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosed(WindowEvent e) {
                            if (!edicionAbierta) {
                                owner.setVisible(true); // solo si NO se fue a edición
                            }

                            if (ventanaDialogo.seElimino()) {          // ¿el usuario lo eliminó?
                                cargarRegistros();          // refresca la JTable
                                mostrarMensaje("Registro eliminado Correctamente");
                            }

                        }
                    });

                }
            }

        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel = new javax.swing.JPanel();
        labelFiltrar = new javax.swing.JLabel();
        labelTitulo1 = new javax.swing.JLabel();
        comboCategoria = new javax.swing.JComboBox<>();
        labelBuscar = new javax.swing.JLabel();
        cajaTitulo = new javax.swing.JTextField();
        botonAgregarRegistro = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla = new javax.swing.JTable();
        botonSalir = new javax.swing.JButton();
        botonCategoria = new javax.swing.JButton();
        labelMensaje = new javax.swing.JLabel();
        botonAcercaDe = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(getIconImage());

        panel.setBackground(new java.awt.Color(13, 17, 23));
        panel.setToolTipText("");
        panel.setPreferredSize(new java.awt.Dimension(850, 600));

        labelFiltrar.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        labelFiltrar.setForeground(new java.awt.Color(255, 255, 255));
        labelFiltrar.setText("Filtrar:");
        labelFiltrar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        labelTitulo1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        labelTitulo1.setForeground(new java.awt.Color(255, 255, 255));
        labelTitulo1.setText("Bitácora de Aprendizaje");
        labelTitulo1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        comboCategoria.setBackground(new java.awt.Color(255, 255, 255));
        comboCategoria.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        comboCategoria.setForeground(new java.awt.Color(0, 0, 0));
        comboCategoria.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        labelBuscar.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        labelBuscar.setForeground(new java.awt.Color(255, 255, 255));
        labelBuscar.setText("Buscar por titulo:");
        labelBuscar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        cajaTitulo.setBackground(new java.awt.Color(255, 255, 255));
        cajaTitulo.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        cajaTitulo.setForeground(new java.awt.Color(0, 0, 0));

        botonAgregarRegistro.setBackground(new java.awt.Color(38, 125, 76));
        botonAgregarRegistro.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        botonAgregarRegistro.setForeground(new java.awt.Color(255, 255, 255));
        botonAgregarRegistro.setText("Agregar Registro");
        botonAgregarRegistro.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        tabla.setBackground(new java.awt.Color(22, 27, 34));
        tabla.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        tabla.setForeground(new java.awt.Color(201, 209, 217));
        tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID", "DESCRIPCION", "FECHA", "CATEGORIA", "TITULO", "SATISFACCIÓN"
            }
        ));
        tabla.setToolTipText("");
        tabla.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tabla.setRowHeight(30);
        tabla.setSelectionBackground(new java.awt.Color(40, 80, 120));
        tabla.setSelectionForeground(new java.awt.Color(255, 255, 255));
        //Centrar las parte de las filas
        DefaultTableCellRenderer centrado = new DefaultTableCellRenderer();
        centrado.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < tabla.getColumnCount(); i++) {
            tabla.getColumnModel().getColumn(i).setCellRenderer(centrado);
        }
        //Aplicar estilos al encabezado
        JTableHeader header = tabla.getTableHeader();
        header.setPreferredSize(new Dimension(0, 35));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setBackground(new Color(40, 48, 60)); // Un fondo oscuro elegante
        jScrollPane1.setViewportView(tabla);

        botonSalir.setBackground(new java.awt.Color(34, 147, 248));
        botonSalir.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        botonSalir.setForeground(new java.awt.Color(255, 255, 255));
        botonSalir.setText("Salir");
        botonSalir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botonSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonSalirActionPerformed(evt);
            }
        });

        botonCategoria.setBackground(new java.awt.Color(38, 76, 125));
        botonCategoria.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        botonCategoria.setForeground(new java.awt.Color(255, 255, 255));
        botonCategoria.setText("Gestionar Categoria");
        botonCategoria.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        labelMensaje.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        labelMensaje.setForeground(new java.awt.Color(255, 255, 255));

        botonAcercaDe.setBackground(new java.awt.Color(59, 89, 152));
        botonAcercaDe.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        botonAcercaDe.setForeground(new java.awt.Color(255, 255, 255));
        botonAcercaDe.setText("Acerca De");
        botonAcercaDe.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botonAcercaDe.setFocusPainted(false);

        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLayout.createSequentialGroup()
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelLayout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelLayout.createSequentialGroup()
                                .addComponent(botonAgregarRegistro, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(botonCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(botonSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelLayout.createSequentialGroup()
                                    .addComponent(labelFiltrar)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(comboCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(labelBuscar)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(cajaTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(27, 27, 27)
                                    .addComponent(botonAcercaDe))
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 764, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(panelLayout.createSequentialGroup()
                        .addGap(277, 277, 277)
                        .addComponent(labelTitulo1))
                    .addGroup(panelLayout.createSequentialGroup()
                        .addGap(356, 356, 356)
                        .addComponent(labelMensaje, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(54, Short.MAX_VALUE))
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(labelTitulo1)
                .addGap(30, 30, 30)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelFiltrar)
                    .addComponent(comboCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelBuscar)
                    .addComponent(cajaTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonAcercaDe, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelMensaje, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botonCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonAgregarRegistro, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(96, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonSalirActionPerformed
        System.exit(0);  // TODO add your handling code here:
    }//GEN-LAST:event_botonSalirActionPerformed

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Principal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonAcercaDe;
    private javax.swing.JButton botonAgregarRegistro;
    private javax.swing.JButton botonCategoria;
    private javax.swing.JButton botonSalir;
    private javax.swing.JTextField cajaTitulo;
    private javax.swing.JComboBox<CategoriaItem> comboCategoria;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelBuscar;
    private javax.swing.JLabel labelFiltrar;
    private javax.swing.JLabel labelMensaje;
    private javax.swing.JLabel labelTitulo1;
    private javax.swing.JPanel panel;
    private javax.swing.JTable tabla;
    // End of variables declaration//GEN-END:variables
}
