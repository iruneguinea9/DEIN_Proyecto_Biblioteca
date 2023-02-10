/*--------------------------------------------------------------------------------------------------------------- 
 	[PROYECTO DEIN 2EV - Irune Guinea Zufiaurre]
    Clase: Main
    Uso: Lanzar la ventana principal
    --------------------------------------------------------------------------------------------------------------- */
package application;

import java.util.Locale;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import util.Propiedades;
import util.Utilidades;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			String idioma = Propiedades.getValor("idioma");
			String region = Propiedades.getValor("region");
			Locale.setDefault(new Locale(idioma, region));
			ResourceBundle bundle = ResourceBundle.getBundle("idiomas/messages");
			// abrir ventana principal
			GridPane root = (GridPane)FXMLLoader.load(getClass().getResource("/fxml/VentanaPrincipal.fxml"),bundle);
			Scene scene = new Scene(root);
			// titulo ventana principal
			primaryStage.setTitle(bundle.getString("label1"));
			primaryStage.setScene(scene);
			//imagen para el logo
			scene.getStylesheets().add(getClass().getResource("/css/application.css").toExternalForm());
			Image image = new Image(getClass().getResource("/images/logoBiblioBlanco.png").toString());
			primaryStage.getIcons().add(image);
			primaryStage.show();
		} catch(Exception e) {
			String idioma = Propiedades.getValor("idioma");
			String region = Propiedades.getValor("region");
			Locale.setDefault(new Locale(idioma, region));
			ResourceBundle bundle = ResourceBundle.getBundle("idiomas/messages");
			Utilidades.mostrarAlertInfo(primaryStage,bundle.getString("err1") );
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}