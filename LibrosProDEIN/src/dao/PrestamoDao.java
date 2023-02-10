/*--------------------------------------------------------------------------------------------------------------- 
 	[PROYECTO DEIN 2EV - Irune Guinea Zufiaurre]
    Clase: Prestamo dao
    Uso: Conexion y trabajo en la base de datos relacionada con prestamos
    --------------------------------------------------------------------------------------------------------------- */
package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Alumno;
import model.Libro;
import model.Prestamo;
import model.PrestamoDatos;
import util.ConexionDB;

public class PrestamoDao {
	private static ConexionDB con;

    /*--------------------------------------------------------------------------------------------------------------- 
    Método: cargar Tabla	
    Uso: devuelve una lista de prestamos para la tabla
    --------------------------------------------------------------------------------------------------------------- */
	public static ObservableList<PrestamoDatos> cargarTabla() throws SQLException {
		ObservableList<PrestamoDatos> lstPresTabla = FXCollections.observableArrayList();
		con = new ConexionDB();
		Connection conn = con.getConexion();
		PrestamoDatos pt;
		String sql = "SELECT * FROM libros.Libro,libros.Alumno,libros.Prestamo where libros.Libro.codigo = libros.Prestamo.codigo_libro and libros.Prestamo.dni_alumno  = libros.Alumno.dni";
		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			pt = new PrestamoDatos(rs.getString(8), rs.getString(2), rs.getDate(15));
			lstPresTabla.add(pt);
		}
		return lstPresTabla;
	}
    /*--------------------------------------------------------------------------------------------------------------- 
    Método: prestar libro
    Uso: para prestar un libro
    --------------------------------------------------------------------------------------------------------------- */
	public static Boolean prestarlibro(Prestamo pres) throws SQLException {
		con = new ConexionDB();
		Connection conn = con.getConexion();
		LocalDate date = LocalDate.now();
		PreparedStatement pst = conn
				.prepareStatement("insert into libros.Prestamo (dni_alumno,codigo_libro,fecha_prestamo) values (?,?,?)");
		pst.setString(1, pres.getDni_alumno());
		pst.setInt(2, pres.getCodigo_libro());
		pst.setDate(3, Date.valueOf(LocalDate.now()));
		pst.execute();
		pst.close();
		conn.close();
		return true;
	}
    /*--------------------------------------------------------------------------------------------------------------- 
    Método: devolver libro
    Uso: para devolver un libro, meterlo en el historico y sacarlo de prestamos
    --------------------------------------------------------------------------------------------------------------- */
	public static Boolean devolverLibro(Prestamo pres) throws SQLException {
		con = new ConexionDB();
		Connection conn = con.getConexion();
		LocalDate date = LocalDate.now();
		PreparedStatement pst = conn
				.prepareStatement("insert into libros.Historico_prestamo (dni_alumno,codigo_libro,fecha_prestamo,fecha_devolucion) values (?,?,?,?)");
		pst.setString(1, pres.getDni_alumno());
		pst.setInt(2, pres.getCodigo_libro());
		pst.setDate(3,pres.getFecha_prestamo());
		pst.setDate(4, Date.valueOf(LocalDate.now()));
		pst.execute();
		// Eliminar de tabla prestamos
		pst = conn.prepareStatement("delete from libros.Prestamo where codigo_libro = ?");
		pst.setInt(1, pres.getCodigo_libro());
		pst.execute();
		pst.close();
		conn.close();
		return true;
	}
	public static Prestamo datosPrestamo(PrestamoDatos prestamoDatos) throws SQLException {
		con = new ConexionDB();
		Connection conn = con.getConexion();
		Prestamo pres = null;
		String sql = "SELECT * FROM libros.Prestamo where libros.Prestamo.codigo_libro = ?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1,libroTitulo( prestamoDatos.getTitulo()).getCodigo());
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			pres = new Prestamo(rs.getString(2), rs.getInt(3), rs.getDate(4));
		}
				
		return pres;
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
}
