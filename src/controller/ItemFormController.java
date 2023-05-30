package controller;

import dto.ItemDTO;
import utill.ValidationUtil;
import view.tm.ItemTM;
import service.ServiceFactory;
import service.custom.ItemService;
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

public class ItemFormController implements Initializable {

    public TextField txtDescription;
    public TextField txtPackSize;
    public TextField txtQtyOnHand;
    public TextField txtUnitPrice;
    public TextField txtCode;
    public TableView <ItemTM> tblItem;
    public TableColumn colItemCode;
    public TableColumn colPackSize;
    public TableColumn colDescription;
    public TableColumn colUnitPrice;
    public TableColumn colQtyOnHand;
    public Button btnSave;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
    Pattern codePattern = Pattern.compile("^(I)[0-9]{3,4}$");
    Pattern descriptionPattern = Pattern.compile("[a-zA-Z]+(?:[\\s-][a-zA-Z]+)*$");
    Pattern packSizePattern = Pattern.compile("^[0-9]{1,2}$");
    Pattern unitPricePattern = Pattern.compile("\\d{1,3}(?:[.,]\\d{3})*(?:[.,]\\d{2})?");
    Pattern qtyOnHandPattern = Pattern.compile("^[0-9]{1,2}$");

    private final ItemService itemService = (ItemService) ServiceFactory.getBOFactory().getBO(ServiceFactory.BoTypes.ITEM);

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        btnSave.setDisable(true);
        storeValidations();

            colItemCode.setCellValueFactory(new PropertyValueFactory<>("code"));
            colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
            colPackSize.setCellValueFactory(new PropertyValueFactory<>("packSize"));
            colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
            colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));

            try {
                setItemsToTable();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
    }

    private void storeValidations() {
        map.put(txtCode,codePattern);
        map.put(txtDescription,descriptionPattern);
        map.put(txtPackSize,packSizePattern);
        map.put(txtUnitPrice,unitPricePattern);
        map.put(txtQtyOnHand,qtyOnHandPattern);
    }

    private void setItemsToTable() throws SQLException, ClassNotFoundException {
        tblItem.setItems(itemService.getAllItem());
    }

    public void saveItemOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        ItemDTO itemDTO= new ItemDTO(
                txtCode.getText(),txtDescription.getText(),txtPackSize.getText(),Double.parseDouble(txtUnitPrice.getText()),Integer.parseInt(txtQtyOnHand.getText())
        );
        try {
            if(itemService.saveItem(itemDTO)){
                setItemsToTable();
                new Alert(Alert.AlertType.CONFIRMATION, "Saved the Customer....").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to save the customer " + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        clearField();
    }

    public void deleteItemOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        ItemTM selectedItem = tblItem.getSelectionModel().getSelectedItem();
        itemService.deleteItem(selectedItem.getCode());
        setItemsToTable();
        itemService.getAllItem();
        clearField();
    }

    public void updateItemOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String code= txtCode.getText();
        String description= txtDescription.getText();
        String packSize= txtPackSize.getText();
        Double unitPrice= Double.parseDouble(txtUnitPrice.getText());
        Integer qtyOnHand=Integer.parseInt(txtQtyOnHand.getText());

        ItemDTO itemDTO = new ItemDTO(code,description,packSize,unitPrice,qtyOnHand);

        if( itemService.updateItem(itemDTO)){
            setItemsToTable();
            new Alert(Alert.AlertType.CONFIRMATION,"Update Customer").show();
        }
        tblItem.refresh();
        clearField();
    }

    public void tableClickOnAction(MouseEvent mouseEvent) {
        ItemTM item = null;
        item = tblItem.getSelectionModel().getSelectedItem();
        txtCode.setText(item.getCode());
        txtDescription.setText(item.getDescription());
        txtPackSize.setText(item.getPackSize());
        txtUnitPrice.setText(String.valueOf(item.getUnitPrice()));
        txtQtyOnHand.setText(String.valueOf(item.getQtyOnHand()));
    }

    public void backToLoginOnAction(ActionEvent actionEvent) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("../view/LoginPageForm.fxml"));
        Scene scene = new Scene(load);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    public void clearField(){
       txtCode.clear();
       txtDescription.clear();
       txtPackSize.clear();
       txtUnitPrice.clear();
       txtQtyOnHand.clear();
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
