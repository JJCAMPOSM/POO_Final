import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MostrarUsuarios extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private usuariosCRUD crud; // Instancia de tu clase CRUD

    public MostrarUsuarios() {
        crud = new usuariosCRUD(); // Inicializa tu CRUD
        initComponents();
        cargarUsuarios(); // Carga los usuarios al iniciar
    }

    private void initComponents() {
        setTitle("Mostrar Usuarios");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        // Crear el modelo de la tabla
        String[] columnNames = {"ID", "Nombre", "Correo", "ID Departamento"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void cargarUsuarios() {
        List<Object[]> usuarios = crud.obtenerUsuarios(); // Método que debes implementar en usuariosCRUD
        for (Object[] usuario : usuarios) {
            tableModel.addRow(usuario);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MostrarUsuarios().setVisible(true);
        });
    }
}