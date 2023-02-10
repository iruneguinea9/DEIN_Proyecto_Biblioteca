/*--------------------------------------------------------------------------------------------------------------- 
 	[PROYECTO DEIN 2EV - Irune Guinea Zufiaurre]
    Modelo: Prestamo Datos
    --------------------------------------------------------------------------------------------------------------- */
package model;

import java.sql.Date;

public class PrestamoDatos {
	private String alumno;
	private String titulo;
	private Date fecha;
	public PrestamoDatos(String alumno, String titulo, Date fecha) {
		super();
		this.alumno = alumno;
		this.titulo = titulo;
		this.fecha = fecha;
	}
	public String getAlumno() {
		return alumno;
	}
	public void setAlumno(String alumno) {
		this.alumno = alumno;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	@Override
	public String toString() {
		return this.titulo+" prestado a "+this.alumno+" en la fecha "+this.fecha;
	}
}
