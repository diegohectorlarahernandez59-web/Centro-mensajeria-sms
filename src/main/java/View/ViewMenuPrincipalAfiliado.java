package View; // <-- CAMBIA esto por el paquete real de tu proyecto

import java.awt.*;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.*;
import javax.swing.border.*;

/**
 * Menú principal – Afiliado.
 * Se usa así desde otra vista (por ejemplo después del login):
 *
 *   ViewMenuPrincipalAfiliado menu = new ViewMenuPrincipalAfiliado();
 *   menu.setAfiliado("Jassiel Hernández", 2000.00, "+52 55 1234 5678");
 *   menu.setVisible(true);
 */
public class ViewMenuPrincipalAfiliado extends JFrame {

    private static final Color AZUL = new Color(0x2F6DB5);
    private static final Color ROJO = new Color(0xB03030);

    private final JLabel lblSaldo = new JLabel("$0.00");
    private final JLabel lblTelefono = new JLabel("-");
    private final JLabel lblEstado = new JLabel("Terminal apagada", SwingConstants.CENTER);
    private final JLabel lblBienvenido = new JLabel("Bienvenido, (Nombre Apellido)");
    private final JButton btnTerminal = new JButton("Encender terminal");
    private final JButton btnCerrarSesion = new JButton("Cerrar sesión");

    private boolean terminalEncendida = false;
    private double saldo = 0;

    public ViewMenuPrincipalAfiliado() {
        super("Menú principal – Afiliado");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(560, 380);
        setLocationRelativeTo(null);

        setJMenuBar(crearBarraMenu());

        JPanel contenido = new JPanel(new GridBagLayout());
        contenido.setBackground(Color.WHITE);
        contenido.setBorder(new EmptyBorder(15, 15, 15, 15));
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(0, 0, 12, 12);
        c.weighty = 0;

        // Tarjeta saldo
        c.gridx = 0; c.gridy = 0; c.weightx = 0.5;
        contenido.add(crearTarjetaSaldo(), c);

        // Tarjeta teléfono activo
        c.gridx = 1; c.insets = new Insets(0, 0, 12, 0);
        contenido.add(crearTarjetaTelefono(), c);

        // Panel bienvenida
        c.gridx = 0; c.gridy = 1; c.gridwidth = 2; c.weightx = 1;
        c.insets = new Insets(0, 0, 0, 0);
        contenido.add(crearPanelBienvenida(), c);

        // Relleno para que no se estire hacia abajo
        c.gridy = 2; c.weighty = 1;
        contenido.add(Box.createVerticalGlue(), c);

        setContentPane(contenido);
    }

    // ---------- Construcción de la interfaz ----------

    private JMenuBar crearBarraMenu() {
        JMenuBar barra = new JMenuBar();
        barra.add(crearItem("Ajustes", e -> abrirAjustes()));
        barra.add(crearItem("Recargar saldo", e -> abrirRecargarSaldo()));
        barra.add(crearItem("Mis teléfonos", e -> abrirMisTelefonos()));
        barra.add(crearItem("Tarifas", e -> abrirTarifas()));
        barra.add(crearItem("Chat", e -> abrirChat()));
        return barra;
    }

    private JMenuItem crearItem(String texto, java.awt.event.ActionListener accion) {
        JMenuItem item = new JMenuItem(texto);
        item.setMaximumSize(new Dimension(item.getPreferredSize().width + 10, 30));
        item.addActionListener(accion);
        return item;
    }

