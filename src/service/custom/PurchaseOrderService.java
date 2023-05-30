package service.custom;

import dto.CustomerDTO;
import dto.ItemDTO;
import dto.OrderDTO;
import service.SuperService;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PurchaseOrderService extends SuperService {

    boolean purchaseOrder(OrderDTO orderDTO) throws SQLException,ClassNotFoundException;

    String generateNewOrderId()throws SQLException, ClassNotFoundException;

    ArrayList<CustomerDTO> getAllCustomers()throws SQLException, ClassNotFoundException;

    ArrayList<ItemDTO> getAllItems()throws SQLException, ClassNotFoundException;

    ItemDTO searchItem(String code)throws SQLException, ClassNotFoundException;

    boolean ifItemExist(String code) throws SQLException, ClassNotFoundException;

    boolean ifCustomerExist(String id) throws SQLException, ClassNotFoundException;

    CustomerDTO searchCustomer(String s)throws SQLException, ClassNotFoundException;

}
