/*--------------------------------------------------------------------------------------------------------------- 
 	[PROYECTO DEIN 2EV - Irune Guinea Zufiaurre]
    Clase: Alumno dao
    Uso: Conexion y trabajo en la base de datos relacionada con alumnos
    --------------------------------------------------------------------------------------------------------------- */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Alumno;
import model.Libro;
import util.ConexionDB;

public class AlumnoDao {
	private static ConexionDB con;
    /*--------------------------------------------------------------------------------------------------------------- 
    Método: lista alumnos
    Uso: Devuelve una lista con todos los alumnos
    --------------------------------------------------------------------------------------------------------------- */
	public static ObservableList<Alumno> listaAlumnos() throws SQLException {
		con = new ConexionDB();
		Connection conn = con.getConexion();
		ObservableList<Alumno> lstAlumnos = FXCollections.observableArrayList();

		Alumno al;
		String sql = "select * from libros.Alumno";
		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {

			al = new Alumno(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4));
			lstAlumnos.add(al);

		}
		rs.close();
		ps.close();
		conn.close();

		return lstAlumnos;
	}
    /*--------------------------------------------------------------------------------------------------------------- 
    Método: cual Alumno
    Uso: Devuelve el alumno que coincide con el dni que se le pasa
    --------------------------------------------------------------------------------------------------------------- */
	public static Alumno cualAlumno(String dni) throws SQLException {
		con = new ConexionDB();
		Connection conn = con.getConexion();
		Alumno al = null;
		PreparedStatement pst = conn
				.prepareStatement("select * from libros.Alumno where dni= ?");
		pst.setString(1, dni);
		ResultSet rs = pst.executeQuery();
		while (rs.next()) {
			al = new Alumno(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4));
		}
		return al;
	}
    /*--------------------------------------------------------------------------------------------------------------- 
    Método: aniadir Alumno
    Uso: para añadir un alumno 
    --------------------------------------------------------------------------------------------------------------- */
	public static Boolean aniadirAlumno(Alumno al) throws SQLException {
		con = new ConexionDB();
		Connection conn = con.getConexion();
		// comprobar que no estaba
		String sql = "select * from libros.Alumno where dni = ?";
		PreparedStatement pst = conn.prepareStatement(sql);
		pst.setString(1, al.getDni());
		ResultSet rs = pst.executeQuery();
		while (rs.next()) {
			// si ya estaba volvemos con false
			return false;
		}
		pst = conn.prepareStatement("insert into libros.Alumno values (?,?,?,?)");
		pst.setString(1, al.getDni());
		pst.setString(2, al.getNombre());
		pst.setString(3, al.getApellido1());
		pst.setString(4, al.getApellido2());
		pst.execute();
		pst.close();
		conn.close();

		return true;
	}
    /*--------------------------------------------------------------------------------------------------------------- 
    Método: editar alumno
    Uso: Para editar un alumno
    --------------------------------------------------------------------------------------------------------------- */
	public static Boolean editarAlumno(Alumno al) throws SQLException {
		con = new ConexionDB();
		Connection conn = con.getConexion();
		PreparedStatement pst = conn
				.prepareStatement("update libros.Alumno set nombre= ?,apellido1= ?,apellido2= ? where dni = ?");
		pst.setString(1, al.getNombre());
		pst.setString(2, al.getApellido1());
		pst.setString(3, al.getApellido2());
		pst.setString(4, al.getDni());
		pst.executeUpdate();
		pst.close();
		conn.close();
		return true;
	}

}
