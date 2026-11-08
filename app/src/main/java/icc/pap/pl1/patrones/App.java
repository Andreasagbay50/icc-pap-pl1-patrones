package icc.pap.pl1.patrones;


import javax.swing.*;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new vista.VentanaPedidos().setVisible(true);
        });
    }


}