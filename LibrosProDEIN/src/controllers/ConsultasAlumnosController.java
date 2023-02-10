/*--------------------------------------------------------------------------------------------------------------- 
    [PROYECTO DEIN 2EV - Irune Guinea Zufiaurre]
    Clase: Consultas Alumnos Controller
    Uso: Consultas sobre Alumnos  
    --------------------------------------------------------------------------------------------------------------- */
package controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

import dao.AlumnoDao;
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
import model.Alumno;
import util.Propiedades;
import util.Utilidades;

public class ConsultasAlumnosController implements Initializable {

	@FXML
	private Button cerrarBtn;

	@FXML
	private TextField dniTxt;

	@FXML
	private ListView<Alumno> listaAlumnos;

	@FXML
	private TextField nombreTxt;

	private ObservableList<Alumno> lista;
	private ObservableList<Alumno> listaBase;
	private ObservableList<Alumno> listaNombre;
	private ObservableList<Alumno> listaCodigo;
	private Stage stage;
	private ResourceBundle bundle;
	private boolean filtrandoDni = false;
	private boolean filtrandoNombre = false;

	/*--------------------------------------------------------------------------------------------------------------- 
	Método: filtra Codigo
	Uso: Filtra los Alumnos por el dni, teniendo en cuenta si se ha filtrado por nombre
	--------------------------------------------------------------------------------------------------------------- */
	@FXML
	void filtraCodigo(KeyEvent event) {
		ObservableList<Alumno> lista2 = FXCollections.observableArrayList();
		try {
			if (!dniTxt.getText().isBlank()) {
				filtrandoDni = true;
				if (!filtrandoNombre) {
					listaBase = lista;
				} else {
					listaBase = listaNombre;
				}
				for (int i = 0; i < listaBase.size(); i++) {
					if (listaBase.get(i).getDni().toString().toLowerCase()
							.contains(dniTxt.getText().toString().toLowerCase())) {
						lista2.add(listaBase.get(i));
					}
				}
				listaCodigo = lista2;
				listaAlumnos.setItems(lista2);
			} else {
				lista2.clear();
				filtrandoDni = false;
				if (!filtrandoNombre) {
					listaAlumnos.setItems(lista);

				} else {
					listaAlumnos.setItems(listaNombre);
				}

			}
		} catch (NumberFormatException e1) {
			Utilidades.mostrarAlertInfo(stage,  bundle.getString("err6"));
			dniTxt.setText("");
		}
	}

	/*--------------------------------------------------------------------------------------------------------------- 
	Método: filtra Nombre
	Uso: filtra los Alumnos por nombre, teniendo en cuenta si se ha filtrado por dni 
	--------------------------------------------------------------------------------------------------------------- */
	@FXML
	void filtraNombre(KeyEvent event) {
		ObservableList<Alumno> lista2 = FXCollections.observableArrayList();
		if (!nombreTxt.getText().isBlank()) {
			filtrandoNombre = true;
			if (!filtrandoDni) {
				listaBase = lista;
			} else {
				listaBase = listaCodigo;
			}
			for (int i = 0; i < listaBase.size(); i++) {
				if (listaBase.get(i).getNombre().toString().toLowerCase().contains(nombreTxt.getText().toLowerCase())) {
					lista2.add(listaBase.get(i));
				}
			}
			listaNombre = lista2;
			listaAlumnos.setItems(lista2);
		} else {
			filtrandoNombre = false;
			lista2.clear();
			if (!filtrandoDni) {
				listaAlumnos.setItems(lista);

			} else {
				listaAlumnos.setItems(listaCodigo);
			}

		}
	}

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
	Método: Initialize
	Uso: para llenar la lista de Alumnos a filtrar
	--------------------------------------------------------------------------------------------------------------- */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			String idioma = Propiedades.getValor("idioma");
			String region = Propiedades.getValor("region");
			Locale.setDefault(new Locale(idioma, region));
			bundle = ResourceBundle.getBundle("idiomas/messages");
			
			lista = FXCollections.observableArrayList();

			lista = AlumnoDao.listaAlumnos();

			listaAlumnos.setItems(lista);

		} catch (SQLException e) {
			Utilidades.mostrarAlertInfo(stage,  bundle.getString("err2"));
		}

	}

}
