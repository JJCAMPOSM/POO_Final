import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ModificarUsuario extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtNombre, txtCorreo, txtId;
    private usuariosCRUD crud;

    public ModificarUsuario() {
        crud = new usuariosCRUD();
        initComponents();
        cargarUsuarios();
    }

    private void initComponents() {
        setTitle("Modificar Usuario");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        // Crear el modelo de la tabla
        String[] columnNames = {"ID", "Nombre", "Correo", "ID Departamento"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Panel para editar
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));
        panel.add(new JLabel("ID:"));
        txtId = new JTextField();
        panel.add(txtId);
        panel.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panel.add(txtNombre);
        panel.add(new JLabel("Correo:"));
        txtCorreo = new JTextField();
        panel.add(txtCorreo);

        JButton btnModificar = new JButton("Modificar");
        btnModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modificarUsuario();
            }
        });
        panel.add(btnModificar);

        add(panel, BorderLayout.SOUTH);
    }

    private void cargarUsuarios() {
        List<Object[]> usuarios = crud.obtenerUsuarios();
        for (Object[] usuario : usuarios) {
            tableModel.addRow(usuario);
        }

        table.getSelectionModel().addListSelectionListener(e -> {
            if (table.getSelectedRow() != -1) {
                txtId.setText(tableModel.getValueAt(table.getSelectedRow(), 0).toString());
                txtNombre.setText(tableModel.getValueAt(table.getSelectedRow(), 1).toString());
                txtCorreo.setText(tableModel.getValueAt(table.getSelectedRow(), 2).toString());
            }
        });
    }

    private void modificarUsuario() {
        int id = Integer.parseInt(txtId.getText());
        String nombre = txtNombre.getText();
        String correo = txtCorreo.getText();
        crud.modificarUsuario(id, nombre, correo);
        actualizarTabla();
    }

    private void actualizarTabla() {
        tableModel.setRowCount(0);
        cargarUsuarios();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ModificarUsuario().setVisible(true);
        });
    }
}