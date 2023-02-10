/*--------------------------------------------------------------------------------------------------------------- 
    [PROYECTO DEIN 2EV - Irune Guinea Zufiaurre]
    Clase: Consulta Historico Controller
    Uso: Consultas en el historico de prestamos 
    --------------------------------------------------------------------------------------------------------------- */
package controllers;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

import dao.HistoricoDao;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.HistoricoPrestamo;
import util.Propiedades;
import util.Utilidades;

public class ConsultaHistoricoController implements Initializable{

	@FXML
	private TextField codigoLibroTxt;

	@FXML
	private Button cerrar;
	@FXML
	private Button consultarBtn;
    @FXML
    private TextField anio_finTxt;
    private ResourceBundle bundle;
    @FXML
    private TextField anio_iniTxt;

	@FXML
	private ListView<HistoricoPrestamo> listaHistorico;
	private Stage stage;
	/*--------------------------------------------------------------------------------------------------------------- 
    Método: Cerrar
    Uso: Boton cerrar para salir de la ventana    
    --------------------------------------------------------------------------------------------------------------- */
	@FXML
	void cerrar(ActionEvent event) {
		stage = (Stage) this.cerrar.getScene().getWindow();
		stage.close();
	}
	/*--------------------------------------------------------------------------------------------------------------- 
    Método: Consultar
    Uso: para consultar con los filtros introducidos    
    --------------------------------------------------------------------------------------------------------------- */
	@FXML
	void consultar(ActionEvent event) {
		try {
		String str = comprobarCampos();
		if(str.equals(bundle.getString("err"))) {
			// ha ido todo bien
			int anio_ini = Integer.parseInt(anio_iniTxt.getText().toString());
			Date dataini = Date.valueOf(anio_ini+"-01-01");
			int anio_fin = Integer.parseInt(anio_finTxt.getText().toString());
			Date datafin = Date.valueOf(anio_fin+"-12-31");
			int codigo = Integer.parseInt(codigoLibroTxt.getText().toString());
			ObservableList<HistoricoPrestamo> lstHistorico = HistoricoDao.consultarHistorico(dataini,datafin,codigo);
			listaHistorico.setItems(lstHistorico);
			if (lstHistorico.size()==0) {
				Utilidades.mostrarAlertInfo(stage, bundle.getString("warning2"));
			}
		}
		else {
			Utilidades.mostrarAlertInfo(stage, str);
		}
		}catch(NumberFormatException e) {
			Utilidades.mostrarAlertInfo(stage, bundle.getString("err6"));

		} catch (SQLException e) {
			Utilidades.mostrarAlertInfo(stage, bundle.getString("err2"));
		}
	}
	/*--------------------------------------------------------------------------------------------------------------- 
    Método: Comprobar campos
    Uso: para comprobar que los campos introducidos por el usuario son correctos    
    --------------------------------------------------------------------------------------------------------------- */
	private String comprobarCampos() {
		String str = bundle.getString("err");
		if (anio_iniTxt.getText().toString().equals("")) {
			str +=  bundle.getString("check9")+"\n";
		}
		if (anio_finTxt.getText().toString().equals("")) {
			str += bundle.getString("check10")+"\n";
		}
		if(codigoLibroTxt.getText().toString().equals("")) {
			str +=  bundle.getString("check11")+"\n";
		}
		if(anio_iniTxt.getText().toString().length()>4)
			str += bundle.getString("err6")+"\n";
		if(anio_finTxt.getText().toString().length()>4)
			str += bundle.getString("err6")+"\n";
		return str;
	}
	/*--------------------------------------------------------------------------------------------------------------- 
    Método: Initialize
    Uso: para poder usar el bundle
    --------------------------------------------------------------------------------------------------------------- */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		String idioma = Propiedades.getValor("idioma");
		String region = Propiedades.getValor("region");
		Locale.setDefault(new Locale(idioma, region));
		bundle = ResourceBundle.getBundle("idiomas/messages");
		
	}

}
