import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class EliminarUsuario extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtId;
    private usuariosCRUD crud;

    public EliminarUsuario() {
        crud = new usuariosCRUD();
        initComponents();
        cargarUsuarios();
    }

    private void initComponents() {
        setTitle("Eliminar Usuario");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);        
        setSize(600, 400);
        setLocationRelativeTo(null);

        // Crear el modelo de la tabla
        String[] columnNames = {"ID", "Nombre", "Correo", "ID Departamento"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Panel para eliminar
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.add(new JLabel("ID del Usuario a Eliminar:"));
        txtId = new JTextField(10);
        panel.add(txtId);

        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarUsuario();
            }
        });
        panel.add(btnEliminar);

        add(panel, BorderLayout.SOUTH);
    }

    private void cargarUsuarios() {
        List<Object[]> usuarios = crud.obtenerUsuarios();
        for (Object[] usuario : usuarios) {
            tableModel.addRow(usuario);
        }
    }

    private void eliminarUsuario() {
        int id = Integer.parseInt(txtId.getText());
        crud.eliminarUsuario(id);
        actualizarTabla();
    }

    private void actualizarTabla() {
        tableModel.setRowCount(0);
        cargarUsuarios();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new EliminarUsuario().setVisible(true);
        });
    }
}