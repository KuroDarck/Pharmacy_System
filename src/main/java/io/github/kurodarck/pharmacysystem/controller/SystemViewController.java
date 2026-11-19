package io.github.kurodarck.pharmacysystem.controller;

import io.github.kurodarck.pharmacysystem.model.Employee;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class SystemViewController {

    @FXML
    public AnchorPane systemView;
    @FXML
    public Button exitBotton;
    @FXML
    public StackPane contentArea;
    public Button UserPhoto;
    public ImageView IconPharmacy;

    // Initialise view settings and event handlers
    @FXML
    public void initialize() {
        Platform.runLater(() -> {
            Stage stage = (Stage) systemView.getScene().getWindow();
            if (stage != null) {
                stage.setResizable(false);
            }
        });

        // Handle application exit button click
        exitBotton.setOnMouseClicked(event -> {
            Platform.exit();
            System.exit(0);
        });

        // Load default product view upon initialisation
        loadView("profile-tap.fxml");
    }

    // Dynamically load FXML views into the central StackPane container
    public void loadView(String fxmlFileName) {
        try {
            URL fileUrl = getClass().getResource("/view/" + fxmlFileName);
            if (fileUrl == null) {
                System.out.println("Error: FXML resource not found -> " + fxmlFileName);
                return;
            }
            Parent view = FXMLLoader.load(fileUrl);
            contentArea.getChildren().clear();
            contentArea.getChildren().add(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * SIDEBAR NAVIGATION EVENT HANDLERS
     *
     */

     @FXML
    private void onProductClick() {
        loadView("product-tab.fxml");
    }

    @FXML
    private void onPurchaseClick() {
        loadView("purchase-tab.fxml");
    }

    @FXML
    private void onSalesClick() {
        loadView("sales-tab.fxml");
    }

    @FXML
    private void onCustomerClick() {
        loadView("customer-tap.fxml");
    }

    @FXML
    private void onEmployeeClick() {
        loadView("employee-tap.fxml");
    }

    @FXML
    private void onSupplierClick() {
        loadView("supplier-tap.fxml");
    }

    @FXML
    private void onCategoryClick() {
        loadView("category-tap.fxml");
    }

    @FXML
    private void onReportClick() {
        loadView("businessReport-tap.fxml");
    }

    @FXML
    private void onProfileClick() {
        loadView("profile-tap.fxml");
    }

    // ==========================================================================
    // MOUSE HOVER EFFECTS
    // ==========================================================================

    @FXML
    private void onMenuMouseEntered(MouseEvent event) {
        Label label = (Label) event.getSource();
        label.setStyle("-fx-background-color: #231e2d; -fx-text-fill: #bd88f4; -fx-background-radius: 4; -fx-cursor: hand;");
    }

    @FXML
    private void onMenuMouseExited(MouseEvent event) {
        Label label = (Label) event.getSource();
        label.setStyle("-fx-background-color: transparent; -fx-text-fill: white;");
    }

    // Setter for the authenticated employee session
    public void setEmployee(Employee employee) {
    }
}