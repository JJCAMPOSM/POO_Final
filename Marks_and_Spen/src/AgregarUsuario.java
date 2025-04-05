import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class AgregarUsuario extends JFrame {
    private JTextField txtNombre;
    private JTextField txtCorreo;
    private JComboBox<String> comboDepartamentos;
    private JButton btnAgregar;
    private usuariosCRUD crud; // Instancia de tu clase CRUD

    public AgregarUsuario() {
        crud = new usuariosCRUD(); // Inicializa tu CRUD
        initComponents();
        cargarDepartamentos(); // Carga los departamentos al iniciar
    }

    private void initComponents() {
        setTitle("Agregar Usuario");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));

        // Crear y añadir etiquetas y campos
        panel.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panel.add(txtNombre);

        panel.add(new JLabel("Correo:"));
        txtCorreo = new JTextField();
        panel.add(txtCorreo);

        panel.add(new JLabel("Departamento:"));
        comboDepartamentos = new JComboBox<>();
        panel.add(comboDepartamentos);

        // Botón para agregar usuario
        btnAgregar = new JButton("Agregar Usuario");
        btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarUsuario();
            }
        });
        panel.add(btnAgregar);

        add(panel);
    }

    private void cargarDepartamentos() {
        Map<String, Integer> departamentos = crud.obtenerDepartamentosMap();
        if (departamentos != null) {
            for (Map.Entry<String, Integer> entry : departamentos.entrySet()) {
                comboDepartamentos.addItem(entry.getKey());
            }
        }
    }

    private void agregarUsuario() {
        String nombre = txtNombre.getText().trim();
        String correo = txtCorreo.getText().trim();
        int idDepartamento = comboDepartamentos.getSelectedIndex() + 1; // Ajustar según la lógica de tu base de datos

        // Validación de campos
        if (nombre.isEmpty() || correo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Llamar al método de CRUD para crear el usuario
        boolean resultado = crud.crearUsuario(nombre, correo, idDepartamento);
        if (resultado) {
            JOptionPane.showMessageDialog(this, "Usuario agregado exitosamente.");
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(this, "Error al agregar usuario.");
        }
    }

    private void limpiarCampos() {
        txtNombre.setText("");
        txtCorreo.setText("");
        comboDepartamentos.setSelectedIndex(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new AgregarUsuario().setVisible(true);
        });
    }
}