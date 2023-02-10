/*--------------------------------------------------------------------------------------------------------------- 
 	[PROYECTO DEIN 2EV - Irune Guinea Zufiaurre]
    Modelo: Alumno
    --------------------------------------------------------------------------------------------------------------- */
package model;

public class Alumno {
	private String dni;
	private String nombre;
	private String apellido1;
	private String apellido2;
	
	
	public Alumno(String dni, String nombre, String apellido1, String apellido2) {
		this.dni = dni;
		this.nombre = nombre;
		this.apellido1 = apellido1;
		this.apellido2 = apellido2;
	}
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido1() {
		return apellido1;
	}
	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}
	public String getApellido2() {
		return apellido2;
	}
	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}
	
	@Override
	public String toString() {
		
		return this.nombre+" "+this.apellido1+" "+this.apellido2;
	}
	}
