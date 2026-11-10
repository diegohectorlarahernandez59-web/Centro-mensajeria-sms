
package Controller;

import View.ViewChat; // O la vista que quieras mostrar primero (ej. ViewLogin)
import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.UIManager;

public class Main {

    public static void main(String[] args) {
        // 1. Inicializar FlatLaf antes de crear cualquier ventana
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception ex) {
            System.err.println("No se pudo inicializar FlatLaf");
        }

        // 2. Lanzar la interfaz gráfica en el hilo de eventos de Swing
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ViewChat().setVisible(true); // O new ViewLogin().setVisible(true);
            }
        });
    }
}
    

