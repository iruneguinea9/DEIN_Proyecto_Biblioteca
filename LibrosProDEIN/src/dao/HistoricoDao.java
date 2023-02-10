/*--------------------------------------------------------------------------------------------------------------- 
 	[PROYECTO DEIN 2EV - Irune Guinea Zufiaurre]
    Clase: Historico dao
    Uso: Conexion y trabajo en la base de datos relacionada con el historico de prestamos
    --------------------------------------------------------------------------------------------------------------- */
package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.HistoricoPrestamo;
import util.ConexionDB;

public class HistoricoDao {
	private static ConexionDB con;
	
    /*--------------------------------------------------------------------------------------------------------------- 
    Método: consultar historico
    Uso: devuelve una lista de historicos entre unos años concretos
    --------------------------------------------------------------------------------------------------------------- */
	public static ObservableList<HistoricoPrestamo> consultarHistorico(Date fechaIni, Date fechaFin, int codigo) throws SQLException {
		con = new ConexionDB();
		Connection conn = con.getConexion();
		ObservableList<HistoricoPrestamo> lstHistorico = FXCollections.observableArrayList();
		HistoricoPrestamo hp = null;
		
		String sql = "select * from libros.Historico_prestamo where libros.Historico_prestamo.fecha_prestamo >= ? and libros.Historico_prestamo.fecha_devolucion < ? and codigo_libro=?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setDate(1, fechaIni);
		ps.setDate(2, fechaFin);
		ps.setInt(3, codigo);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {

			hp = new HistoricoPrestamo(rs.getString(2), codigo, rs.getDate(4), rs.getDate(5));
			lstHistorico.add(hp);

		}
		rs.close();
		ps.close();
		conn.close();

		return lstHistorico;
	}

}
