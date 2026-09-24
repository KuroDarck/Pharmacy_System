package io.github.kurodarck.pharmacysystem.controller;

import io.github.kurodarck.pharmacysystem.dao.EmployeeDAO;
import io.github.kurodarck.pharmacysystem.model.Employee;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class LoginController {

    @FXML
    public AnchorPane LoginView;
    @FXML
    public PasswordField txt_password;
    @FXML
    public TextField text_UserName;
    @FXML
    public Button Submit;

    private String userName;
    private String password;

    public void OnSubmitButtonClick(ActionEvent onMouseEvent) {
        userName = text_UserName.getText().trim();
        password = txt_password.getText();
        if (userName.isEmpty() || password.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Completed Data");
            alert.setHeaderText("Username or Password are Incomplete.");
            alert.setContentText("Check your Username and password: They need to be complete. ");
            alert.showAndWait();
            txt_password.requestFocus();
        } else {
            EmployeeDAO employeeDAO = new EmployeeDAO();
            try {
                Optional<Employee> employee = employeeDAO.loggingQuery(userName, password);

                if (employee.isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Data Not Found");
                    alert.setHeaderText("Username or Password are Incorrect.");
                    alert.setContentText("Check your Username and password.");
                    alert.showAndWait();
                    txt_password.requestFocus();
                } else {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/io/github/kurodarck/pharmacysystem/view/SystemView.fxml"));
                    Parent root = loader.load();
                    SystemViewController systemViewController = loader.getController();
                    systemViewController.setEmployee(employee.orElse(null));
                    Scene scene = new Scene(root);
                    Stage stage = (Stage) LoginView.getScene().getWindow();
                    stage.setScene(scene);
                    stage.centerOnScreen();
                    stage.show();
                }

            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Connection Error");
                alert.setHeaderText("It was not possible to connect to database.");
                alert.setContentText("Please try again later.");
                alert.showAndWait();
            }  catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

    }

    public void initialize() {
        Platform.runLater(() -> {
            Stage stage = (Stage) LoginView.getScene().getWindow();
            if (stage != null) {
                stage.setResizable(false);
            }
        });
    }

}
