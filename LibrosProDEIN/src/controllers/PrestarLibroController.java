/*--------------------------------------------------------------------------------------------------------------- 
  	[PROYECTO DEIN 2EV - Irune Guinea Zufiaurre]
    Class: Prestar Libro Controller
    Uso:Para prestar libros 
    --------------------------------------------------------------------------------------------------------------- */
package controllers;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Locale;
import java.util.ResourceBundle;

import dao.AlumnoDao;
import dao.InformesDao;
import dao.LibrosDao;
import dao.PrestamoDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import model.Alumno;
import model.Libro;
import model.Prestamo;
import net.sf.jasperreports.engine.JRException;
import util.Propiedades;
import util.Utilidades;

public class PrestarLibroController implements Initializable{

    @FXML
    private Button cancelarBtn;

    @FXML
    private ComboBox<Alumno> cmbAlumnos;

    @FXML
    private ComboBox<Libro> cmbLibros;
    private ResourceBundle bundle;
    @FXML
    private Button prestarBtn;
    private Stage stage;
    /*--------------------------------------------------------------------------------------------------------------- 
    Método: Cancelar
    Uso: Boton cancelar para salir de la ventana    
    --------------------------------------------------------------------------------------------------------------- */
    @FXML
    void cancelar(ActionEvent event) {
    	stage = (Stage) this.prestarBtn.getScene().getWindow();
		stage.close();
    }
    /*--------------------------------------------------------------------------------------------------------------- 
    Método: Prestar
    Uso: Crea un prestamo nuevo
    --------------------------------------------------------------------------------------------------------------- */
    @FXML
    void prestar(ActionEvent event) {
    	String str = comprobarCampos();
    	if(!str.equals(bundle.getString("err"))) {
    		// ha habido algun error
			Utilidades.mostrarAlertInfo(stage,str);
    	}
    	else {
    		// todo bien
    		Boolean b;
			try {
				Prestamo pres = new Prestamo(cmbAlumnos.getSelectionModel().getSelectedItem().getDni(),cmbLibros.getSelectionModel().getSelectedItem().getCodigo(),Date.valueOf(LocalDate.now()));
				b = PrestamoDao.prestarlibro(pres);
				if (b) {
					// lanzar informe 1
					InformesDao id = new InformesDao();
					id.crearInforme1();
					
					Utilidades.mostrarAlertInfo(stage,  bundle.getString("info5"));
					stage = (Stage) this.cancelarBtn.getScene().getWindow();

					stage.close();
					
				}
				else {
					Utilidades.mostrarAlertInfo(stage,  bundle.getString("err3"));
					
				}
			} catch (SQLException e) {
				Utilidades.mostrarAlertInfo(stage,  bundle.getString("err3"));

			} catch (JRException e) {
				// TODO Auto-generated catch block
				Utilidades.mostrarAlertInfo(stage,  bundle.getString("err3"));
			}
    		

    	}
    }
    /*--------------------------------------------------------------------------------------------------------------- 
    Método: Comprobar campo
    Uso: Para asegurarme de que el usuario selecciona los campos   
    --------------------------------------------------------------------------------------------------------------- */
    private String comprobarCampos() {
    	String str =  bundle.getString("err");
    	if(cmbAlumnos.getSelectionModel().getSelectedItem()==null)
    		str+= bundle.getString("check13");
    	if(cmbLibros.getSelectionModel().getSelectedItem()==null)
    		str+= bundle.getString("check14");
    	return str;
    }
    /*--------------------------------------------------------------------------------------------------------------- 
    Método: Initialize
    Uso: Cargar los comboboxes  
    --------------------------------------------------------------------------------------------------------------- */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			String idioma = Propiedades.getValor("idioma");
			String region = Propiedades.getValor("region");
			Locale.setDefault(new Locale(idioma, region));
			bundle = ResourceBundle.getBundle("idiomas/messages");
			
			cmbLibros.setItems(LibrosDao.librosPrestables());
			cmbAlumnos.setItems(AlumnoDao.listaAlumnos());
		} catch (SQLException e) {
			Utilidades.mostrarAlertInfo(stage, bundle.getString("err2"));

		}
	}
	/*--------------------------------------------------------------------------------------------------------------- 
    Método: pasarCosas
    Uso: Pasar el libro que se ha seleccionado en la lista de la ventana principal    
    --------------------------------------------------------------------------------------------------------------- */
	public void pasarCosas(Libro libro) {
		cmbLibros.getSelectionModel().select(libro);
		cmbLibros.setDisable(true);
		
	}

}
