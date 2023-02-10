/*--------------------------------------------------------------------------------------------------------------- 
 	[PROYECTO DEIN 2EV - Irune Guinea Zufiaurre]
    Clase: Ventana principal controller
    Uso: ventana principal de la aplicacion, tablas con informacion de alumnos, libros y prestamos
    --------------------------------------------------------------------------------------------------------------- */
package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

import com.qoppa.pdf.PDFException;
import com.qoppa.pdfViewerFX.PDFViewer;

import dao.AlumnoDao;
import dao.InformesDao;
import dao.LibrosDao;
import dao.PrestamoDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Alumno;
import model.Libro;
import model.Prestamo;
import model.PrestamoDatos;
import net.sf.jasperreports.engine.JRException;
import util.Propiedades;
import util.Utilidades;

public class VentanaPrincipalController implements Initializable {

	@FXML
	private TableColumn<PrestamoDatos, String> alumnoCol;

	@FXML
	private TableColumn<Alumno, String> apellido1Col;

	@FXML
	private TableColumn<Alumno, String> apellido2Col;
	private PDFViewer visorPDF;
	@FXML
	private TableColumn<Libro, String> autorCol;

	@FXML
	private Button bajaLibroBtn;

	@FXML
	private Button consultarAlumnosBtn;

	@FXML
	private Button consultarLibroBtn;

	@FXML
	private Button consultarPrestamosBtn;
	private ResourceBundle bundle;
	@FXML
	private Button devolverBtn;

	@FXML
	private Button editarAlumnoBtn;

	@FXML
	private Button editarLibroBtn;

	@FXML
	private TableColumn<Libro, String> editorialCol;

	@FXML
	private TableColumn<Libro, String> estadoCol;

	@FXML
	private TableColumn<PrestamoDatos, Date> fechaPrestamoCol;

	@FXML
	private Button historicoPrestamosBtn;

	@FXML
	private TableColumn<Alumno, String> nombreAlumnoCol;

	@FXML
	private Button nuevoAlumnoBtn;

	@FXML
	private Button nuevoLibroBtn;

	@FXML
	private TableColumn<Libro, ImageView> portadaCol;
	@FXML
	private ListView<Libro> lstPrestables;
	@FXML
	private Button prestarBtn;
	@FXML
	private GridPane grid;

	@FXML
	private GridPane grid1;

	@FXML
	private GridPane grid2;
	@FXML
	private GridPane gridGrande;

	@FXML
	private TableView<Alumno> tablaAlumnos;

	@FXML
	private TableView<Libro> tablaLibros;

	@FXML
	private TableView<PrestamoDatos> tablaPrestamos;

	@FXML
	private TableColumn<Libro, String> tituloLibroCol;

	@FXML
	private TableColumn<PrestamoDatos, String> tituloPrestamoCol;

	private ObservableList<Libro> listaLibrosTabla;
	private ObservableList<Libro> listaLibrosPrestables;
	private ObservableList<Alumno> listaAlumnosTabla;
	@FXML
	private MenuItem ayudaMenu;
	@FXML
	private MenuItem menuInforme1;
	@FXML
	private MenuItem menuInforme3;
	@FXML
	private MenuItem menuinforme2;
	@FXML
	private Menu menuAyuda;

