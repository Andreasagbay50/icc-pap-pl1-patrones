package vista;

import models.Pedido;
import servicio.ServicioPedidos;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class VentanaPedidos extends JFrame {
    private ServicioPedidos servicio;
    private JComboBox<String> comboProductos;
    private JComboBox<String> comboIngredientes;
    private JTable tablaPedidos;
    private DefaultTableModel modeloTabla;
    private JTextArea areaDetalle;
    private Pedido pedidoSeleccionado;

    public VentanaPedidos() {
        servicio = new ServicioPedidos();
        setTitle("PedidoLab - Prototype & Decorator");
        setSize(850, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panelControles = new JPanel();
        panelControles.setLayout(new GridLayout(7, 1, 10, 10));
        panelControles.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        comboProductos = new JComboBox<>(new String[]{"Café", "Ensalada sencilla", "Pizza sencilla"});
        JButton btnClonar = new JButton("Clonar producto");

        comboIngredientes = new JComboBox<>(new String[]{"Azúcar", "Crema", "Tomate", "Pollo", "Queso", "Pepperoni"});
        JButton btnAgregarIngrediente = new JButton("Agregar ingrediente");
        JButton btnReiniciar = new JButton("Reiniciar pedidos");

        panelControles.add(new JLabel("1. Seleccionar producto base:"));
        panelControles.add(comboProductos);
        panelControles.add(btnClonar);
        panelControles.add(new JLabel("2. Agregar ingredientes:"));
        panelControles.add(comboIngredientes);
        panelControles.add(btnAgregarIngrediente);
        panelControles.add(btnReiniciar);

        add(panelControles, BorderLayout.WEST);

        JPanel panelDerecho = new JPanel(new GridLayout(2, 1, 10, 10));
        panelDerecho.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        modeloTabla = new DefaultTableModel(new String[]{"ID", "Producto", "Composición", "Total"}, 0);
        tablaPedidos = new JTable(modeloTabla);
        panelDerecho.add(new JScrollPane(tablaPedidos));

        areaDetalle = new JTextArea();
        areaDetalle.setEditable(false);
        panelDerecho.add(new JScrollPane(areaDetalle));

        add(panelDerecho, BorderLayout.CENTER);

        btnClonar.addActionListener(e -> {
            String seleccionado = (String) comboProductos.getSelectedItem();
            String clave = seleccionado.equalsIgnoreCase("Café") ? "cafe" : 
                           seleccionado.equalsIgnoreCase("Ensalada sencilla") ? "ensalada" : "pizza";
            pedidoSeleccionado = servicio.crearPedidoBase(clave);
            actualizarTabla();
        });

        btnAgregarIngrediente.addActionListener(e -> {
            if (pedidoSeleccionado == null) {
                JOptionPane.showMessageDialog(this, "Selecciona un pedido de la tabla primero.");
                return;
            }
            try {
                String ingrediente = (String) comboIngredientes.getSelectedItem();
                pedidoSeleccionado = servicio.agregarIngrediente(pedidoSeleccionado, ingrediente);
                actualizarTabla();
                mostrarDetalle(pedidoSeleccionado);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error de compatibilidad", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnReiniciar.addActionListener(e -> {
            servicio.reiniciarPedidos();
            pedidoSeleccionado = null;
            actualizarTabla();
            areaDetalle.setText("");
        });

        tablaPedidos.getSelectionModel().addListSelectionListener(e -> {
            int fila = tablaPedidos.getSelectedRow();
            if (fila >= 0 && fila < modeloTabla.getRowCount()) {
                String id = (String) modeloTabla.getValueAt(fila, 0);
                pedidoSeleccionado = servicio.getPedidosCreados().stream()
                        .filter(p -> p.getId().equals(id))
                        .findFirst().orElse(null);
                if (pedidoSeleccionado != null) {
                    mostrarDetalle(pedidoSeleccionado);
                }
            }
        });
    }

    private void actualizarTabla() {
        modeloTabla.setRowCount(0);
        for (Pedido p : servicio.getPedidosCreados()) {
            modeloTabla.addRow(new Object[]{p.getId(), p.getTipo(), p.getDescripcion(), "$" + p.getPrecio()});
        }
    }

    private void mostrarDetalle(Pedido p) {
        areaDetalle.setText("=== DETALLE DEL PEDIDO SELECCIONADO ===\n" +
                "ID: " + p.getId() + "\n" +
                "Tipo: " + p.getTipo() + "\n" +
                "Composición / Descripción: " + p.getDescripcion() + "\n" +
                "Total a Pagar: $" + p.getPrecio());L
    }
}