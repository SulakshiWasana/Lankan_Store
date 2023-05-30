package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class LoginPageFormController {
    public AnchorPane LoginPageFormContext;
    public TextField txtId;
    public PasswordField txtPsw;

    public void openCashierHomePageForm(ActionEvent actionEvent) throws IOException {
        if (txtId.getText().equals("U001") & txtPsw.getText().equals("2000")) {
            new Alert(Alert.AlertType.CONFIRMATION, "Login Succussfully...").show();
            URL resource = getClass().getResource("../View/CustomerForm.fxml");
            Parent load = FXMLLoader.load(resource);
            Stage window = (Stage) LoginPageFormContext.getScene().getWindow();
            window.setScene(new Scene(load));
        } else {
        }
    }

    public void openAdminHomePageForm(ActionEvent actionEvent) throws IOException {
        if (txtId.getText().equals("U002") & txtPsw.getText().equals("2000")) {
            new Alert(Alert.AlertType.CONFIRMATION, "Login Succussfully...").show();
            URL resource = getClass().getResource("../View/ItemForm.fxml");
            Parent load = FXMLLoader.load(resource);
            Stage window = (Stage) LoginPageFormContext.getScene().getWindow();
            window.setScene(new Scene(load));
        } else {
        }
    }
}