	private ObservableList<PrestamoDatos> listaPrestamosTabla;
	private Stage stage;
	/*--------------------------------------------------------------------------------------------------------------- 
	Método: Dar de baja
	Uso: Cambiar el campo baja del libro a 0
	--------------------------------------------------------------------------------------------------------------- */
	@FXML
	void bajaLibroBtn(ActionEvent event) {
		try {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle(bundle.getString("check15"));
			alert.setHeaderText(bundle.getString("check16"));
			alert.setContentText(bundle.getString("check17"));

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				Boolean b = LibrosDao.darDeBaja(tablaLibros.getSelectionModel().getSelectedItem());
				if (!b) {
					Utilidades.mostrarAlertInfo(stage, bundle.getString("err2"));
				} else {
					listaLibrosTabla = LibrosDao.cargarTabla();
					tablaLibros.setItems(listaLibrosTabla);
					tablaLibros.refresh();
					Utilidades.mostrarAlertInfo(stage, bundle.getString("info1"));
				}
			}

		} catch (SQLException e) {
			Utilidades.mostrarAlertInfo(stage, bundle.getString("err2"));

		}
	}
	/*--------------------------------------------------------------------------------------------------------------- 
	Método: Consultar alumnos
	Uso: Lanza la ventana para hacer consultas sobre alumnos
	--------------------------------------------------------------------------------------------------------------- */
	@FXML
	void consultarAlumnosBtn(ActionEvent event) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/consultasAlumnos.fxml"), bundle);
		try {
			Parent root = loader.load();
			Scene newScene = new Scene(root);
			Stage newStage = new Stage();

			newStage.setResizable(false);
			newStage.initModality(Modality.APPLICATION_MODAL);
			newStage.initOwner(this.nuevoLibroBtn.getScene().getWindow());
			newStage.setScene(newScene);
			newStage.setTitle(bundle.getString("titulo1"));
			newStage.showAndWait();
		} catch (IOException e) {
			Utilidades.mostrarAlertInfo(stage, bundle.getString("err1"));
		}
	}
	/*--------------------------------------------------------------------------------------------------------------- 
	Método: Consultar libro 
	Uso: Lanza la ventana para hacer consultas sobre libros
	--------------------------------------------------------------------------------------------------------------- */
	@FXML
	void consultarLibroBtn(ActionEvent event) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/consultasLibros.fxml"), bundle);
		try {
			Parent root = loader.load();
			Scene newScene = new Scene(root);
			Stage newStage = new Stage();

			newStage.setResizable(false);
			newStage.initModality(Modality.APPLICATION_MODAL);
			newStage.initOwner(this.nuevoLibroBtn.getScene().getWindow());
			newStage.setScene(newScene);
			newStage.setTitle(bundle.getString("titulo11"));
			newStage.showAndWait();
			listaLibrosTabla = LibrosDao.cargarTabla();
			tablaLibros.setItems(listaLibrosTabla);
			tablaLibros.refresh();
		} catch (IOException e) {
			Utilidades.mostrarAlertInfo(stage, bundle.getString("err1"));
		} catch (SQLException e) {
			Utilidades.mostrarAlertInfo(stage, bundle.getString("err2"));
		}
	}
	/*--------------------------------------------------------------------------------------------------------------- 
	Método: Constular Prestamos
	Uso: Lanza la ventana para hacer consultas sobre prestamos
	--------------------------------------------------------------------------------------------------------------- */
	@FXML
	void consultarPrestamosBtn(ActionEvent event) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/consultasPrestamo.fxml"), bundle);
		try {
			Parent root = loader.load();
			Scene newScene = new Scene(root);
			Stage newStage = new Stage();

			newStage.setResizable(false);
			newStage.initModality(Modality.APPLICATION_MODAL);
			newStage.initOwner(this.nuevoLibroBtn.getScene().getWindow());
			newStage.setScene(newScene);
			newStage.setTitle(bundle.getString("titulo2"));
			newStage.showAndWait();
		} catch (IOException e) {
			Utilidades.mostrarAlertInfo(stage, bundle.getString("err1"));
		}
	}
	/*--------------------------------------------------------------------------------------------------------------- 
	Método: Devolver libro
	Uso: Devuelve el libro y pregunta al usuario sobre el estado
	--------------------------------------------------------------------------------------------------------------- */
	@FXML
	void devolverBtn(ActionEvent event) {
		if (tablaPrestamos.getSelectionModel().getSelectedItem() != null) {
			// hay alguno seleccionado
			try {
				Prestamo pres = PrestamoDao.datosPrestamo(tablaPrestamos.getSelectionModel().getSelectedItem());
				Boolean b = PrestamoDao.devolverLibro(pres);
				if (b) {
					// libro devuelto
					FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/consultarEstado.fxml"), bundle);
					try {
						Parent root = loader.load();
						Scene newScene = new Scene(root);
						Stage newStage = new Stage();
						ConsultaPostPrestamoController controlador = loader.getController();
						newStage.setResizable(false);
						newStage.initModality(Modality.APPLICATION_MODAL);
						newStage.initOwner(this.nuevoLibroBtn.getScene().getWindow());
						newStage.setScene(newScene);
						newStage.setTitle(bundle.getString("titulo3"));
						controlador.pasoPrestamo(pres);
						newStage.showAndWait();
						listaPrestamosTabla = PrestamoDao.cargarTabla();
						listaLibrosPrestables = LibrosDao.librosPrestables();
						lstPrestables.setItems(listaLibrosPrestables);
						tablaPrestamos.setItems(listaPrestamosTabla);
						tablaPrestamos.refresh();
						lstPrestables.refresh();
					} catch (IOException e) {

						Utilidades.mostrarAlertInfo(stage, bundle.getString("err1"));
					}

				}
			} catch (SQLException e) {
				Utilidades.mostrarAlertInfo(stage, bundle.getString("err2"));

			}
		} else {
			Utilidades.mostrarAlertInfo(stage, bundle.getString("warning1"));
		}
	}
	/*--------------------------------------------------------------------------------------------------------------- 
	Método: Editar alumno
	Uso: Lanza ventana para editar un alumno seleccionado de la tabla
	--------------------------------------------------------------------------------------------------------------- */
	@FXML
	void editarAlumnoBtn(ActionEvent event) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/aniadirAlumno.fxml"), bundle);
		try {
			Parent root = loader.load();
			Scene newScene = new Scene(root);
			Stage newStage = new Stage();
			AniadirAlumnoController controlador = loader.getController();
			newStage.setResizable(false);
			newStage.initModality(Modality.APPLICATION_MODAL);
			newStage.initOwner(this.nuevoAlumnoBtn.getScene().getWindow());
			newStage.setScene(newScene);
			newStage.setTitle(bundle.getString("titulo4"));
			controlador.rellenar(tablaAlumnos.getSelectionModel().getSelectedItem());
			newStage.showAndWait();
			listaAlumnosTabla = AlumnoDao.listaAlumnos();
			tablaAlumnos.setItems(listaAlumnosTabla);
			tablaAlumnos.refresh();
		} catch (IOException e) {
			Utilidades.mostrarAlertInfo(stage, bundle.getString("err1"));
		} catch (SQLException e) {
			Utilidades.mostrarAlertInfo(stage, bundle.getString("err2"));
		}
	}
	/*--------------------------------------------------------------------------------------------------------------- 
	Método: Editar libro
	Uso: Lanza ventana para editar un libro seleccionado de la tabla
	--------------------------------------------------------------------------------------------------------------- */
	@FXML
	void editarLibroBtn(ActionEvent event) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/aniadirLibro.fxml"), bundle);
		try {
			Parent root = loader.load();
			Scene newScene = new Scene(root);
			Stage newStage = new Stage();
			AniadirLibroController controlador = loader.getController();
			newStage.setResizable(false);
			newStage.initModality(Modality.APPLICATION_MODAL);
			newStage.initOwner(this.nuevoLibroBtn.getScene().getWindow());
			newStage.setScene(newScene);
			newStage.setTitle(bundle.getString("titulo5"));
			controlador.rellenar(tablaLibros.getSelectionModel().getSelectedItem());
			newStage.showAndWait();
			listaLibrosTabla = LibrosDao.cargarTabla();
			tablaLibros.setItems(listaLibrosTabla);
			tablaLibros.refresh();
		} catch (IOException e) {
			Utilidades.mostrarAlertInfo(stage, bundle.getString("err1"));
		} catch (SQLException e) {
			Utilidades.mostrarAlertInfo(stage, bundle.getString("err2"));
		}
	}
	/*--------------------------------------------------------------------------------------------------------------- 
	Método: Historico prestamo
	Uso: Lanza ventana para consultar sobre el historico
	--------------------------------------------------------------------------------------------------------------- */
	@FXML
	void historicoPrestamosBtn(ActionEvent event) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/consultaHistorico.fxml"), bundle);
		try {
			Parent root = loader.load();
			Scene newScene = new Scene(root);
			Stage newStage = new Stage();

			newStage.setResizable(false);
			newStage.initModality(Modality.APPLICATION_MODAL);
			newStage.initOwner(this.nuevoLibroBtn.getScene().getWindow());
			newStage.setScene(newScene);
			newStage.setTitle(bundle.getString("titulo6"));
			newStage.showAndWait();
		} catch (IOException e) {

			Utilidades.mostrarAlertInfo(stage, bundle.getString("err1"));
		}
	}
	/*--------------------------------------------------------------------------------------------------------------- 
	Método: Nuevo alumno
	Uso: Lanza ventana para insertar un alumno nuevo
	--------------------------------------------------------------------------------------------------------------- */
	@FXML
	void nuevoAlumnoBtn(ActionEvent event) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/aniadirAlumno.fxml"), bundle);
		try {
			Parent root = loader.load();
			Scene newScene = new Scene(root);
			Stage newStage = new Stage();
			newStage.setResizable(false);
			newStage.initModality(Modality.APPLICATION_MODAL);
			newStage.initOwner(this.nuevoAlumnoBtn.getScene().getWindow());
			newStage.setScene(newScene);
			newStage.setTitle(bundle.getString("titulo7"));
			newStage.showAndWait();
			listaAlumnosTabla = AlumnoDao.listaAlumnos();
			tablaAlumnos.setItems(listaAlumnosTabla);
			tablaAlumnos.refresh();
		} catch (IOException e) {

			Utilidades.mostrarAlertInfo(stage, bundle.getString("err1"));
		} catch (SQLException e) {
			Utilidades.mostrarAlertInfo(stage, bundle.getString("err2"));
		}
	}
	/*--------------------------------------------------------------------------------------------------------------- 
	Método: Nuevo libro
	Uso: Lanza ventana para insertar un libro nuevo
	--------------------------------------------------------------------------------------------------------------- */
	@FXML
	void nuevoLibroBtn(ActionEvent event) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/aniadirLibro.fxml"), bundle);
		try {
			Parent root = loader.load();
			Scene newScene = new Scene(root);
			Stage newStage = new Stage();

			newStage.setResizable(false);
			newStage.initModality(Modality.APPLICATION_MODAL);
			newStage.initOwner(this.nuevoLibroBtn.getScene().getWindow());
			newStage.setScene(newScene);
			newStage.setTitle(bundle.getString("titulo8"));
			newStage.showAndWait();
			listaLibrosTabla = LibrosDao.cargarTabla();
			tablaLibros.setItems(listaLibrosTabla);
			tablaLibros.refresh();
		} catch (IOException e) {

			Utilidades.mostrarAlertInfo(stage, bundle.getString("err1"));
		} catch (SQLException e) {
			Utilidades.mostrarAlertInfo(stage, bundle.getString("err2"));
		}
	}
	/*--------------------------------------------------------------------------------------------------------------- 
	Método: Click tabla alumno
	Uso: Para al seleccionar un alumno habilitar la opcion de editarlo
	--------------------------------------------------------------------------------------------------------------- */
	@FXML
	void clickTablaAlumno(MouseEvent event) {
		if (tablaAlumnos.getSelectionModel().getSelectedItem() != null) {
			editarAlumnoBtn.setDisable(false);
		}
	}
	/*--------------------------------------------------------------------------------------------------------------- 
	Método: Prestar 
	Uso: Lanza ventana para prestar un libro 
	--------------------------------------------------------------------------------------------------------------- */
	@FXML
	void prestarBtn(ActionEvent event) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/prestarLibro.fxml"), bundle);
		try {
			Parent root = loader.load();
			Scene newScene = new Scene(root);
			Stage newStage = new Stage();
			PrestarLibroController controlador = loader.getController();
			newStage.setResizable(false);
			newStage.initModality(Modality.APPLICATION_MODAL);
			newStage.initOwner(this.nuevoLibroBtn.getScene().getWindow());
			newStage.setScene(newScene);
			newStage.setTitle(bundle.getString("titulo9"));
			if (lstPrestables.getSelectionModel().getSelectedItem() != null)
				controlador.pasarCosas(lstPrestables.getSelectionModel().getSelectedItem());
			newStage.showAndWait();
			listaPrestamosTabla = PrestamoDao.cargarTabla();
			listaLibrosPrestables = LibrosDao.librosPrestables();
			lstPrestables.setItems(listaLibrosPrestables);
			tablaPrestamos.setItems(listaPrestamosTabla);
			tablaPrestamos.refresh();
			lstPrestables.refresh();
		} catch (IOException e) {
			Utilidades.mostrarAlertInfo(stage, bundle.getString("err1"));
		} catch (SQLException e) {
			Utilidades.mostrarAlertInfo(stage, bundle.getString("err3"));

		}
	}
	/*--------------------------------------------------------------------------------------------------------------- 
	Método: Click tabla libros
	Uso: Para al seleccionar un libro habilitar las opciones de editarlo y darlo de baja
	--------------------------------------------------------------------------------------------------------------- */
	@FXML
	void clickTabla(MouseEvent event) {
		if (tablaLibros.getSelectionModel().getSelectedItem() != null) {
			editarLibroBtn.setDisable(false);
			bajaLibroBtn.setDisable(false);
		}
	}
	/*--------------------------------------------------------------------------------------------------------------- 
	Método: Lanzar guia rápida
	Uso: Para lanzar la guia rápida en HTML
	--------------------------------------------------------------------------------------------------------------- */
    @FXML
    void lanzarGuiaRapida(ActionEvent event) {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/VisorAyuda.fxml"));
		Parent root;
		try {
			root = loader.load();
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.setTitle(bundle.getString("label18"));
			stage.show();
		} catch (IOException e) {
			Utilidades.mostrarAlertInfo(stage, bundle.getString("errPDF"));
		}
    }
    /*--------------------------------------------------------------------------------------------------------------- 
	Método: Lanzar ayuda
	Uso: Para lanzar la ayuda en PDF
	--------------------------------------------------------------------------------------------------------------- */
	@FXML
	void lanzarAyuda(ActionEvent event) {
		visorPDF = new PDFViewer();
		try {
			visorPDF.loadPDF(getClass().getResource("/pdf/manual.pdf"));
			Stage stage = new Stage();
			BorderPane borderPane = new BorderPane(visorPDF);
			Scene scene = new Scene(borderPane);
			stage.setTitle(bundle.getString("label17"));
			stage.setScene(scene);
			stage.centerOnScreen();
			stage.show();
		} catch (PDFException e) {
			Utilidades.mostrarAlertInfo(stage, bundle.getString("errPDF"));

		}
		

	}
	/*--------------------------------------------------------------------------------------------------------------- 
	Método: Click tabla prestamo
	Uso: Para al seleccionar un prestamo habilitar la opcion de devolverlo
	--------------------------------------------------------------------------------------------------------------- */
	@FXML
	void clickPrestamo(MouseEvent event) {
		if (tablaPrestamos.getSelectionModel().getSelectedItem() != null) {
			devolverBtn.setDisable(false);
		}
	}
	/*--------------------------------------------------------------------------------------------------------------- 
	Método: Lanzar informe 1
	Uso: Para lanzar informe de los prestamos
	--------------------------------------------------------------------------------------------------------------- */
	@FXML
	void lanzarInforme1(ActionEvent event) {
		// lanzar informe 2
		InformesDao id = new InformesDao();
		try {
			id.crearInforme2();
		} catch (SQLException e) {
			Utilidades.mostrarAlertInfo(stage, bundle.getString("err2"));
		} catch (JRException e) {
			Utilidades.mostrarAlertInfo(stage, bundle.getString("errPDF"));

		}
	}
	/*--------------------------------------------------------------------------------------------------------------- 
	Método: Lanzar informe 2
	Uso: Para lanzar informe de los graficos
	--------------------------------------------------------------------------------------------------------------- */
	@FXML
	void lanzarInforme2(ActionEvent event) {
		// lanzar informe 3
		InformesDao id = new InformesDao();
		try {
			id.crearInforme3();
		} catch (JRException e) {
			Utilidades.mostrarAlertInfo(stage, bundle.getString("errPDF"));
		} catch (SQLException e) {
			Utilidades.mostrarAlertInfo(stage, bundle.getString("err2"));

		}
	}
	/*--------------------------------------------------------------------------------------------------------------- 
	Método: Lanzar informe 3
	Uso: Para lanzar informe de los calculos
	--------------------------------------------------------------------------------------------------------------- */
	@FXML
	void lanzarInforme3(ActionEvent event) {
		// lanzar informe 4
		InformesDao id = new InformesDao();
		try {
			id.crearInforme4();
		} catch (SQLException e) {
			Utilidades.mostrarAlertInfo(stage, bundle.getString("err2"));

		} catch (JRException e) {
			Utilidades.mostrarAlertInfo(stage, bundle.getString("errPDF"));
		}
	}
	/*--------------------------------------------------------------------------------------------------------------- 
	Método: Initialize
	Uso: Iniciar el bundle, llenar las tablas, iniciarlas
	--------------------------------------------------------------------------------------------------------------- */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		String idioma = Propiedades.getValor("idioma");
		String region = Propiedades.getValor("region");
		Locale.setDefault(new Locale(idioma, region));
		bundle = ResourceBundle.getBundle("idiomas/messages");

		// para cargar los libros
		listaLibrosTabla = FXCollections.observableArrayList();
		// para cargar los prestamos
		listaPrestamosTabla = FXCollections.observableArrayList();
		// para cargar lista de prestables
		listaLibrosPrestables = FXCollections.observableArrayList();
		// para cargar los alumnos
		listaAlumnosTabla = FXCollections.observableArrayList();
		try {
			listaLibrosTabla = LibrosDao.cargarTabla();
			listaPrestamosTabla = PrestamoDao.cargarTabla();
			listaLibrosPrestables = LibrosDao.librosPrestables();
			listaAlumnosTabla = AlumnoDao.listaAlumnos();

			editarLibroBtn.setDisable(true);
			bajaLibroBtn.setDisable(true);
			editarAlumnoBtn.setDisable(true);
			devolverBtn.setDisable(true);

			// columnas libros
			portadaCol.setCellValueFactory(new PropertyValueFactory<Libro, ImageView>("portada"));
			tituloLibroCol.setCellValueFactory(new PropertyValueFactory<Libro, String>("titulo"));
			autorCol.setCellValueFactory(new PropertyValueFactory<Libro, String>("autor"));
			editorialCol.setCellValueFactory(new PropertyValueFactory<Libro, String>("editorial"));
			estadoCol.setCellValueFactory(new PropertyValueFactory<Libro, String>("estado"));
			portadaCol.prefWidthProperty().bind(tablaLibros.widthProperty().multiply(0.2));
			tituloLibroCol.prefWidthProperty().bind(tablaLibros.widthProperty().multiply(0.2));
			autorCol.prefWidthProperty().bind(tablaLibros.widthProperty().multiply(0.2));
			editorialCol.prefWidthProperty().bind(tablaLibros.widthProperty().multiply(0.15));
			estadoCol.prefWidthProperty().bind(tablaLibros.widthProperty().multiply(0.25));

			// columnas prestamo

			alumnoCol.setCellValueFactory(new PropertyValueFactory<PrestamoDatos, String>("alumno"));
			tituloPrestamoCol.setCellValueFactory(new PropertyValueFactory<PrestamoDatos, String>("titulo"));
			fechaPrestamoCol.setCellValueFactory(new PropertyValueFactory<PrestamoDatos, Date>("fecha"));
			alumnoCol.prefWidthProperty().bind(tablaPrestamos.widthProperty().multiply(0.35));
			tituloPrestamoCol.prefWidthProperty().bind(tablaPrestamos.widthProperty().multiply(0.35));
			fechaPrestamoCol.prefWidthProperty().bind(tablaPrestamos.widthProperty().multiply(0.3));

			// columnas alumno

			nombreAlumnoCol.setCellValueFactory(new PropertyValueFactory<Alumno, String>("nombre"));
			apellido1Col.setCellValueFactory(new PropertyValueFactory<Alumno, String>("apellido1"));
			apellido2Col.setCellValueFactory(new PropertyValueFactory<Alumno, String>("apellido2"));
			nombreAlumnoCol.prefWidthProperty().bind(tablaAlumnos.widthProperty().multiply(0.3));
			apellido1Col.prefWidthProperty().bind(tablaAlumnos.widthProperty().multiply(0.35));
			apellido2Col.prefWidthProperty().bind(tablaAlumnos.widthProperty().multiply(0.35));

			tablaAlumnos.setItems(listaAlumnosTabla);
			lstPrestables.setItems(listaLibrosPrestables);
			tablaPrestamos.setItems(listaPrestamosTabla);
			tablaLibros.setItems(listaLibrosTabla);
		} catch (SQLException e) {
			Utilidades.mostrarAlertInfo(stage, bundle.getString("err2"));
		}

	}
}
