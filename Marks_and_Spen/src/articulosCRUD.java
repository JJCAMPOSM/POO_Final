/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Jesus Campos
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.LinkedHashMap;
import java.util.Map;

public class articulosCRUD {
    
    private Connection conexion;

    public articulosCRUD() {
        conexion = ConexionMySQL.conectar();
    }    
    public boolean crearUsuario(String nom, String cat, int stock){
        
        String sqlInsert="INSERT INTO ARTICULOS(nombre,categoria,stock) VALUE(?,?,?)";
        
        try{
            PreparedStatement ps = conexion.prepareStatement(sqlInsert);
            ps.setString(1,nom);
            ps.setString(2,cat);
            ps.setInt(3,stock);
            return ps.executeUpdate()> 0;
        }
        catch(SQLException e){
            System.out.println("Error al intentar insertar articulo: " + e.getMessage());
            return false;
        }
    }
    
    public ResultSet obtenerArticuloPorID(int id){
        String selectSql= "SELECT a.id_articulo, a.nombre, c.nombre AS categoria, a.stock " +
                           "FROM articulos a " +
                           "JOIN categorias c ON a.id_categoria = c.id_categoria " +
                           "WHERE a.id_articulo = ?";
        
        try {
            PreparedStatement ps = conexion.prepareStatement (selectSql);
            ps.setInt(1, id);
            return ps.executeQuery();
        }
        catch(SQLException x){
            System.out.println("Error al intentar consultar Articulo: " + x.getMessage());
            return null;
        }
    }
    
   public ResultSet obtenerTodos(){
       String sqlTodos= "SELECT a.id_articulo, a.nombre, c.nombre AS categoria, a.stock " +
                          "FROM articulos a " +
                          "JOIN categorias c ON a.id_categoria = c.id_categoria";
       
       try{
           PreparedStatement ps = conexion.prepareStatement (sqlTodos);
            return ps.executeQuery();
       }
       
       catch(SQLException w){
           System.out.println("Error al consultar" +w.getMessage());
           return null;
       }
   }
   
public boolean actualizarArticulo(int id, String nombre, String categoria, int stock) {
    String sql = "UPDATE ARTICULOS SET nombre = ?, categoria = ?, stock = ? WHERE id = ?";
    
    try (PreparedStatement ps = conexion.prepareStatement(sql)) {
        ps.setString(1, nombre);
        ps.setString(2, nombre);
        ps.setInt(3, stock);
        ps.setInt(4, id);
        
        return ps.executeUpdate() > 0;
        
    } catch (SQLException e) {
        System.err.println("Error al actualizar Articulo: " + e.getMessage());
        return false;
    }
}

public boolean eliminarArticulos (int id){
    String sql = "Delete from ARTICULOS WHERE id = ?";
    
    try (PreparedStatement ps = conexion.prepareStatement(sql)) {
        ps.setInt(1, id);
        
        return ps.executeUpdate() > 0;
        
     } catch (SQLException a) {
        System.err.println("Error al eliminar articulos: " + a.getMessage());
        return false;
    }  
  }
    public Map<String, Integer> obtenerCategoriasMap() {
        Map<String, Integer> categoriasMap = new LinkedHashMap<>();
        String sql = "SELECT id_categoria, nombre FROM categorias";
        
        try (PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while(rs.next()) {
                categoriasMap.put(rs.getString("nombre"), rs.getInt("id_categoria"));
            }
            return categoriasMap;
            
        } catch(SQLException e) {
            System.err.println("Error al obtener categorías: " + e.getMessage());
            return null;
        }
    }
    
    public boolean agregarArticulo(String nombre, int idCategoria, int stock) {
        String sql = "INSERT INTO articulos (nombre, id_categoria, stock) VALUES (?, ?, ?)";
        
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setInt(2, idCategoria);
            ps.setInt(3, stock);
            
            return ps.executeUpdate() > 0;
        } catch(SQLException e) {
            System.err.println("Error al agregar artículo: " + e.getMessage());
            return false;
        }
    }
}

