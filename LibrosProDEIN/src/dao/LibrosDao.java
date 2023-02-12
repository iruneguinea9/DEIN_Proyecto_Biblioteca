/*--------------------------------------------------------------------------------------------------------------- 
 	[PROYECTO DEIN 2EV - Irune Guinea Zufiaurre]
    Clase: Libros dao
    Uso: Conexion y trabajo en la base de datos relacionada con libros
    --------------------------------------------------------------------------------------------------------------- */
package dao;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Libro;
import util.ConexionDB;

public class LibrosDao {
	private static ConexionDB con;

	
    /*--------------------------------------------------------------------------------------------------------------- 
    Método: Cargar tabla
    Uso: Devuelve una lista con todos los libros
    --------------------------------------------------------------------------------------------------------------- */
	public static ObservableList<Libro> cargarTabla() throws SQLException {
		con = new ConexionDB();
		Connection conn = con.getConexion();
		ObservableList<Libro> lstLibros = FXCollections.observableArrayList();
		Libro lb;
		String sql = "SELECT * FROM libros.Libro";
		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			if (rs.getInt(6) == 1) {
				lb = new Libro(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getInt(6),rs.getBlob(7));
				lstLibros.add(lb);
			}
		}
		rs.close();
		ps.close();
		conn.close();

		return lstLibros;
	}

    /*--------------------------------------------------------------------------------------------------------------- 
    Método: aniadir Libro
    Uso: para añadir libros 
    --------------------------------------------------------------------------------------------------------------- */
	public static Boolean aniadirLibro(String titulo, String autor, String editorial, String estado,InputStream portada)
			throws SQLException {

		con = new ConexionDB();
		Connection conn = con.getConexion();
		PreparedStatement pst;
		pst = conn.prepareStatement("insert into libros.Libro (titulo,autor,editorial,estado,baja,portada) values (?,?,?,?,?,?)");
		pst.setString(1, titulo);
		pst.setString(2, autor);
		pst.setString(3, editorial);
		pst.setString(4, estado);
		pst.setInt(5, 1);
		pst.setBlob(6, portada);
		pst.execute();
		pst.close();
		conn.close();

		return true;
	}
	/*--------------------------------------------------------------------------------------------------------------- 
    Método: dar de baja Libro
    Uso: cambiar campo baja a 0
    --------------------------------------------------------------------------------------------------------------- */
	public static Boolean darDeBaja(Libro libro) throws SQLException {
		con = new ConexionDB();
		Connection conn = con.getConexion();
		PreparedStatement pst = conn.prepareStatement("update libros.Libro set baja= ? where codigo = ?");
		pst.setInt(1, 0);
		pst.setInt(2, libro.getCodigo());
		pst.executeUpdate();
		pst.close();
		conn.close();
		return true;
	}
    /*--------------------------------------------------------------------------------------------------------------- 
    Método: editar libro
    Uso: para editar un libro
    --------------------------------------------------------------------------------------------------------------- */
	public static Boolean editarLibro(Libro libro, String titulo, String autor, String editorial, String estado,InputStream portada)
			throws SQLException {
		con = new ConexionDB();
		Connection conn = con.getConexion();
		PreparedStatement pst = conn
				.prepareStatement("update libros.Libro set titulo= ?,autor= ?,editorial= ?,estado= ? ,portada=? where codigo = ?");
		pst.setString(1, titulo);
		pst.setString(2, autor);
		pst.setString(3, editorial);
		pst.setString(4, estado);
		pst.setInt(6, libro.getCodigo());
		pst.setBlob(5, portada);
		pst.executeUpdate();
		pst.close();
		conn.close();
		return true;
	}
	public static Boolean editarLibro(Libro libro, String titulo, String autor, String editorial, String estado)
			throws SQLException {
		con = new ConexionDB();
		Connection conn = con.getConexion();
		PreparedStatement pst = conn
				.prepareStatement("update libros.Libro set titulo= ?,autor= ?,editorial= ?,estado= ? where codigo = ?");
		pst.setString(1, titulo);
		pst.setString(2, autor);
		pst.setString(3, editorial);
		pst.setString(4, estado);
		pst.setInt(5, libro.getCodigo());
		pst.executeUpdate();
		pst.close();
		conn.close();
		return true;
	}
    /*--------------------------------------------------------------------------------------------------------------- 
    Método: libros prestables
    Uso: Devuelve los libros que no esten dados de baja y que no esten prestados ya
    --------------------------------------------------------------------------------------------------------------- */
	public static ObservableList<Libro> librosPrestables() throws SQLException {
		con = new ConexionDB();
		Connection conn = con.getConexion();
		ObservableList<Libro> lstLibros = FXCollections.observableArrayList();

		Libro lb;
		String sql = "select * from libros.Libro where libros.Libro.codigo not in ( select libros.Prestamo.codigo_libro from libros.Prestamo) and baja =1";
		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {

			lb = new Libro(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
					rs.getInt(6),rs.getBlob(7));
			lstLibros.add(lb);

		}
		rs.close();
		ps.close();
		conn.close();

		return lstLibros;
	}
    /*--------------------------------------------------------------------------------------------------------------- 
    Método: actualizar estado
    Uso: actualiza el estado del libro
    --------------------------------------------------------------------------------------------------------------- */
	public static Boolean actualizarEstado(int codigo,String nuevoEstado) throws SQLException {
		con = new ConexionDB();
		Connection conn = con.getConexion();
		PreparedStatement pst = conn
				.prepareStatement("update libros.Libro set estado= ? where codigo = ?");
		pst.setString(1, nuevoEstado);
		pst.setInt(2, codigo);
		pst.executeUpdate();
		pst.close();
		conn.close();
		return true;
	}
    /*--------------------------------------------------------------------------------------------------------------- 
    Método: Libro titulo
    Uso: Devuelve el libro que coincida con el titulo que se le pasa
    --------------------------------------------------------------------------------------------------------------- */
	public static Libro libroTitulo(String titulo) throws SQLException {
		
		con = new ConexionDB();
		Connection conn = con.getConexion();
		Libro lib = null;
		String sql = "SELECT * FROM libros.Libro where libros.Libro.titulo = ?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, titulo);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			lib = new Libro(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5), rs.getInt(6),rs.getBlob(7));
		}
		return lib;
	}
    /*--------------------------------------------------------------------------------------------------------------- 
    Método: cual libro
    Uso: Devuelve el libro que coincide con el codigo que se le pasa
    --------------------------------------------------------------------------------------------------------------- */
	public static Libro cualLibro(int codigo) throws SQLException {
		con = new ConexionDB();
		Connection conn = con.getConexion();
		Libro lib = null;
		PreparedStatement pst = conn
				.prepareStatement("select * from libros.Libro where codigo= ?");
		pst.setInt(1, codigo);
		ResultSet rs = pst.executeQuery();
		while (rs.next()) {
			lib = new Libro(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
					rs.getInt(6),rs.getBlob(7));
		}
		return lib;
	}

	
}

