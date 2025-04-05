import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

public class usuariosCRUD {
    private Connection conexion;

    public usuariosCRUD() {
        conexion = ConexionMySQL.conectar(); // Asegúrate de que esta conexión esté bien configurada
    }    

    // Crear Usuario
    public boolean crearUsuario(String nombre, String correo, int idDepartamento) {
        String sqlInsert = "INSERT INTO usuarios(nombre, correo, id_departamento) VALUES(?, ?, ?)";
        
        try {
            PreparedStatement ps = conexion.prepareStatement(sqlInsert);
            ps.setString(1, nombre);
            ps.setString(2, correo);
            ps.setInt(3, idDepartamento);
            return ps.executeUpdate() > 0;
        } catch(SQLException e) {
            System.out.println("Error al intentar insertar usuario: " + e.getMessage());
            return false;
        }
    }

    // Obtener departamentos para usar en la creación de usuario
    public Map<String, Integer> obtenerDepartamentosMap() {
        Map<String, Integer> departamentosMap = new LinkedHashMap<>();
        String sql = "SELECT id_departamento, nombre FROM departamentos";
        
        try (PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while(rs.next()) {
                departamentosMap.put(rs.getString("nombre"), rs.getInt("id_departamento"));
            }
            return departamentosMap;
        } catch(SQLException e) {
            System.err.println("Error al obtener departamentos: " + e.getMessage());
            return null;
        }
    } // fin obtener departamentos
    
        // Método para obtener la lista de usuarios
    public List<Object[]> obtenerUsuarios() {
        List<Object[]> usuarios = new ArrayList<>();
        String sql = "SELECT id_usuario, nombre, correo, id_departamento FROM usuarios";
        
        try (PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                int id = rs.getInt("id_usuario");
                String nombre = rs.getString("nombre");
                String correo = rs.getString("correo");
                int idDepartamento = rs.getInt("id_departamento");
                usuarios.add(new Object[]{id, nombre, correo, idDepartamento});
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener usuarios: " + e.getMessage());
        }
        return usuarios;
    }
    
    public void modificarUsuario(int id, String nombre, String correo) {
    String sql = "UPDATE usuarios SET nombre=?, correo=? WHERE id_usuario=?";
    try (PreparedStatement ps = conexion.prepareStatement(sql)) {
        ps.setString(1, nombre);
        ps.setString(2, correo);
        ps.setInt(3, id);
        ps.executeUpdate();
    } catch (SQLException e) {
        System.err.println("Error al modificar usuario: " + e.getMessage());
    }
} // fin modificar usuario

    public void eliminarUsuario(int id) {
        String sql = "DELETE FROM usuarios WHERE id_usuario=?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al eliminar usuario: " + e.getMessage());
        }
    } // fin eliminar usuario

}
