/*--------------------------------------------------------------------------------------------------------------- 
 	[PROYECTO DEIN 2EV - Irune Guinea Zufiaurre]
    Modelo: Libro
    --------------------------------------------------------------------------------------------------------------- */
package model;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Libro {
	private int codigo;
	private String titulo;
	private String autor;
	private String editorial;
	private String estado;
	private int baja;
	private ImageView portada;
	
	public Libro(int codigo, String titulo, String autor, String editorial, String estado, int baja, Blob portada) {
		if (portada == null) {
			Image foto = new Image(getClass().getResourceAsStream("/images/coverDefault.jpeg"),100,100, false, false);
			this.portada = new ImageView(foto);
		} else {
			try {
				InputStream is = portada.getBinaryStream();
				this.portada = new ImageView(new Image(is,100,100, false, false));
				is.close();	
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			this.codigo=codigo;
			this.titulo = titulo;
			this.autor = autor;
			this.editorial = editorial;
			this.estado = estado;
			this.baja = baja;
		
		
	}
	public int getCodigo() {
		return codigo;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getAutor() {
		return autor;
	}
	public void setAutor(String autor) {
		this.autor = autor;
	}
	public String getEditorial() {
		return editorial;
	}
	public void setEditorial(String editorial) {
		this.editorial = editorial;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public int getBaja() {
		return baja;
	}
	public void setBaja(int baja) {
		this.baja = baja;
	}
	public ImageView getPortada() {
		return portada;
	}
	@Override
	public String toString() {
		String str = "["+this.codigo+"] "+this.titulo+" de "+this.autor;
		return str;
	}
	
}
