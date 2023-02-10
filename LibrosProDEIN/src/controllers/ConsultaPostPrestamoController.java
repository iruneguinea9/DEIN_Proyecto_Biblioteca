/*--------------------------------------------------------------------------------------------------------------- 
    [PROYECTO DEIN 2EV - Irune Guinea Zufiaurre]
    Clase: Consulta Post Prestamo Controller
    Uso: Para saber si se ha modificado el estado del libro tras 
    la devolucion, y si es asi, a cual
    --------------------------------------------------------------------------------------------------------------- */
package controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

import dao.LibrosDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import model.Prestamo;
import util.Propiedades;
import util.Utilidades;

public class ConsultaPostPrestamoController implements Initializable {
	private int codigo;
	@FXML
	private Button btnGuardar;

	@FXML
	private ComboBox<String> cmbEstados;

	@FXML
	private ToggleGroup opciones;
	private ResourceBundle bundle;
	@FXML
	private RadioButton rbNo;
	private Stage stage;

	@FXML
	private RadioButton rbSi;
	/*--------------------------------------------------------------------------------------------------------------- 
    Método: Guardar
    Uso: Modifica el estado del libro si ha cambiado   
    --------------------------------------------------------------------------------------------------------------- */
	@FXML
	void guardar(ActionEvent event) {
		if (rbSi.isSelected()) {
			if (comprobarCampo()) {
				Boolean b;
				try {
					b = LibrosDao.actualizarEstado(codigo, cmbEstados.getSelectionModel().getSelectedItem());
					if (b) {
						Utilidades.mostrarAlertInfo(stage,  bundle.getString("info4"));
						stage = (Stage) this.btnGuardar.getScene().getWindow();
						stage.close();
					}
				} catch (SQLException e) {
					Utilidades.mostrarAlertInfo(stage,  bundle.getString("err2"));

				}

			}
		} else {
			Utilidades.mostrarAlertInfo(stage,  bundle.getString("info4"));
			stage = (Stage) this.btnGuardar.getScene().getWindow();
			stage.close();
		}
	}
	/*--------------------------------------------------------------------------------------------------------------- 
    Método: botonNo
    Uso: Desactiva el combobox ya que no ha cambiado el estado    
    --------------------------------------------------------------------------------------------------------------- */
	@FXML
	void botonNO(ActionEvent event) {
		cmbEstados.setDisable(true);
	}
	/*--------------------------------------------------------------------------------------------------------------- 
    Método: botonSi
    Uso: Activa el combobox para cambiar el estado    
    --------------------------------------------------------------------------------------------------------------- */
	@FXML
	void botonSI(ActionEvent event) {
		cmbEstados.setDisable(false);
	}
	/*--------------------------------------------------------------------------------------------------------------- 
    Método: Comprobar campo
    Uso: Para comprobar que el usuario ha seleccionado algo    
    --------------------------------------------------------------------------------------------------------------- */
	private boolean comprobarCampo() {

		if (cmbEstados.getSelectionModel().getSelectedItem() == null) {
			Utilidades.mostrarAlertInfo(stage,  bundle.getString("check12"));
			return false;
		}
		return true;

	}
	/*--------------------------------------------------------------------------------------------------------------- 
    Método: Initialize
    Uso: rellenar el combobox con los estados del libro    
    --------------------------------------------------------------------------------------------------------------- */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		String idioma = Propiedades.getValor("idioma");
		String region = Propiedades.getValor("region");
		Locale.setDefault(new Locale(idioma, region));
		bundle = ResourceBundle.getBundle("idiomas/messages");
		
		ObservableList<String> posiblesEstados = FXCollections.observableArrayList();
		posiblesEstados.add("Nuevo");
		posiblesEstados.add("Usado nuevo");
		posiblesEstados.add("Usado seminuevo");
		posiblesEstados.add("Usado estropeado");
		posiblesEstados.add("Restaurado");
		cmbEstados.setItems(posiblesEstados);
		;
	}
	/*--------------------------------------------------------------------------------------------------------------- 
    Método: pasoPrestamo
    Uso: Para pasar desde la ventana principal el prestamo al que hacemos referencia
    --------------------------------------------------------------------------------------------------------------- */
	public void pasoPrestamo(Prestamo pres) {
		codigo = pres.getCodigo_libro();

	}

}
