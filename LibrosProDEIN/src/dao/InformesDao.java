/*--------------------------------------------------------------------------------------------------------------- 
 	[PROYECTO DEIN 2EV - Irune Guinea Zufiaurre]
    Clase: Informes DAO
    Uso: Conexion de base de datos para los informes
    --------------------------------------------------------------------------------------------------------------- */
package dao;

import java.sql.SQLException;

import javafx.scene.control.Alert;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import util.ConexionDB;

public class InformesDao {
	/*--------------------------------------------------------------------------------------------------------------- 
    Método: crear informe 1
    Uso: Lanza el informe sobre el prestamo que se ha hecho
    --------------------------------------------------------------------------------------------------------------- */
	public void crearInforme1() throws SQLException, JRException {
		// lanzar informe de prestamo

	        ConexionDB con = new ConexionDB();
	        JasperReport report = (JasperReport) JRLoader.loadObject(getClass().getResource("/informes/informe1.jasper"));
	        JasperPrint jprint = JasperFillManager.fillReport(report, null, con.getConexion());
	        JasperViewer viewer = new JasperViewer(jprint, false);
	        viewer.setVisible(true);

	}
	/*--------------------------------------------------------------------------------------------------------------- 
    Método: crear informe 2
    Uso: Lanza el informe sobre el listado de prestamos en historico
    --------------------------------------------------------------------------------------------------------------- */
	public void crearInforme2() throws SQLException, JRException {
		// lanzar informe de prestamo

	        ConexionDB con = new ConexionDB();
	        JasperReport report = (JasperReport) JRLoader.loadObject(getClass().getResource("/informes/informe2.jasper"));
	        JasperPrint jprint = JasperFillManager.fillReport(report, null, con.getConexion());
	        JasperViewer viewer = new JasperViewer(jprint, false);
	        viewer.setVisible(true);
	    
	}
	/*--------------------------------------------------------------------------------------------------------------- 
    Método: crear informe 3
    Uso: Lanza el informe con los graficos
    --------------------------------------------------------------------------------------------------------------- */
	public void crearInforme3() throws JRException, SQLException {
		// lanzar informe de prestamo
		
	        ConexionDB con = new ConexionDB();
	        JasperReport report = (JasperReport) JRLoader.loadObject(getClass().getResource("/informes/informe3.jasper"));
	        JasperPrint jprint = JasperFillManager.fillReport(report, null, con.getConexion());
	        JasperViewer viewer = new JasperViewer(jprint, false);
	        viewer.setVisible(true);
	    
	}
	/*--------------------------------------------------------------------------------------------------------------- 
    Método: crear informe 4
    Uso: Lanza el informe con los calculos sobre los alumnos y sus prestamos
    --------------------------------------------------------------------------------------------------------------- */
	public void crearInforme4() throws SQLException, JRException {
		// lanzar informe de prestamo
	
	        ConexionDB con = new ConexionDB();
	        JasperReport report = (JasperReport) JRLoader.loadObject(getClass().getResource("/informes/informe4.jasper"));
	        JasperPrint jprint = JasperFillManager.fillReport(report, null, con.getConexion());
	        JasperViewer viewer = new JasperViewer(jprint, false);
	        viewer.setVisible(true);
	  
	}
}