    private JPanel crearTarjetaSaldo() {
        JPanel p = tarjeta();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        JLabel titulo = new JLabel("Saldo");
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 14f));
        lblSaldo.setFont(lblSaldo.getFont().deriveFont(Font.BOLD, 26f));
        titulo.setAlignmentX(LEFT_ALIGNMENT);
        lblSaldo.setAlignmentX(LEFT_ALIGNMENT);
        p.add(titulo);
        p.add(Box.createVerticalStrut(8));
        p.add(lblSaldo);
        return p;
    }

    private JPanel crearTarjetaTelefono() {
        JPanel p = tarjeta();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        JLabel titulo = new JLabel("Teléfono activo");
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 14f));
        titulo.setAlignmentX(LEFT_ALIGNMENT);
        lblTelefono.setAlignmentX(LEFT_ALIGNMENT);

        lblEstado.setOpaque(true);
        lblEstado.setBackground(Color.WHITE);
        lblEstado.setFont(lblEstado.getFont().deriveFont(Font.PLAIN, 11f));
        lblEstado.setBorder(new CompoundBorder(
                new LineBorder(Color.DARK_GRAY, 1, true), new EmptyBorder(2, 8, 2, 8)));
        lblEstado.setAlignmentX(LEFT_ALIGNMENT);
        lblEstado.setMaximumSize(lblEstado.getPreferredSize());

        p.add(titulo);
        p.add(Box.createVerticalStrut(8));
        p.add(lblTelefono);
        p.add(Box.createVerticalStrut(6));
        p.add(lblEstado);
        return p;
    }

    private JPanel crearPanelBienvenida() {
        JPanel p = tarjeta();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        lblBienvenido.setFont(lblBienvenido.getFont().deriveFont(Font.BOLD, 14f));
        lblBienvenido.setAlignmentX(LEFT_ALIGNMENT);

        btnTerminal.setBackground(AZUL);
        btnTerminal.setForeground(Color.WHITE);
        btnTerminal.setOpaque(true);
        btnTerminal.setBorderPainted(false);
        btnTerminal.setFocusPainted(false);
        btnTerminal.addActionListener(e -> alternarTerminal());

        btnCerrarSesion.setBackground(new Color(0xFBEAEA));
        btnCerrarSesion.setForeground(ROJO);
        btnCerrarSesion.setOpaque(true);
        btnCerrarSesion.setBorder(new LineBorder(ROJO));
        btnCerrarSesion.setFocusPainted(false);
        btnCerrarSesion.addActionListener(e -> cerrarSesion());

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        botones.setOpaque(false);
        botones.setAlignmentX(LEFT_ALIGNMENT);
        btnTerminal.setPreferredSize(new Dimension(190, 38));
        btnCerrarSesion.setPreferredSize(new Dimension(120, 38));
        botones.add(btnTerminal);
        botones.add(btnCerrarSesion);

        p.add(lblBienvenido);
        p.add(Box.createVerticalStrut(10));
        p.add(botones);
        return p;
    }

    private JPanel tarjeta() {
        JPanel p = new JPanel();
        p.setBackground(Color.WHITE);
        p.setBorder(new CompoundBorder(new LineBorder(Color.GRAY, 1), new EmptyBorder(10, 10, 10, 10)));
        return p;
    }

    // ---------- Datos del afiliado ----------

    /** Llama esto con los datos reales del afiliado que inició sesión. */
    public void setAfiliado(String nombreCompleto, double saldo, String telefono) {
        this.saldo = saldo;
        lblBienvenido.setText("Bienvenido, " + nombreCompleto);
        lblTelefono.setText(telefono);
        actualizarSaldo();
    }

    /** Úsalo al regresar de la pantalla de Recargar saldo. */
    public void setSaldo(double nuevoSaldo) {
        this.saldo = nuevoSaldo;
        actualizarSaldo();
    }

    private void actualizarSaldo() {
        lblSaldo.setText(NumberFormat.getCurrencyInstance(Locale.US).format(saldo));
    }

    // ---------- Acciones ----------

    private void alternarTerminal() {
        terminalEncendida = !terminalEncendida;
        if (terminalEncendida) {
            btnTerminal.setText("Apagar terminal");
            lblEstado.setText("Terminal encendida");
        } else {
            btnTerminal.setText("Encender terminal");
            lblEstado.setText("Terminal apagada");
        }
        lblEstado.setMaximumSize(lblEstado.getPreferredSize());
        // TODO: aquí va la lógica real (conectar/desconectar la terminal en tu modelo)
    }

    private void cerrarSesion() {
        int r = JOptionPane.showConfirmDialog(this, "¿Seguro que quieres cerrar sesión?",
                "Cerrar sesión", JOptionPane.YES_NO_OPTION);
        if (r == JOptionPane.YES_OPTION) {
            dispose();
            // TODO: abre tu ventana de login, por ejemplo:
            // new ViewLogin().setVisible(true);
        }
    }

    // Conecta aquí tus otras vistas. Cambia los nombres por los de tus clases.
    private void abrirAjustes() {
        // new ViewAjustes().setVisible(true);
        avisoPendiente("Ajustes");
    }

    private void abrirRecargarSaldo() {
        // new ViewRecargarSaldo(this).setVisible(true);
        avisoPendiente("Recargar saldo");
    }

    private void abrirMisTelefonos() {
        // new ViewMisTelefonos().setVisible(true);
        avisoPendiente("Mis teléfonos");
    }

    private void abrirTarifas() {
        // new ViewTarifas().setVisible(true);
        avisoPendiente("Tarifas");
    }

    private void abrirChat() {
        // new ViewChat().setVisible(true);
        avisoPendiente("Chat");
    }

    private void avisoPendiente(String pantalla) {
        JOptionPane.showMessageDialog(this, "Pantalla \"" + pantalla + "\" aún no conectada.");
    }

    // ---------- Prueba rápida ----------

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ViewMenuPrincipalAfiliado v = new ViewMenuPrincipalAfiliado();
            v.setAfiliado("Nombre Apellido", 2000.00, "+52 55 1234 5678");
            v.setVisible(true);
        });
    }
}