/*--------------------------------------------------------------------------------------------------------------- 
    [PROYECTO DEIN 2EV - Irune Guinea Zufiaurre]
    Clase: Consultas Libros Controller
    Uso: Consultas sobre libros  
    --------------------------------------------------------------------------------------------------------------- */
package controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

import dao.LibrosDao;
import dao.PrestamoDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.Libro;
import util.Propiedades;
import util.Utilidades;

public class ConsultasLibrosController implements Initializable {

	@FXML
	private Button cerrarBtn;

	@FXML
	private TextField codigoTxt;
	private ResourceBundle bundle;
	@FXML
	private ListView<Libro> listaLIbros;

	@FXML
	private TextField nombreTxt;
	private ContextMenu ctxMenu;

	private ObservableList<Libro> lista;
	private ObservableList<Libro> listaBase;
	private ObservableList<Libro> listaNombre;
	private ObservableList<Libro> listaCodigo;
	private Stage stage;

	private boolean filtrandoCodigo = false;
	private boolean filtrandoNombre = false;

	/*--------------------------------------------------------------------------------------------------------------- 
	Método: filtra Codigo
	Uso: Filtra los libros por el codigo, teniendo en cuenta si se ha filtrado por nombre
	--------------------------------------------------------------------------------------------------------------- */
	@FXML
	void filtraCodigo(KeyEvent event) {
		ObservableList<Libro> lista2 = FXCollections.observableArrayList();
		try {
			if (!codigoTxt.getText().isBlank()) {
				filtrandoCodigo = true;
				if (!filtrandoNombre) {
					listaBase = lista;
				} else {
					listaBase = listaNombre;
				}
				for (int i = 0; i < listaBase.size(); i++) {
					if (listaBase.get(i).getCodigo() == Integer.parseInt(codigoTxt.getText().toString())) {
						lista2.add(listaBase.get(i));
					}
				}
				listaCodigo = lista2;
				listaLIbros.setItems(lista2);
			} else {
				lista2.clear();
				filtrandoCodigo = false;
				if (!filtrandoNombre) {
					listaLIbros.setItems(lista);

				} else {
					listaLIbros.setItems(listaNombre);
				}

			}
		} catch (NumberFormatException e1) {
			Utilidades.mostrarAlertInfo(stage, bundle.getString("err6"));
			codigoTxt.setText("");
		}
	}

	/*--------------------------------------------------------------------------------------------------------------- 
	Método: filtra Nombre
	Uso: filtra los libros por nombre, teniendo en cuenta si se ha filtrado por codigo 
	--------------------------------------------------------------------------------------------------------------- */
	@FXML
	void filtraNombre(KeyEvent event) {
		ObservableList<Libro> lista2 = FXCollections.observableArrayList();
		if (!nombreTxt.getText().isBlank()) {
			filtrandoNombre = true;
			if (!filtrandoCodigo) {
				listaBase = lista;
			} else {
				listaBase = listaCodigo;
			}
			for (int i = 0; i < listaBase.size(); i++) {
				if (listaBase.get(i).getTitulo().toString().toLowerCase().contains(nombreTxt.getText().toLowerCase())) {
					lista2.add(listaBase.get(i));
				}
			}
			listaNombre = lista2;
			listaLIbros.setItems(lista2);
		} else {
			filtrandoNombre = false;
			lista2.clear();
			if (!filtrandoCodigo) {
				listaLIbros.setItems(lista);

			} else {
				listaLIbros.setItems(listaCodigo);
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
	Uso: para llenar la lista de libros a filtrar
	--------------------------------------------------------------------------------------------------------------- */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {

			String idioma = Propiedades.getValor("idioma");
			String region = Propiedades.getValor("region");
			Locale.setDefault(new Locale(idioma, region));
			bundle = ResourceBundle.getBundle("idiomas/messages");

			lista = FXCollections.observableArrayList();

			lista = LibrosDao.cargarTabla();

			listaLIbros.setItems(lista);

			MenuItem bajaItem = new MenuItem(bundle.getString("titulo10"));
			ctxMenu = new ContextMenu();
			ctxMenu.getItems().addAll(bajaItem);
			listaLIbros.setContextMenu(ctxMenu);
			bajaItem.setOnAction(e -> darDeBaja(e));

		} catch (SQLException e) {
			Utilidades.mostrarAlertInfo(stage, bundle.getString("err2"));
		}

	}

	/*--------------------------------------------------------------------------------------------------------------- 
	Método: Dar de baja
	Uso: Cambiar el campo baja del libro a 0
	--------------------------------------------------------------------------------------------------------------- */
	private void darDeBaja(ActionEvent e) {
		try {
			if (listaLIbros.getSelectionModel().getSelectedItem() != null) {
				if (PrestamoDao.estaPrestado(listaLIbros.getSelectionModel().getSelectedItem()))
					Utilidades.mostrarAlertInfo(stage, bundle.getString("warning3"));
				else {
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle(bundle.getString("check15"));
					alert.setHeaderText(bundle.getString("check16"));
					alert.setContentText(bundle.getString("check17"));
					Optional<ButtonType> result = alert.showAndWait();
					if (result.get() == ButtonType.OK) {

						Boolean b = LibrosDao.darDeBaja(listaLIbros.getSelectionModel().getSelectedItem());
						if (b) {
							Utilidades.mostrarAlertInfo(stage, bundle.getString("info1"));
							lista = LibrosDao.cargarTabla();
							listaLIbros.setItems(lista);
							listaLIbros.refresh();
						}

					}
				}
			}
		} catch (SQLException e1) {
			Utilidades.mostrarAlertInfo(stage, bundle.getString("err2"));
		}
	}
}
