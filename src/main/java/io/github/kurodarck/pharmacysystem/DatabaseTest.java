package io.github.kurodarck.pharmacysystem;

import io.github.kurodarck.pharmacysystem.config.MySQLConnection;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseTest extends Application {

    public static void main(String[] args) {
        // Inicializa el motor gráfico de JavaFX y llama internamente a start()
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        try {
            Connection conn = MySQLConnection.getConnection();
            if (conn != null && !conn.isClosed()) {
                // Alerta opcional de éxito gráfica
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Éxito");
                alert.setHeaderText("Conexión Establecida");
                alert.setContentText("Conectado exitosamente al catálogo: " + conn.getCatalog());
                alert.showAndWait();
            }
        } catch (SQLException e) {
            // La excepción viaja desde MySQLConnection y se dibuja en pantalla sin colapsar la app
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de Conexión");
            alert.setHeaderText("No se pudo conectar a la Base de Datos");
            alert.setContentText("Detalle técnico: " + e.getMessage());
            alert.showAndWait();
        }
    }
}