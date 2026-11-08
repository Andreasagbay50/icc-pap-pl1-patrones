package icc.pap.pl1.patrones;

import javax.swing.SwingUtilities;

import icc.pap.pl1.patrones.vista.VentanaPedidos;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VentanaPedidos().setVisible(true));
    }
}