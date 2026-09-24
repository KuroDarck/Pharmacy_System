module io.github.kurodarck.pharmacysystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;
    requires java.management;

    // Apertura de paquetes para reflexión de JavaFX (Vistas y Controladores)
    opens io.github.kurodarck.pharmacysystem to javafx.fxml;
    opens io.github.kurodarck.pharmacysystem.controller to javafx.fxml;
    opens io.github.kurodarck.pharmacysystem.model to javafx.base, javafx.fxml;

    // Exportación de paquetes para visibilidad pública en el proyecto
    exports io.github.kurodarck.pharmacysystem;
    exports io.github.kurodarck.pharmacysystem.controller;
    exports io.github.kurodarck.pharmacysystem.model;
    exports io.github.kurodarck.pharmacysystem.config;
    exports io.github.kurodarck.pharmacysystem.dao;
}