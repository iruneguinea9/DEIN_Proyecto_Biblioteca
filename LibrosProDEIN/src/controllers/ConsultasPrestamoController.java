/*--------------------------------------------------------------------------------------------------------------- 
    [PROYECTO DEIN 2EV - Irune Guinea Zufiaurre]
    Método: Consultar Prestamo Controller
    Uso: Consultas sobre prestamos
    --------------------------------------------------------------------------------------------------------------- */

package controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

import dao.PrestamoDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.PrestamoDatos;
import util.Propiedades;
import util.Utilidades;

public class ConsultasPrestamoController implements Initializable{

    @FXML
    private TextField alumnoConsultaPrestamo;

    @FXML
    private Button cerrarBtn;

    @FXML
    private TextField libroConsultaPrestamo;
    @FXML
    private ListView<PrestamoDatos> listaConsultaPrestamos;
    private Stage stage;
    private ObservableList<PrestamoDatos> lista;
	private ObservableList<PrestamoDatos> listaBase;
	private ObservableList<PrestamoDatos> listaAlumno;
	private ObservableList<PrestamoDatos> listaLibro;
	private ResourceBundle bundle;
	
	private boolean filtrandoLibro = false;
	private boolean filtrandoAlumno = false;
	/*--------------------------------------------------------------------------------------------------------------- 
    Método: Cerrar
    Uso: Boton cerrar para salir de la ventana    
    --------------------------------------------------------------------------------------------------------------- */	
    @FXML
    void cerrar(ActionEvent event) {
    	stage = (Stage) this.cerrarBtn.getScene().getWindow();
		stage.close();
    }
    /*--------------------------------------------------------------------------------------------------------------- 
    Método: filtra Alumno
    Uso: Filtra los prestamos por el alumno, teniendo en cuenta si se ha filtrado por libro   
    --------------------------------------------------------------------------------------------------------------- */
    @FXML
    void filtraAlumno(KeyEvent event) {
    	ObservableList<PrestamoDatos> lista2 = FXCollections.observableArrayList();
		if (!alumnoConsultaPrestamo.getText().isBlank()) {
			filtrandoAlumno = true;
			if (!filtrandoLibro) {
				listaBase = lista;
			} else {
				listaBase = listaLibro;
			}
			for (int i = 0; i < listaBase.size(); i++) {
				if (listaBase.get(i).getAlumno().toString().toLowerCase().contains(alumnoConsultaPrestamo.getText().toLowerCase())) { 
					lista2.add(listaBase.get(i));
				}
			}
			listaAlumno = lista2;
			listaConsultaPrestamos.setItems(lista2);
		} else {
			filtrandoAlumno = false;
			lista2.clear();
			if (!filtrandoLibro) {
				listaConsultaPrestamos.setItems(lista);

			} else {
				listaConsultaPrestamos.setItems(listaLibro);
			}

		}
    }
    /*--------------------------------------------------------------------------------------------------------------- 
    Método: filtra Libro
    Uso: filtra por libro teniendo en cuenta si se ha filtrado por alumno  
    --------------------------------------------------------------------------------------------------------------- */
    @FXML
    void filtraLibro(KeyEvent event) {
    	ObservableList<PrestamoDatos> lista2 = FXCollections.observableArrayList();
		if (!libroConsultaPrestamo.getText().isBlank()) {
			filtrandoLibro = true;
			if (!filtrandoAlumno) {
				listaBase = lista;
			} else {
				listaBase = listaAlumno;
			}
			for (int i = 0; i < listaBase.size(); i++) {
				if (listaBase.get(i).getTitulo().toString().toLowerCase().contains(libroConsultaPrestamo.getText().toLowerCase())) { 
					lista2.add(listaBase.get(i));
				}
			}
			listaLibro = lista2;
			listaConsultaPrestamos.setItems(lista2);
		} else {
			filtrandoLibro = false;
			lista2.clear();
			if (!filtrandoAlumno) {
				listaConsultaPrestamos.setItems(lista);

			} else {
				listaConsultaPrestamos.setItems(listaAlumno);
			}

		}
    }
    /*--------------------------------------------------------------------------------------------------------------- 
    Método: Initialize
    Uso: Cargar la lista con los prestamos   
    --------------------------------------------------------------------------------------------------------------- */
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			String idioma = Propiedades.getValor("idioma");
			String region = Propiedades.getValor("region");
			Locale.setDefault(new Locale(idioma, region));
			bundle = ResourceBundle.getBundle("idiomas/messages");
			
			lista = FXCollections.observableArrayList();

			lista = PrestamoDao.cargarTabla();

			listaConsultaPrestamos.setItems(lista);
			
			
		} catch (SQLException e) {
			Utilidades.mostrarAlertInfo(stage,  bundle.getString("err2"));
		}

	}
}
