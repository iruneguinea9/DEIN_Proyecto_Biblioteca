/*--------------------------------------------------------------------------------------------------------------- 
 	[PROYECTO DEIN 2EV - Irune Guinea Zufiaurre]
    Modelo: Historico Prestamo
    --------------------------------------------------------------------------------------------------------------- */
package model;

import java.sql.Date;
import java.sql.SQLException;

import dao.AlumnoDao;
import dao.LibrosDao;

public class HistoricoPrestamo {
	private String titulo;
	private String alumno;
	private Date fecha_prestamo;
	private Date fecha_devolucion;
	
	public HistoricoPrestamo(String dni,int codigo_libro,Date fecha_prestamo,Date fecha_devolucion) throws SQLException {
		this.titulo = LibrosDao.cualLibro(codigo_libro).getTitulo();
		this.alumno = AlumnoDao.cualAlumno(dni).getNombre();
		this.fecha_prestamo= fecha_prestamo;
		this.fecha_devolucion=fecha_devolucion;
	}
	
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getAlumno() {
		return alumno;
	}
	public void setAlumno(String alumno) {
		this.alumno = alumno;
	}
	public Date getFecha_prestamo() {
		return fecha_prestamo;
	}
	public void setFecha_prestamo(Date fecha_prestamo) {
		this.fecha_prestamo = fecha_prestamo;
	}
	public Date getFecha_devolucion() {
		return fecha_devolucion;
	}
	public void setFecha_devolucion(Date fecha_devolucion) {
		this.fecha_devolucion = fecha_devolucion;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.titulo+": "+this.alumno+" ["+this.fecha_prestamo+"] - ["+this.fecha_devolucion+"]";
	}
}
