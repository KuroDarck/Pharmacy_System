package io.github.kurodarck.pharmacysystem.controller;

import io.github.kurodarck.pharmacysystem.model.Employee;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class SystemViewController {
    @FXML
    public AnchorPane systemView;
    public Button exitBotton;

    @FXML
    public void initialize() {
        Platform.runLater(() -> {
            Stage stage = (Stage) systemView.getScene().getWindow();
            if (stage != null) {
                stage.setResizable(false);
            }
        });
        exitBotton.setOnMouseClicked(event -> {Platform.exit(); System.exit(0);});
    }


    @FXML
    private void onMenuMouseEntered(MouseEvent event) {
        Label label = (Label) event.getSource();
        label.setStyle("-fx-background-color: #1a2332; -fx-text-fill: #2ecc71; -fx-background-radius: 4; -fx-cursor: hand;");
    }

    @FXML
    private void onMenuMouseExited(MouseEvent event) {
        Label label = (Label) event.getSource();
        label.setStyle("-fx-background-color: transparent; -fx-text-fill: white;");
    }
    private Employee employee =new Employee();
    public void setEmployee(Employee employee) {
        this.employee=employee;
    }
}
