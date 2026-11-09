package icc.pap.pl1.patrones.vista;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.swing.Box;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import icc.pap.pl1.patrones.models.Pedido;
import icc.pap.pl1.patrones.models.TipoProducto;
import icc.pap.pl1.patrones.prototype.Cafe;
import icc.pap.pl1.patrones.prototype.Ensalada;
import icc.pap.pl1.patrones.prototype.Pizza;
import icc.pap.pl1.patrones.servicio.ServicioPedido;

public class VentanaPedidos extends JFrame {

    // ---------- Colores ----------
    private static final Color FONDO = new Color(0xFB, 0xF4, 0xF2);
    private static final Color VINO = new Color(0x52, 0x2D, 0x37);
    private static final Color ROSA = new Color(0xA8, 0x3E, 0x5F);
    private static final Color ROSA_CLARO = new Color(0xF8, 0xE4, 0xE8);
    private static final Color TEXTO = new Color(0x3D, 0x26, 0x2C);
    private static final Color TEXTO_SUAVE = new Color(0x8E, 0x7A, 0x7F);
    private static final Color BORDE = new Color(0xEC, 0xE2, 0xE4);
    private static final Color ROSA_PALIDO = new Color(0xD9, 0xA9, 0xB6);
    private static final Color LINEA_OSCURA = new Color(0x6B, 0x45, 0x4E);

    // ---------- Lógica ----------
    private final ServicioPedido servicio = new ServicioPedido();
    private final DefaultListModel<Pedido> modeloPedidos = new DefaultListModel<>();
    private final JList<Pedido> listaPedidos = new JList<>(modeloPedidos);
    private final Map<String, List<String>> ingredientesPorPedido = new HashMap<>();
    private TipoProducto tipoElegido = TipoProducto.CAFE;

    // ---------- Componentes ----------
    private final Map<TipoProducto, Redondeado> tarjetas = new EnumMap<>(TipoProducto.class);
    private final Map<TipoProducto, JLabel> insignias = new EnumMap<>(TipoProducto.class);
    private final JComboBox<String> comboIngredientes = new JComboBox<>(
            new String[] { "Azúcar", "Crema", "Tomate", "Pollo", "Queso", "Pepperoni" });

    private final JLabel lblId = new JLabel("—");
    private final JLabel lblProducto = new JLabel("—");
    private final JLabel lblDescripcion = new JLabel();
    private final JLabel lblIngredientes = new JLabel();
    private final JLabel lblTotal = new JLabel("$0.00");
    private final JLabel lblTotalGeneral = new JLabel("$0.00");

