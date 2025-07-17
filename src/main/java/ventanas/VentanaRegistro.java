package ventanas;

import clases.CambiarIcono;
import clases.CategoriaItem;
import clases.CategoriaServicio;
import clases.ComponenteEstrellas;
import java.awt.Image;
import java.sql.*;
import clases.Conexion;
import clases.RecargarCategoria;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author Rael
 */
public class VentanaRegistro extends javax.swing.JFrame implements RecargarCategoria{

    private boolean modoEdicion = false;
    private boolean registroActualizado = false;

    private int idRegistroBD; // Solo se usa en modo edición

    //creando el formato de fecha
    private static final DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private ComponenteEstrellas componenteEstrellas;
    private JLabel[] stars;                // array de labels
    private int valorSeleccionado = 0;     // 0 = ninguna
    private JFrame frame;
    private Principal principal;
    
    public VentanaRegistro() {
        initComponents();
        CambiarIcono.aplicarIcono(this);
        //Obteniendo la fecha actual si no esta en modo edición 
        if (!modoEdicion) {
        cajaFecha.setText(LocalDate.now().format(formatoFecha));
    }

        //VALIDAR FORMATO SI SE MODIFICA
        cajaFecha.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (!validarFechaCampo()) {
                    JOptionPane.showMessageDialog(VentanaRegistro.this,
                            "Formato de fecha inválido. Usa Día-Mes-Año");
                    cajaFecha.setText(LocalDate.now().format(formatoFecha));
                    cajaFecha.requestFocus();
                }
            }
        });

        cargarCategoriasEnCombo();
        configurarEventosComboCategoria();
        setResizable(false);
        setLocationRelativeTo(null);
        setTitle("Agregar Registro");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);//no termina completamenente el programa

        // Usa getClass().getResource para rutas dentro de resources
        ImageIcon imagen = new ImageIcon(getClass().getResource("/images/fondo.png"));
        Icon fondo = new ImageIcon(imagen.getImage().getScaledInstance(labelImagen.getWidth(), labelImagen.getHeight(), Image.SCALE_DEFAULT));
        labelImagen.setIcon(fondo);

        pack();
        stars = new JLabel[]{labelStart1, labelStart2, labelStart3, labelStart4, labelStart5};
        componenteEstrellas = new ComponenteEstrellas(stars, true); // editable = true

        botonCerrar.setVisible(true);
        botonCancelar.setVisible(false);
        areaDescripcion.setLineWrap(true); // Activar salto automático
        areaDescripcion.setWrapStyleWord(true); // Ajuste por palabra, no por carácter

    }

    public VentanaRegistro(int idRegistro) {
        this(); // llama al constructor principal (ya carga categorías)
        this.idRegistroBD = idRegistro;
        this.modoEdicion = true;
        this.setTitle("Actualizar Registro");
        botonCancelar.setVisible(true);
        botonCerrar.setVisible(false);
        botonRegistrar.setText("Guardar cambios"); // Cambiar texto del botón}
        cargarDatos(idRegistro);
    }
    
    public VentanaRegistro(Principal principal){
    this();
    this.frame = principal;
    this.principal = principal;
            addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
            frame.setVisible(true);
            principal.cargarRegistros();
            principal.recargarCategorias();
            
            }
        });
    
    }
   
    @Override
    public void recargarCategorias() {
    cargarCategoriasEnCombo(); // método que actualiza el JComboBox
    }

    public boolean seActualizoRegistro() {
    return registroActualizado;
}

    //CARGAR DATOS MODO EDICIÓN
    private void cargarDatos(int idRegistro) {
    String sql = "SELECT titulo, descripcion, id_categoria, fecha, satisfaccion FROM registros WHERE id_registro = ?";

    try (Connection cn = Conexion.conectar(); PreparedStatement pst = cn.prepareStatement(sql)) {

        pst.setInt(1, idRegistro);
        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            // SQLite retorna fecha como texto
            String fechaTexto = rs.getString("fecha"); // ejemplo: "2025-07-14"
            LocalDate fecha = LocalDate.parse(fechaTexto); // convertir a LocalDate
            String fechaStr = fecha.format(formatoFecha); // aplicar formato

            cajaTitulo.setText(rs.getString("titulo"));
            areaDescripcion.setText(rs.getString("descripcion"));
            cajaFecha.setText(fechaStr);

            int idCategoria = rs.getInt("id_categoria");
            seleccionarCategoriaPorId(idCategoria);

            valorSeleccionado = rs.getInt("satisfaccion");
            componenteEstrellas.refrescarEstrellas(valorSeleccionado);
            componenteEstrellas.setValor(valorSeleccionado);
        }

    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this,
                "Error al cargar datos: " + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
    }
}


    private void seleccionarCategoriaPorId(int idCategoria) {
        for (int i = 0; i < comboCategoria.getItemCount(); i++) {
            CategoriaItem item = (CategoriaItem) comboCategoria.getItemAt(i);
            if (item.getId() == idCategoria) {
                comboCategoria.setSelectedIndex(i);
                break;
            }
        }
    }

    private boolean validarFechaCampo() {
        try {
            LocalDate.parse(cajaFecha.getText().trim(), formatoFecha);
            return true;
        } catch (DateTimeParseException ex) {
            return false;
        }
    }



    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelRegistro = new javax.swing.JPanel();
        labelTituloRegistro = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        areaDescripcion = new javax.swing.JTextArea();
        cajaFecha = new javax.swing.JTextField();
        cajaTitulo = new javax.swing.JTextField();
        labelFecha = new javax.swing.JLabel();
        labelFecha1 = new javax.swing.JLabel();
        labelFecha2 = new javax.swing.JLabel();
        labelStart5 = new javax.swing.JLabel();
        labelStart4 = new javax.swing.JLabel();
        labelStart3 = new javax.swing.JLabel();
        labelStart2 = new javax.swing.JLabel();
        labelStart1 = new javax.swing.JLabel();
        labelFecha3 = new javax.swing.JLabel();
        botonRegistrar = new javax.swing.JButton();
        botonCerrar = new javax.swing.JButton();
        labelImagen = new javax.swing.JLabel();
        labelFecha4 = new javax.swing.JLabel();
        comboCategoria = new javax.swing.JComboBox<>();
        botonCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(13, 17, 23));

        panelRegistro.setBackground(new java.awt.Color(13, 17, 23));
        panelRegistro.setPreferredSize(new java.awt.Dimension(850, 600));
        panelRegistro.setRequestFocusEnabled(false);

        labelTituloRegistro.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        labelTituloRegistro.setForeground(new java.awt.Color(255, 255, 255));
        labelTituloRegistro.setText("Aprendizaje del Dia");
        labelTituloRegistro.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jPanel1.setBackground(new java.awt.Color(28, 34, 45));
        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(50, 70, 75), new java.awt.Color(50, 70, 75), new java.awt.Color(50, 70, 75), new java.awt.Color(50, 70, 75)));

        areaDescripcion.setColumns(20);
        areaDescripcion.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        areaDescripcion.setRows(5);
        jScrollPane1.setViewportView(areaDescripcion);

        cajaFecha.setBackground(new java.awt.Color(255, 255, 255));
        cajaFecha.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        cajaFecha.setForeground(new java.awt.Color(0, 0, 0));

        cajaTitulo.setBackground(new java.awt.Color(255, 255, 255));
        cajaTitulo.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        cajaTitulo.setForeground(new java.awt.Color(0, 0, 0));

        labelFecha.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        labelFecha.setForeground(new java.awt.Color(255, 255, 255));
        labelFecha.setText("Fecha:");
        labelFecha.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        labelFecha1.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        labelFecha1.setForeground(new java.awt.Color(255, 255, 255));
        labelFecha1.setText("Titulo:");
        labelFecha1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        labelFecha2.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        labelFecha2.setForeground(new java.awt.Color(255, 255, 255));
        labelFecha2.setText("Descripción:");
        labelFecha2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        labelStart5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/estrella_vacia.png"))); // NOI18N
        labelStart5.setPreferredSize(new java.awt.Dimension(32, 32));

        labelStart4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/estrella_vacia.png"))); // NOI18N
        labelStart4.setPreferredSize(new java.awt.Dimension(32, 32));

        labelStart3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/estrella_vacia.png"))); // NOI18N
        labelStart3.setPreferredSize(new java.awt.Dimension(32, 32));

        labelStart2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/estrella_vacia.png"))); // NOI18N
        labelStart2.setPreferredSize(new java.awt.Dimension(32, 32));

        labelStart1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/estrella_vacia.png"))); // NOI18N
        labelStart1.setPreferredSize(new java.awt.Dimension(32, 32));

        labelFecha3.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        labelFecha3.setForeground(new java.awt.Color(255, 255, 255));
        labelFecha3.setText("Satisfacción: ");
        labelFecha3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        botonRegistrar.setBackground(new java.awt.Color(38, 76, 125));
        botonRegistrar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        botonRegistrar.setForeground(new java.awt.Color(255, 255, 255));
        botonRegistrar.setText("Registrar");
        botonRegistrar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botonRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonRegistrarActionPerformed(evt);
            }
        });

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

        labelFecha4.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        labelFecha4.setForeground(new java.awt.Color(255, 255, 255));
        labelFecha4.setText("Categoria:");
        labelFecha4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        comboCategoria.setBackground(new java.awt.Color(255, 255, 255));
        comboCategoria.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        comboCategoria.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        botonCancelar.setBackground(new java.awt.Color(34, 147, 248));
        botonCancelar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        botonCancelar.setForeground(new java.awt.Color(255, 255, 255));
        botonCancelar.setText("Cancelar");
        botonCancelar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botonCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(labelFecha4)
                                .addGap(20, 20, 20))
                            .addComponent(labelFecha2, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(13, 13, 13)
                                .addComponent(comboCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(90, 90, 90)
                                .addComponent(labelImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 454, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(botonRegistrar, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(labelFecha3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelStart1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(labelStart2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(labelStart3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(labelStart4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(labelStart5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(botonCerrar, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(botonCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelFecha)
                            .addComponent(labelFecha1))
                        .addGap(64, 64, 64)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cajaFecha, javax.swing.GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE)
                            .addComponent(cajaTitulo))))
                .addContainerGap(113, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cajaFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelFecha))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cajaTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelFecha1))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelFecha4)
                            .addComponent(comboCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(labelImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(labelFecha2)
                        .addGap(0, 124, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelStart2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelStart3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(labelFecha3)
                                    .addComponent(labelStart1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addComponent(labelStart4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                        .addComponent(labelStart5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botonRegistrar, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonCerrar, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19))
        );

        //Obteniendo la fecha actual
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String fechaActual = LocalDate.now().format(formatoFecha);
        cajaFecha.setText(fechaActual);

        //VALIDAR FORMATO SI SE MODIFICA
        cajaFecha.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent e) {
                try {
                    // Intenta parsear la fecha
                    LocalDate.parse(cajaFecha.getText(), formatoFecha);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Formato de fecha inválido. Usa Dia-Mes-Año");
                    cajaFecha.setText(LocalDate.now().format(formatoFecha)); // Restaurar fecha actual
                }
            }
        });

        javax.swing.GroupLayout panelRegistroLayout = new javax.swing.GroupLayout(panelRegistro);
        panelRegistro.setLayout(panelRegistroLayout);
        panelRegistroLayout.setHorizontalGroup(
            panelRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRegistroLayout.createSequentialGroup()
                .addContainerGap(60, Short.MAX_VALUE)
                .addGroup(panelRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRegistroLayout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(61, 61, 61))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRegistroLayout.createSequentialGroup()
                        .addComponent(labelTituloRegistro)
                        .addGap(308, 308, 308))))
        );
        panelRegistroLayout.setVerticalGroup(
            panelRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRegistroLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(labelTituloRegistro)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelRegistro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelRegistro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cargarCategoriasEnCombo() {
    comboCategoria.removeAllItems();
    comboCategoria.addItem(new CategoriaItem(0, "Selecciona categoría"));

    for (CategoriaItem item : CategoriaServicio.obtenerCategorias()) {
        comboCategoria.addItem(item);
    }
    comboCategoria.addItem(new CategoriaItem(-1, "+ Agregar"));
        comboCategoria.setSelectedIndex(0);
}
    private void configurarEventosComboCategoria() {
    comboCategoria.addActionListener(e -> {
        CategoriaItem seleccionado = (CategoriaItem) comboCategoria.getSelectedItem();

        if (seleccionado != null && seleccionado.getId() == -1) {
            // El usuario seleccionó "Agregar nueva categoría"
            new VentanaCategoria(this).setVisible(true);
            this.setVisible(false);
        }
    });
}


    private void botonRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonRegistrarActionPerformed
       DatosValidos datos = validarCampos();
    
    if (modoEdicion) {
        if (datos == null) return;
        String sql = "UPDATE registros SET fecha=?, id_categoria=?, titulo=?, descripcion=?, satisfaccion=? WHERE id_registro=?";

        try (Connection cn = Conexion.conectar();
             PreparedStatement pst = cn.prepareStatement(sql)) {

            pst.setString (1, datos.fecha.toString()); // ← usar String ISO "YYYY-MM-DD"
            pst.setInt    (2, datos.idCategoria);
            pst.setString (3, datos.titulo);
            pst.setString (4, datos.descripcion);
            pst.setInt    (5, datos.satisfaccion);
            pst.setInt    (6, idRegistroBD);

            if (pst.executeUpdate() > 0) {
                registroActualizado = true;
                dispose(); // cierra la ventana
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar: " + ex.getMessage());
        }

    } else {
        // MODO REGISTRO NUEVO
        if (datos == null) return;

        String sql = "INSERT INTO registros "
                   + "(fecha, id_categoria, titulo, descripcion, satisfaccion) "
                   + "VALUES (?, ?, ?, ?, ?)";

        try (Connection cn = Conexion.conectar();
             PreparedStatement pst = cn.prepareStatement(sql)) {

            pst.setString (1, datos.fecha.toString()); // ← igual que arriba
            pst.setInt    (2, datos.idCategoria);
            pst.setString (3, datos.titulo);
            pst.setString (4, datos.descripcion);
            pst.setInt    (5, datos.satisfaccion);

            int filas = pst.executeUpdate();
            if (filas > 0) {
                JOptionPane.showMessageDialog(this, "Registro guardado.");
                limpiarCampos();
                componenteEstrellas.setValor(0);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al guardar:\n" + ex.getMessage(),
                    "Base de datos", JOptionPane.ERROR_MESSAGE);
        }
    }

      
    }//GEN-LAST:event_botonRegistrarActionPerformed

    private DatosValidos validarCampos() {
        String titulo = cajaTitulo.getText().trim();
        String descripcion = areaDescripcion.getText().trim();
        int satisfaccion = componenteEstrellas.getValor();

        // Validar fecha
        LocalDate fecha;
        try {
            fecha = LocalDate.parse(cajaFecha.getText().trim(), formatoFecha);
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Fecha inválida. Usa el formato Día-Mes-Año (dd-MM-yyyy).");
            cajaFecha.requestFocus();
            return null;
        }

        // Validar categoría
        // Validar categoría
        CategoriaItem item = (CategoriaItem) comboCategoria.getSelectedItem();
        if (item == null || item.getId() <= 0) {
        JOptionPane.showMessageDialog(this, "Selecciona una categoría válida.");
        comboCategoria.requestFocus();
        return null;
        }


        // Validar título
        if (titulo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El título no puede estar vacío.");
            cajaTitulo.requestFocus();
            return null;
        }

        // Validar descripción
        if (descripcion.isEmpty()) {
            JOptionPane.showMessageDialog(this, "La descripción no puede estar vacía.");
            areaDescripcion.requestFocus();
            return null;
        }

        // Validar satisfacción
        if (satisfaccion < 1 || satisfaccion > 5) {
            JOptionPane.showMessageDialog(this, "Selecciona una satisfacción de 1 a 5 haciendo clic en las estrellas.");
            return null;
        }

       return new DatosValidos(fecha, titulo, descripcion, item.getId(), satisfaccion);

    }

    private void limpiarCampos() {
        cajaFecha.setText(LocalDate.now()
                .format(formatoFecha));
        cajaTitulo.setText("");
        areaDescripcion.setText("");           // valor medio
        comboCategoria.setSelectedIndex(0);    // primer ítem
    }


    private void botonCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCerrarActionPerformed
        // TODO add your handling code here:
        dispose();
        frame.setVisible(true);
    }//GEN-LAST:event_botonCerrarActionPerformed

    private void botonCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCancelarActionPerformed
        dispose();
    }//GEN-LAST:event_botonCancelarActionPerformed

    public static void main(String args[]) {
        System.out.println();
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VentanaRegistro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VentanaRegistro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VentanaRegistro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VentanaRegistro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                 new VentanaRegistro().setVisible(true);

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea areaDescripcion;
    private javax.swing.JButton botonCancelar;
    private javax.swing.JButton botonCerrar;
    private javax.swing.JButton botonRegistrar;
    private javax.swing.JTextField cajaFecha;
    private javax.swing.JTextField cajaTitulo;
    private javax.swing.JComboBox<CategoriaItem> comboCategoria;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelFecha;
    private javax.swing.JLabel labelFecha1;
    private javax.swing.JLabel labelFecha2;
    private javax.swing.JLabel labelFecha3;
    private javax.swing.JLabel labelFecha4;
    private javax.swing.JLabel labelImagen;
    private javax.swing.JLabel labelStart1;
    private javax.swing.JLabel labelStart2;
    private javax.swing.JLabel labelStart3;
    private javax.swing.JLabel labelStart4;
    private javax.swing.JLabel labelStart5;
    private javax.swing.JLabel labelTituloRegistro;
    private javax.swing.JPanel panelRegistro;
    // End of variables declaration//GEN-END:variables
}

class DatosValidos {
    LocalDate fecha;
    String titulo;
    String descripcion;
    int idCategoria;
    int satisfaccion;

    DatosValidos(LocalDate fecha, String titulo, String descripcion, int idCategoria, int satisfaccion) {
        this.fecha = fecha;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.idCategoria = idCategoria;
        this.satisfaccion = satisfaccion;
    }
}
