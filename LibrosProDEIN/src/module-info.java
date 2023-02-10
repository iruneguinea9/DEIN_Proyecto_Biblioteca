module LibrosProDEIN {
	requires javafx.controls;
	requires java.desktop;
	requires javafx.web;
	requires javafx.fxml;
	requires javafx.swing;
	requires javafx.media;
	requires javafx.graphics;
	requires javafx.base;
	requires java.sql;
	requires jasperreports;
	requires jPDFViewerFX;
	requires org.kordamp.ikonli.javafx;
	opens application to javafx.graphics, javafx.fxml;
	opens controllers to javafx.graphics, javafx.fxml;
	opens util to java.sql;
	opens model to javafx.graphics, javafx.fxml,javafx.base;
	opens dao to javafx.base;
}