/*--------------------------------------------------------------------------------------------------------------- 
    [PROYECTO DEIN 2EV - Irune Guinea Zufiaurre]
    Clase: Aniadir Alumno Controller
    Uso: Ventana para añadir o modificar alumnos   
    --------------------------------------------------------------------------------------------------------------- */

package controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

import dao.AlumnoDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Alumno;
import util.Propiedades;
import util.Utilidades;

public class AniadirAlumnoController implements Initializable{

    @FXML
    private TextField ape1Txt;

    @FXML
    private TextField ape2txt;

    @FXML
    private Button cancelarBtn;

    @FXML
    private TextField dniTxt;

    @FXML
    private Button guardarBtn;

    @FXML
    private TextField nombreTxt;
    private Stage stage;
    @FXML
    private Label titulo;
    private ResourceBundle bundle;

    /*--------------------------------------------------------------------------------------------------------------- 
    Método: Cancelar
    Uso: Boton cancelar para salir de la ventana    
    --------------------------------------------------------------------------------------------------------------- */
    @FXML
    void cancelar(ActionEvent event) {
    	stage = (Stage) this.cancelarBtn.getScene().getWindow();
		stage.close();
    }
    /*--------------------------------------------------------------------------------------------------------------- 
    Método: Guardar
    Uso: Boton guardar para añadir o modificar alumno  
    --------------------------------------------------------------------------------------------------------------- */

    @FXML
    void guardar(ActionEvent event) {
    	String comprobar = comprobarCampos();
		stage = (Stage) this.cancelarBtn.getScene().getWindow();
    	if(comprobar.equals(bundle.getString("err"))) {
    		try {
    			String nombre = nombreTxt.getText().toString();
	    		String ape1 = ape1Txt.getText().toString();
	    		String ape2 = ape2txt.getText().toString();
	    		String dni =  dniTxt.getText().toString();
	    		Alumno al = new Alumno(dni,nombre,ape1,ape2);
	    		// No ha dado ningun error
    			if (stage.getTitle().equals(bundle.getString("titulo7"))) {
		    		
					Boolean b = AlumnoDao.aniadirAlumno(al);
					if (!b)
						Utilidades.mostrarAlertInfo(stage, bundle.getString("err4"));
					else {
						stage = (Stage) this.cancelarBtn.getScene().getWindow();
						Utilidades.mostrarAlertInfo(stage,  bundle.getString("info2"));
						stage.close();
					}
    			}
    			else {
    				Boolean b = AlumnoDao.editarAlumno(al);
    				if (!b)
						Utilidades.mostrarAlertInfo(stage,  bundle.getString("err2"));
					else {
						stage = (Stage) this.cancelarBtn.getScene().getWindow();
						Utilidades.mostrarAlertInfo(stage,  bundle.getString("info3"));
						stage.close();
					}
    			}
    		} catch (SQLException e) {
    			Utilidades.mostrarAlertInfo(stage,  bundle.getString("err2"));
			}
    	}
    	else {
    		// Hay algun error
    		Utilidades.mostrarAlertInfo(stage, comprobar);
    	}
    }
    /*--------------------------------------------------------------------------------------------------------------- 
    Método: comprobar campos
    Uso: para asegurarme de que lo introducido por el usuario es correcto
    --------------------------------------------------------------------------------------------------------------- */

    private String comprobarCampos() {
    	String str=bundle.getString("err");
    	if(dniTxt.getText().toString().equals(""))
    		str += bundle.getString("check1");;
    	if(nombreTxt.getText().toString().equals(""))
    		str += bundle.getString("check2");;
    	if(ape1Txt.getText().toString().equals(""))
    		str += bundle.getString("check3");;
    	if(ape2txt.getText().toString().equals(""))
    		str += bundle.getString("check4");;
    	
    	return str;
    }
    
    /*--------------------------------------------------------------------------------------------------------------- 
    Método: rellenar
    Uso: Poner los datos del alumno a modificar, cambiar titulo de label para modificar    
    --------------------------------------------------------------------------------------------------------------- */

    public void rellenar(Alumno al) {
    	dniTxt.setText(al.getDni());
    	dniTxt.setDisable(true);
    	nombreTxt.setText(al.getNombre());
    	ape1Txt.setText(al.getApellido1());
    	ape2txt.setText(al.getApellido2());
    	titulo.setText( bundle.getString("titulo4"));
    }
    
    /*--------------------------------------------------------------------------------------------------------------- 
    Método: initualize
    Uso: Para poder hacer uso del bundle
    --------------------------------------------------------------------------------------------------------------- */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		String idioma = Propiedades.getValor("idioma");
		String region = Propiedades.getValor("region");
		Locale.setDefault(new Locale(idioma, region));
		bundle = ResourceBundle.getBundle("idiomas/messages");
		
	}

}