    public VentanaPedidos() {
        super("PedidoLab");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1100, 760));
        setSize(1250, 900);
        setLocationRelativeTo(null);
        setContentPane(crearContenido());
        elegirTipo(TipoProducto.CAFE);
        mostrarPedido(null);
    }

    
    private JPanel crearContenido() {
        JPanel raiz = new JPanel(new BorderLayout(0, 20));
        raiz.setBackground(FONDO);
        raiz.setBorder(new EmptyBorder(24, 40, 28, 40));

        JLabel titulo = new JLabel("<html><span style='color:#3D262C'>Crea algo </span>"
                + "<i style='color:#A83E5F'>delicioso</i></html>");
        titulo.setFont(titulo(46));
        JLabel subtitulo = new JLabel("Elige tu favorito, añade un toque especial y prepara un pedido tan único como tú.");
        subtitulo.setFont(texto(Font.PLAIN, 15));
        subtitulo.setForeground(TEXTO_SUAVE);
        JPanel encabezado = new JPanel(new BorderLayout(0, 4));
        encabezado.setOpaque(false);
        encabezado.add(titulo, BorderLayout.NORTH);
        encabezado.add(subtitulo, BorderLayout.CENTER);

        JPanel centro = new JPanel(new BorderLayout(28, 0));
        centro.setOpaque(false);
        centro.add(crearPanelIzquierdo(), BorderLayout.CENTER);
        centro.add(crearPanelPedido(), BorderLayout.EAST);

        raiz.add(encabezado, BorderLayout.NORTH);
        raiz.add(centro, BorderLayout.CENTER);
        return raiz;
    }

    private JPanel crearPanelIzquierdo() {
        Redondeado p = new Redondeado(40, Color.WHITE, BORDE);
        p.setLayout(new GridBagLayout());
        p.setBorder(new EmptyBorder(26, 36, 26, 36));

        // 01 - productos
        JPanel productos = new JPanel(new GridLayout(1, 3, 16, 0));
        productos.setOpaque(false);
        for (TipoProducto t : TipoProducto.values()) {
            productos.add(crearTarjeta(t));
        }

        JButton btnCrear = boton("Crear pedido", true);
        btnCrear.addActionListener(e -> crearPedido());
        JPanel filaCrear = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        filaCrear.setOpaque(false);
        filaCrear.add(btnCrear);

        // 02 - lista de pedidos (grande, ocupa todo el espacio libre)
        listaPedidos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaPedidos.setFont(texto(Font.PLAIN, 16));
        listaPedidos.setFixedCellHeight(40);
        listaPedidos.setSelectionBackground(ROSA_CLARO);
        listaPedidos.setSelectionForeground(TEXTO);
        listaPedidos.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> lista, Object valor, int indice,
                    boolean seleccionado, boolean foco) {
                super.getListCellRendererComponent(lista, valor, indice, seleccionado, foco);
                Pedido p = (Pedido) valor;
                setText(p.id() + "   ·   " + p.descripcion() + "   ·   " + dinero(p.precio()));
                setBorder(new EmptyBorder(8, 12, 8, 12));
                return this;
            }
        });
        listaPedidos.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                mostrarPedido(listaPedidos.getSelectedValue());
            }
        });
        JScrollPane scroll = new JScrollPane(listaPedidos);
        scroll.setBorder(new LineBorder(BORDE));
        scroll.setPreferredSize(new Dimension(0, 260));
        scroll.setMinimumSize(new Dimension(0, 160));

        // 03 - ingredientes
        JButton btnAgregar = boton("Añadir", true);
        btnAgregar.addActionListener(e -> agregarIngrediente());
        comboIngredientes.setFont(texto(Font.PLAIN, 14));
        JPanel filaIngrediente = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        filaIngrediente.setOpaque(false);
        filaIngrediente.add(comboIngredientes);
        filaIngrediente.add(btnAgregar);

        JButton btnReiniciar = boton("Reiniciar pedidos", false);
        btnReiniciar.addActionListener(e -> reiniciarPedidos());
        JPanel filaReiniciar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        filaReiniciar.setOpaque(false);
        filaReiniciar.add(btnReiniciar);

        fila(p, encabezadoSeccion("01", "Elige tu favorito", "Selecciona la base de tu pedido"), 0, 0);
        fila(p, productos, 1, 18);
        fila(p, filaCrear, 2, 14);
        fila(p, linea(BORDE), 3, 18);
        fila(p, encabezadoSeccion("02", "Tus pedidos", "Selecciona uno para ver su detalle"), 4, 18);
        filaExpandible(p, scroll, 5, 12);
        fila(p, encabezadoSeccion("03", "Hazlo a tu manera", "Elige un ingrediente para el pedido seleccionado"), 6, 18);
        fila(p, filaIngrediente, 7, 12);
        fila(p, filaReiniciar, 8, 12);
        return p;
    }

    private JPanel crearPanelPedido() {
        Redondeado p = new Redondeado(40, VINO, null);
        p.setLayout(new GridBagLayout());
        p.setBorder(new EmptyBorder(32, 34, 28, 34));
        p.setPreferredSize(new Dimension(340, 0));

        lblId.setFont(titulo(34));
        lblId.setForeground(Color.WHITE);
        lblProducto.setFont(titulo(20));
        lblProducto.setForeground(Color.WHITE);
        lblDescripcion.setFont(texto(Font.PLAIN, 14));
        lblDescripcion.setForeground(Color.WHITE);
        lblIngredientes.setFont(texto(Font.PLAIN, 14));
        lblIngredientes.setForeground(Color.WHITE);

        JLabel textoTotal = new JLabel("Total");
        textoTotal.setFont(texto(Font.PLAIN, 15));
        textoTotal.setForeground(Color.WHITE);
        lblTotal.setFont(titulo(32));
        lblTotal.setForeground(Color.WHITE);
        JPanel filaTotal = new JPanel(new BorderLayout());
        filaTotal.setOpaque(false);
        filaTotal.add(textoTotal, BorderLayout.WEST);
        filaTotal.add(lblTotal, BorderLayout.EAST);

        JLabel textoGeneral = new JLabel("Total de todos los pedidos");
        textoGeneral.setFont(texto(Font.BOLD, 14));
        textoGeneral.setForeground(ROSA_PALIDO);
        lblTotalGeneral.setFont(titulo(26));
        lblTotalGeneral.setForeground(Color.WHITE);
        JPanel filaGeneral = new JPanel(new BorderLayout());
        filaGeneral.setOpaque(false);
        filaGeneral.add(textoGeneral, BorderLayout.WEST);
        filaGeneral.add(lblTotalGeneral, BorderLayout.EAST);

        JLabel pie = new JLabel("Preparado especialmente para ti");
        pie.setFont(new Font("Georgia", Font.ITALIC, 14));
        pie.setForeground(ROSA_PALIDO);

        fila(p, etiquetaPequena("PEDIDO SELECCIONADO"), 0, 0);
        fila(p, lblId, 1, 8);
        fila(p, linea(LINEA_OSCURA), 2, 20);
        fila(p, etiquetaPequena("PRODUCTO"), 3, 18);
        fila(p, lblProducto, 4, 6);
        fila(p, etiquetaPequena("DESCRIPCIÓN"), 5, 18);
        fila(p, lblDescripcion, 6, 6);
        fila(p, linea(LINEA_OSCURA), 7, 18);
        fila(p, etiquetaPequena("INGREDIENTES"), 8, 18);
        fila(p, lblIngredientes, 9, 6);
        fila(p, linea(LINEA_OSCURA), 10, 18);
        fila(p, filaTotal, 11, 16);
        fila(p, linea(LINEA_OSCURA), 12, 18);
        fila(p, filaGeneral, 13, 16);
        relleno(p, 14);
        fila(p, pie, 15, 0);
        return p;
    }

    private Redondeado crearTarjeta(TipoProducto tipo) {
        Redondeado t = new Redondeado(28, Color.WHITE, BORDE);
        t.setLayout(new BorderLayout(0, 8));
        t.setBorder(new EmptyBorder(16, 18, 16, 18));
        t.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        t.setPreferredSize(new Dimension(230, 140));

        JLabel nombre = new JLabel(nombreTipo(tipo));
        nombre.setFont(titulo(17));
        nombre.setForeground(TEXTO);

        JLabel insignia = new JLabel(" ELEGIDO ");
        insignia.setOpaque(true);
        insignia.setBackground(ROSA);
        insignia.setForeground(Color.WHITE);
        insignia.setFont(texto(Font.BOLD, 10));
        insignia.setVisible(false);
        insignias.put(tipo, insignia);

        JPanel arriba = new JPanel(new BorderLayout());
        arriba.setOpaque(false);
        arriba.add(nombre, BorderLayout.WEST);
        arriba.add(insignia, BorderLayout.EAST);

        JLabel descripcion = new JLabel("<html>" + descripcionTarjeta(tipo) + "</html>");
        descripcion.setFont(texto(Font.PLAIN, 13));
        descripcion.setForeground(TEXTO_SUAVE);

        JLabel precio = new JLabel(dinero(precioBase(tipo)));
        precio.setFont(texto(Font.BOLD, 17));
        precio.setForeground(ROSA);

        t.add(arriba, BorderLayout.NORTH);
        t.add(descripcion, BorderLayout.CENTER);
        t.add(precio, BorderLayout.SOUTH);

        t.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                elegirTipo(tipo);
            }
        });

        tarjetas.put(tipo, t);
        return t;
    }

    // =====================================================
    // Operaciones del enunciado
    // =====================================================

    // 1. Clonar producto
    private void crearPedido() {
        Pedido nuevo = servicio.crearPedido(tipoElegido);
        if (nuevo == null) {
            error("No existe ese producto en el registro de prototipos.");
            return;
        }
        modeloPedidos.addElement(nuevo);
        ingredientesPorPedido.put(nuevo.id(), new ArrayList<>());
        actualizarTotalGeneral();
        listaPedidos.setSelectedIndex(modeloPedidos.size() - 1);
    }

    // 3 y 5. Agregar ingrediente
    private void agregarIngrediente() {
        int indice = listaPedidos.getSelectedIndex();
        Pedido actual = listaPedidos.getSelectedValue();
        String ingrediente = (String) comboIngredientes.getSelectedItem();

        try {
            Pedido decorado = servicio.personalizarPedido(actual, ingrediente);
            modeloPedidos.set(indice, decorado);
            ingredientesPorPedido.get(decorado.id()).add(ingrediente);
            actualizarTotalGeneral();
            mostrarPedido(decorado);
        } catch (Exception ex) {
            error(ex.getMessage());
        }
    }

    // 6. Reiniciar pedidos
    private void reiniciarPedidos() {
        servicio.reiniciarPedidos();
        modeloPedidos.clear();
        ingredientesPorPedido.clear();
        actualizarTotalGeneral();
        mostrarPedido(null);
    }

    // 2. Seleccionar pedido
    private void mostrarPedido(Pedido p) {
        if (p == null) {
            lblId.setText("—");
            lblProducto.setText("—");
            lblDescripcion.setText(html("Sin pedido seleccionado"));
            lblIngredientes.setText(html("—"));
            lblTotal.setText("$0.00");
            return;
        }
        List<String> ingredientes = ingredientesPorPedido.getOrDefault(p.id(), new ArrayList<>());
        String detalle = ingredientes.isEmpty() ? "Sin ingredientes"
                : "• " + String.join("<br>• ", ingredientes);

        lblId.setText(p.id());
        lblProducto.setText(nombreTipo(p.tipo()));
        lblDescripcion.setText(html(p.descripcion()));
        lblIngredientes.setText(html(detalle));
        lblTotal.setText(dinero(p.precio()));
    }

    private void actualizarTotalGeneral() {
        BigDecimal suma = BigDecimal.ZERO;
        for (int i = 0; i < modeloPedidos.size(); i++) {
            suma = suma.add(modeloPedidos.get(i).precio());
        }
        lblTotalGeneral.setText(dinero(suma));
    }

    private void elegirTipo(TipoProducto elegido) {
        tipoElegido = elegido;
        for (Map.Entry<TipoProducto, Redondeado> e : tarjetas.entrySet()) {
            boolean activo = e.getKey() == elegido;
            e.getValue().setBorde(activo ? ROSA : BORDE, activo ? 2 : 1);
            insignias.get(e.getKey()).setVisible(activo);
        }
    }

    private void error(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "PedidoLab", JOptionPane.WARNING_MESSAGE);
    }

    // =====================================================
    // Utilidades
    // =====================================================

    private static String nombreTipo(TipoProducto t) {
        switch (t) {
            case CAFE: return "Café";
            case ENSALADA: return "Ensalada";
            default: return "Pizza";
        }
    }

    private static String descripcionTarjeta(TipoProducto t) {
        switch (t) {
            case CAFE: return "Espresso suave y leche cremosa";
            case ENSALADA: return "Hojas frescas y vinagreta";
            default: return "Masa artesanal y salsa de tomate";
        }
    }

    // Solo se lee el precio; no se registra ningún pedido
    private static BigDecimal precioBase(TipoProducto t) {
        switch (t) {
            case CAFE: return new Cafe("DEMO").precio();
            case ENSALADA: return new Ensalada("DEMO").precio();
            default: return new Pizza("DEMO").precio();
        }
    }

    // Formato en dólares: $3.50
    private static String dinero(BigDecimal valor) {
        return String.format(Locale.US, "$%.2f", valor);
    }

    private static String html(String texto) {
        return "<html><div style='width:250px'>" + texto + "</div></html>";
    }

    private static Font titulo(int tamano) {
        return new Font("Georgia", Font.BOLD, tamano);
    }

    private static Font texto(int estilo, int tamano) {
        return new Font("Segoe UI", estilo, tamano);
    }

    private JButton boton(String texto, boolean principal) {
        JButton b = new JButton(texto);
        b.setFont(texto(Font.BOLD, 13));
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setOpaque(true);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setBackground(principal ? ROSA : ROSA_CLARO);
        b.setForeground(principal ? Color.WHITE : ROSA);
        b.setBorder(new EmptyBorder(10, 22, 10, 22));
        return b;
    }

    private JLabel etiquetaPequena(String texto) {
        JLabel l = new JLabel(texto);
        l.setFont(texto(Font.BOLD, 11));
        l.setForeground(ROSA_PALIDO);
        return l;
    }

    private JPanel linea(Color color) {
        JPanel l = new JPanel();
        l.setBackground(color);
        l.setPreferredSize(new Dimension(0, 1));
        return l;
    }

    private JPanel encabezadoSeccion(String numero, String titulo, String subtitulo) {
        Redondeado badge = new Redondeado(46, ROSA_CLARO, null);
        badge.setLayout(new GridBagLayout());
        badge.setPreferredSize(new Dimension(46, 46));
        JLabel lblNumero = new JLabel(numero);
        lblNumero.setFont(titulo(13));
        lblNumero.setForeground(ROSA);
        badge.add(lblNumero);

        JPanel contenedorBadge = new JPanel(new GridBagLayout());
        contenedorBadge.setOpaque(false);
        contenedorBadge.add(badge);

        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(titulo(26));
        lblTitulo.setForeground(TEXTO);
        JLabel lblSubtitulo = new JLabel(subtitulo);
        lblSubtitulo.setFont(texto(Font.PLAIN, 13));
        lblSubtitulo.setForeground(TEXTO_SUAVE);

        JPanel textos = new JPanel(new GridLayout(2, 1, 0, 2));
        textos.setOpaque(false);
        textos.add(lblTitulo);
        textos.add(lblSubtitulo);

        JPanel p = new JPanel(new BorderLayout(16, 0));
        p.setOpaque(false);
        p.add(contenedorBadge, BorderLayout.WEST);
        p.add(textos, BorderLayout.CENTER);
        return p;
    }

    private void fila(JPanel padre, Component c, int y, int espacioArriba) {
        GridBagConstraints g = new GridBagConstraints();
        g.gridx = 0;
        g.gridy = y;
        g.weightx = 1;
        g.fill = GridBagConstraints.HORIZONTAL;
        g.anchor = GridBagConstraints.NORTHWEST;
        g.insets = new Insets(espacioArriba, 0, 0, 0);
        padre.add(c, g);
    }

    // Fila que se estira para ocupar todo el espacio vertical libre
    private void filaExpandible(JPanel padre, Component c, int y, int espacioArriba) {
        GridBagConstraints g = new GridBagConstraints();
        g.gridx = 0;
        g.gridy = y;
        g.weightx = 1;
        g.weighty = 1;
        g.fill = GridBagConstraints.BOTH;
        g.anchor = GridBagConstraints.NORTHWEST;
        g.insets = new Insets(espacioArriba, 0, 0, 0);
        padre.add(c, g);
    }

    private void relleno(JPanel padre, int y) {
        GridBagConstraints g = new GridBagConstraints();
        g.gridx = 0;
        g.gridy = y;
        g.weightx = 1;
        g.weighty = 1;
        padre.add(Box.createGlue(), g);
    }

    // Panel con esquinas redondeadas
    private static class Redondeado extends JPanel {
        private final int radio;
        private final Color fondo;
        private Color borde;
        private int grosor = 1;

        Redondeado(int radio, Color fondo, Color borde) {
            this.radio = radio;
            this.fondo = fondo;
            this.borde = borde;
            setOpaque(false);
        }

        void setBorde(Color borde, int grosor) {
            this.borde = borde;
            this.grosor = grosor;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(fondo);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radio, radio);
            if (borde != null) {
                g2.setColor(borde);
                g2.setStroke(new BasicStroke(grosor));
                int o = grosor / 2;
                g2.drawRoundRect(o, o, getWidth() - grosor, getHeight() - grosor, radio, radio);
            }
            g2.dispose();
            super.paintComponent(g);
        }
    }
}