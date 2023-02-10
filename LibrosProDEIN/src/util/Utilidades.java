package util;

import javafx.scene.control.Alert;
import javafx.stage.Window;

public class Utilidades {
	public static void mostrarAlertInfo(Window win, String str) {
		Alert alert;
		if (str.contains("Error")) {
			alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Error");
		} else {
			alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Info");
		}
		alert.initOwner(win);
		alert.setHeaderText(null);
		alert.setContentText(str);
		alert.showAndWait();
	}
}
