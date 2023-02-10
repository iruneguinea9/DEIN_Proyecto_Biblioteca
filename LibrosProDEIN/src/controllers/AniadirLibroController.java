/*--------------------------------------------------------------------------------------------------------------- 
    [PROYECTO DEIN 2EV - Irune Guinea Zufiaurre]
    Clase: Aniadir libro controller
    Uso: Añadir o modificar libros 
    --------------------------------------------------------------------------------------------------------------- */

package controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Libro;
import util.Propiedades;
import util.Utilidades;

public class AniadirLibroController implements Initializable{

    @FXML
    private TextField autorlTxt;

    @FXML
    private Button cancelarBtn;

    @FXML
    private ComboBox<String> cmbEstado;
	private Image image=null;
	private InputStream foto;
	
    @FXML
    private TextField editorialTxt;

    @FXML
    private Button guardarBtn;

    @FXML
    private Label labelLibro;
    private ResourceBundle bundle;
    @FXML
    private TextField tituloTxt;
    @FXML
    private Button elegirPortadaBtn;
    
    private Stage stage;
    private Libro libro;
    
    /*--------------------------------------------------------------------------------------------------------------- 
    Método: Cancelar
    Uso: Boton cancelar para salir de la ventana    
    --------------------------------------------------------------------------------------------------------------- */
    @FXML
    void cancelarBtn(ActionEvent event) {
    	stage = (Stage) this.cancelarBtn.getScene().getWindow();
		stage.close();
    }
    
    /*--------------------------------------------------------------------------------------------------------------- 
    Método: Elegir Portada
    Uso: Para elegir una portada para el libro    
    --------------------------------------------------------------------------------------------------------------- */

    @FXML
    void elegirPortada(ActionEvent event) {
    	FileChooser elegir = new FileChooser();
		elegir.setTitle("Elegir Foto");
		String currentPath = Paths.get(".").toAbsolutePath().normalize().toString(); 
		elegir.setInitialDirectory(new File(currentPath));
        //Set extension filter
        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("Archivos JPG", "*.jpg", "*.jpg");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
		FileChooser.ExtensionFilter extFilterIMG = new FileChooser.ExtensionFilter("Imagenes", "*.jpg", "*.jpeg","*.png");

        elegir.getExtensionFilters().addAll(extFilterIMG,extFilterJPG, extFilterPNG);

        //Show open file dialog
        File file = elegir.showOpenDialog(null);

        if (file != null) {
            image = new Image(file.toURI().toString());
            
            try {
				foto = new FileInputStream(file);
			} catch (FileNotFoundException e) {
				Utilidades.mostrarAlertInfo(stage, bundle.getString("err5"));
			}
        }
        else {
        	try {
        		URL imageUrl = new URL(getClass().getResource("/images/coverDefault.jpeg").toString());
        		foto = new URL(imageUrl.toString()).openStream();
			} catch (FileNotFoundException | MalformedURLException e) {
				Utilidades.mostrarAlertInfo(stage,bundle.getString("err5"));
			} catch (IOException e) {
				Utilidades.mostrarAlertInfo(stage,bundle.getString("err5"));
			}
        }
	}

    /*--------------------------------------------------------------------------------------------------------------- 
    Método: guardarBtn
    Uso: Boton guardar para añadir o modificar libros    
    --------------------------------------------------------------------------------------------------------------- */
    @FXML
    void guardarBtn(ActionEvent event) {
    	String comprobar = comprobarCampos();
		stage = (Stage) this.cancelarBtn.getScene().getWindow();
    	if(comprobar.equals(bundle.getString("err"))) {
    		try {
    			String titulo = tituloTxt.getText().toString();
	    		String autor = autorlTxt.getText().toString();
	    		String editorial = editorialTxt.getText().toString();
	    		String estado = cmbEstado.getSelectionModel().getSelectedItem().toString();
	    		
	    		// No ha dado ningun error
    			if (stage.getTitle().equals(bundle.getString("titulo8"))) {
		    		
					Boolean b = LibrosDao.aniadirLibro(titulo, autor, editorial, estado,foto);
					if (!b)
						Utilidades.mostrarAlertInfo(stage, bundle.getString("err4"));
					else {
						stage = (Stage) this.cancelarBtn.getScene().getWindow();
						Utilidades.mostrarAlertInfo(stage, bundle.getString("info2"));
						stage.close();
					}
    			}
    			else {
            		Boolean b;
    				if (foto==null) {
    					// la foto no ha cambiado
    					b = LibrosDao.editarLibro(libro,titulo,autor,editorial,estado);
    				}
    				else {
    					b = LibrosDao.editarLibro(libro,titulo,autor,editorial,estado,foto);
    				}
    				
    				if (!b)
						Utilidades.mostrarAlertInfo(stage,bundle.getString("err2"));
					else {
						stage = (Stage) this.cancelarBtn.getScene().getWindow();
						Utilidades.mostrarAlertInfo(stage, bundle.getString("info3"));
						stage.close();
					}
    			}
    		} catch (SQLException e) {
    			e.printStackTrace();
    			Utilidades.mostrarAlertInfo(stage, bundle.getString("err2"));
			}
    	}
    	else {
    		// Hay algun error
    		Utilidades.mostrarAlertInfo(stage, comprobar);
    	}
    }
    /*--------------------------------------------------------------------------------------------------------------- 
    Método: comprobar campos
    Uso: para asegurarme que los campos introducidos por el usuario son correctos   
    --------------------------------------------------------------------------------------------------------------- */
    private String comprobarCampos() {
    	String str = bundle.getString("err");
    	if(tituloTxt.getText().equals(""))
    		str += bundle.getString("check5");
    	if(autorlTxt.getText().equals(""))
    		str += bundle.getString("check6");
    	if(editorialTxt.getText().equals(""))
    		str += bundle.getString("check7");
    	if (cmbEstado.getSelectionModel().getSelectedItem() == null)
    		str += bundle.getString("check8");
    	 	    	
    	return str;
    }
    /*--------------------------------------------------------------------------------------------------------------- 
    Método: rellenar
    Uso: para editar un libro, necesito llenar los campos con el libro seleccionado  
    --------------------------------------------------------------------------------------------------------------- */
    public void rellenar(Libro lb) {
    	libro = lb;
    	labelLibro.setText(bundle.getString("titulo5"));
    	tituloTxt.setText(lb.getTitulo());
    	autorlTxt.setText(lb.getAutor());
    	editorialTxt.setText(lb.getEditorial());
    	if ( lb.getEstado().equals("Nuevo"))
    		cmbEstado.getSelectionModel().select(0);
    	if ( lb.getEstado().equals("Usado nuevo"))
    		cmbEstado.getSelectionModel().select(1);
    	if ( lb.getEstado().equals("Usado seminuevo"))
    		cmbEstado.getSelectionModel().select(2);
    	if ( lb.getEstado().equals("Usado estropeado"))
    		cmbEstado.getSelectionModel().select(3);
    	if ( lb.getEstado().equals("Restaurado"))
    		cmbEstado.getSelectionModel().select(4);
    }
    /*--------------------------------------------------------------------------------------------------------------- 
    Método: Initizalize
    Uso: Para rellenar el combobox con los estados para el libro   
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
		cmbEstado.setItems(posiblesEstados);
		
	}

}
