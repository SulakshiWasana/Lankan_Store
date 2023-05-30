package controller;

import dto.CustomerDTO;
import dto.ItemDTO;
import dto.ItemDetailsDTO;
import dto.OrderDTO;
import view.tm.CartTM;
import service.ServiceFactory;
import service.custom.PurchaseOrderService;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;

import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class PlaceOrderFormController implements Initializable {
    public Label lblOrderId;
    public Label lblDate;
    public Label lblTime;
    public ComboBox<String> cmbCustomerIds;
    public TextField txtTitle;
    public TextField txtAddress;
    public TextField txtName;
    public TextField txtPostalCode;
    public TextField txtProvince;
    public TextField txtCity;
    public ComboBox<String> cmbItemIds;
    public TextField txtDescription;
    public TextField txtQty;
    public TextField txtUnitPrice;
    public TextField txtPackSize;
    public TextField txtQtyOnHand;
    public TableView <CartTM>tblCart;
    public TableColumn colCode;
    public TableColumn colDescription;
    public TableColumn colUnitPrice;
    public TableColumn colQty;
    public TableColumn colTotal;
    public Label txtTtl;

    int cartSelectedRowForRemove = -1;

    private final PurchaseOrderService purchaseOrderService = (PurchaseOrderService) ServiceFactory.getBOFactory().getBO(ServiceFactory.BoTypes.PURCHASE_ORDER);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        loadDateAndTime();
        setOrderID();

        try {
            loadCustomerIds();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            loadItemIds();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        cmbCustomerIds.getSelectionModel().selectedItemProperty().
                addListener((observable, oldValue, newValue) -> {
                    try {
                        setCustomerData(newValue);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                });

        cmbItemIds.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                setItemData(newValue);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        tblCart.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            cartSelectedRowForRemove = (int) newValue;
        });
    }

    private void setOrderID() {
        try {
            lblOrderId.setText(purchaseOrderService.generateNewOrderId());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setItemData(String itemCode) throws SQLException, ClassNotFoundException {
        ItemDTO item = purchaseOrderService.searchItem(itemCode);
        if (item == null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set");
        } else {
            txtDescription.setText(item.getDescription());
            txtPackSize.setText(item.getPackSize());
            txtUnitPrice.setText(String.valueOf(item.getUnitPrice()));
            txtQtyOnHand.setText(String.valueOf(item.getQtyOnHand()));
        }
    }

    private void setCustomerData(String customerId) throws SQLException, ClassNotFoundException {
        CustomerDTO customer = purchaseOrderService.searchCustomer(customerId);
        if (customer == null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set");
        } else {
            txtTitle.setText(customer.getTitle());
            txtName.setText(customer.getName());
            txtAddress.setText(customer.getAddress());
            txtCity.setText(customer.getCity());
            txtProvince.setText(customer.getProvince());
            txtPostalCode.setText(customer.getPostalCode());
            System.out.println(customer.getName());
        }
    }

    private void loadCustomerIds() throws SQLException, ClassNotFoundException {
       ArrayList<CustomerDTO> customerDTOS = purchaseOrderService.getAllCustomers();
       for (CustomerDTO dto: customerDTOS) {
           cmbCustomerIds.getItems().add(dto.getId());
       }
    }

    private void loadItemIds() throws SQLException, ClassNotFoundException {
        ArrayList<ItemDTO> itemDTOS = purchaseOrderService.getAllItems();
        for (ItemDTO dto: itemDTOS) {
            cmbItemIds.getItems().add(dto.getCode());
        }
    }

    private void loadDateAndTime() {
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        lblDate.setText(f.format(date));

        Timeline time = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();
            lblTime.setText(
                    currentTime.getHour() + " : " + currentTime.getMinute() + " : "
                            + currentTime.getSecond()
            );
        }),
                new KeyFrame(Duration.seconds(1))
        );
        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }

    ObservableList<CartTM> obList = FXCollections.observableArrayList();
    public void addToCartOnAction(ActionEvent actionEvent) {
        String description = txtDescription.getText();
        int qtyOnHand = Integer.parseInt(txtQtyOnHand.getText());
        double unitPrice = Double.parseDouble(txtUnitPrice.getText());
        int qtyForCustomer = Integer.parseInt(txtQty.getText());
        double total = qtyForCustomer * unitPrice;

        if (qtyOnHand < qtyForCustomer) {
            new Alert(Alert.AlertType.WARNING, "Invalid QTY").show();
            return;
        }

        CartTM tm = new CartTM(
                cmbItemIds.getValue(),
                description,
                qtyForCustomer,
                unitPrice,
                total,
                lblOrderId.getText()

        );

        int rowNumber = isExists(tm);

        if (isExists(tm) == -1) {
            obList.add(tm);
        } else {
            CartTM temp = obList.get(rowNumber);
            CartTM newTm = new CartTM(
                   temp.getCode(),
                    temp.getDescription(),
                    temp.getQty()+qtyForCustomer,
                    unitPrice,
                    total+temp.getTotal(),
                    temp.getOrderId()
            );

            obList.remove(rowNumber);
            obList.add(newTm);
        }
        tblCart.setItems(obList);

        calculateCost();
    }

    private int isExists(CartTM tm) {

        for (int i = 0; i < obList.size(); i++) {
            if (tm.getCode().equals(obList.get(i).getCode())) {
                return i;
            }
        }
        return -1;
    }

    void calculateCost() {
        double ttl = 0;
        for (CartTM tm : obList
        ) {
            ttl += tm.getTotal();
        }
        txtTtl.setText(ttl + " /=");
    }

    public void clearOnAction(ActionEvent actionEvent) {
        if (cartSelectedRowForRemove == -1) {
            new Alert(Alert.AlertType.WARNING, "Please Select a Row").show();
        } else {
            obList.remove(cartSelectedRowForRemove);
            calculateCost();
            tblCart.refresh();
        }
    }

    public void placeOrderOnAction(ActionEvent actionEvent) {
        ArrayList<ItemDetailsDTO> items = new ArrayList<>();
        double total=0;
        for (CartTM tempTm:obList
        ) {
            total+=tempTm.getTotal();
            items.add(
                    new ItemDetailsDTO(
                            tempTm.getCode() ,
                            tempTm.getOrderId(),
                            tempTm.getQty(),
                            tempTm.getUnitPrice()
                    )
            );
        }

        OrderDTO order = new OrderDTO(
                lblOrderId.getText(),
                cmbCustomerIds.getValue(),
                lblDate.getText(),
                total,
                items
        );

        try {
            if (purchaseOrderService.purchaseOrder(order)){
                new Alert(Alert.AlertType.CONFIRMATION,"Success").show();
                setOrderID();
            }else{
                new Alert(Alert.AlertType.WARNING,"Try Again").show();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
