package controller;

import dto.CustomerDTO;
import utill.ValidationUtil;
import view.tm.CustomerTM;
import service.ServiceFactory;
import service.custom.CustomerService;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class CustomerFormController implements Initializable {
    public TextField txtTitle;
    public TextField txtName;
    public TextField txtAddress;
    public TextField txtCity;
    public TextField txtProvince;
    public TextField txtPostalCode;
    public TextField txtId;
    public TableView<CustomerTM> tblCustomer;
    public TableColumn colCustomerId;
    public TableColumn colCustomerTitle;
    public TableColumn colCustomerName;
    public TableColumn colCustomerAddress;
    public TableColumn colCustomerCity;
    public TableColumn colCustomerProvince;
    public TableColumn colCustomerPostalCode;
    public Button btnSave;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
    Pattern idPattern = Pattern.compile("^(C)[0-9]{3,4}$");
    Pattern titlePattern = Pattern.compile("^(Miss|Mr|Mrs|Ms)$");
    Pattern namePattern = Pattern.compile("^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$");
    Pattern addressPattern = Pattern.compile("^[A-z0-9/ ,]{6,30}[.]?$");
    Pattern cityPattern = Pattern.compile("^[a-zA-Z]+(?:[\\s-][a-zA-Z]+)*$");
    Pattern provincePattern = Pattern.compile("^[a-zA-Z]+(?:[\\s-][a-zA-Z]+)*$");
    Pattern postalCodePattern = Pattern.compile("^[0-9]{3,4}$");

    private final CustomerService customerService = (CustomerService) ServiceFactory.getBOFactory().getBO(ServiceFactory.BoTypes.CUSTOMER);

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        btnSave.setDisable(true);
        storeValidations();

        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("id"));
            colCustomerTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
            colCustomerName.setCellValueFactory(new PropertyValueFactory<>("name"));
            colCustomerAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
            colCustomerCity.setCellValueFactory(new PropertyValueFactory<>("city"));
            colCustomerProvince.setCellValueFactory(new PropertyValueFactory<>("province"));
            colCustomerPostalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));

        try {
            setCustomersToTable();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void storeValidations() {
        map.put(txtId,idPattern);
        map.put(txtTitle,titlePattern);
        map.put(txtName,namePattern);
        map.put(txtAddress,addressPattern);
        map.put(txtCity,cityPattern);
        map.put(txtProvince,provincePattern);
        map.put(txtPostalCode,postalCodePattern);
    }

    private void setCustomersToTable() throws SQLException, ClassNotFoundException {
        tblCustomer.setItems(customerService.getAllCustomer());
    }

    public void saveCustomerOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        CustomerDTO customerDTO= new CustomerDTO(
                txtId.getText(),txtTitle.getText(),txtName.getText(),
                txtAddress.getText(),txtCity.getText(),txtProvince.getText(),txtPostalCode.getText()
        );
        try {
            if(customerService.saveCustomer(customerDTO)){
                setCustomersToTable();
                new Alert(Alert.AlertType.CONFIRMATION, "Saved the Customer....").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to save the customer " + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        clearField();
    }

    public void deleteCustomerOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        CustomerTM selectedItem = tblCustomer.getSelectionModel().getSelectedItem();
        customerService.deleteCustomer(selectedItem.getId());
        setCustomersToTable();
        customerService.getAllCustomer();
        clearField();
    }

    public void updateCustomerOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String customerId=txtId.getText();
        String title=txtTitle.getText();
        String name=txtName.getText();
        String address=txtAddress.getText();
        String city=txtCity.getText();
        String province=txtProvince.getText();
        String postalCode=txtPostalCode.getText();

        CustomerDTO customerDTO = new CustomerDTO(customerId,title,name,address,city,province,postalCode);

        if( customerService.updateCustomer(customerDTO)){
            setCustomersToTable();
            new Alert(Alert.AlertType.CONFIRMATION,"Update Customer").show();
        }
        tblCustomer.refresh();
        clearField();

    }

    public void tableClickOnAction(MouseEvent mouseEvent) {
        CustomerTM customer = null;
        customer = tblCustomer.getSelectionModel().getSelectedItem();
        txtId.setText(customer.getId());
        txtTitle.setText(customer.getTitle());
        txtName.setText(customer.getName());
        txtAddress.setText(customer.getAddress());
        txtCity.setText(customer.getCity());
        txtProvince.setText(customer.getProvince());
        txtPostalCode.setText(customer.getPostalCode());
    }

    public void orderPlaceOnAction(ActionEvent actionEvent) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("../view/PlaceOrderForm.fxml"));
        Scene scene = new Scene(load);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    public void backToLoginOnAction(ActionEvent actionEvent) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("../view/LoginPageForm.fxml"));
        Scene scene = new Scene(load);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    public void clearField(){
        txtId.clear();
        txtTitle.clear();
        txtName.clear();
        txtAddress.clear();
        txtCity.clear();
        txtProvince.clear();
        txtPostalCode.clear();
    }

    public void textField_KeyReleased(KeyEvent keyEvent) {
        Object response = ValidationUtil.validate(map,btnSave);
        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField){
                TextField errorText =(TextField) response;
                errorText.requestFocus();
            }else if (response instanceof Boolean){
                new Alert(Alert.AlertType.INFORMATION,"Added").showAndWait();
            }
        }
    }
}
