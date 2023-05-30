package service.custom.impl;

import entity.Customer;
import entity.Item;
import entity.ItemDetails;
import entity.Order;
import dto.CustomerDTO;
import dto.ItemDTO;
import dto.ItemDetailsDTO;
import dto.OrderDTO;
import service.custom.PurchaseOrderService;
import dao.Custom.CustomerDAO;
import dao.Custom.ItemDAO;
import dao.Custom.OrderDAO;
import dao.Custom.impl.ItemDetailsDAOImpl;
import dao.DAOFactory;
import db.DbConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class PurchaseOrderServiceImpl implements PurchaseOrderService {

    private final CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);
    private final ItemDAO itemDAO = (ItemDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ITEM);
    private final OrderDAO orderDAO = (OrderDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ORDER);
    private final ItemDetailsDAOImpl itemDetailsDAO = (ItemDetailsDAOImpl) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ITEMDETAIL);

    @Override
    public boolean purchaseOrder(OrderDTO o) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        con.setAutoCommit(false);
        if (orderDAO.save(new Order(o.getOrderID(),o.getCID(),o.getOrderDate(),o.getCost()))){
            for (ItemDetailsDTO i: o.getItems()){
                if (!itemDetailsDAO.save(new ItemDetails(i.getItemCode(),i.getOrderId(),i.getOrderQTY(),i.getUnitPrice()))){
                    con.rollback();
                    con.setAutoCommit(true);
                    return false;
                }
                if (!itemDAO.updateQty(i.getItemCode(),i.getOrderQTY())){
                    con.rollback();
                    con.setAutoCommit(true);
                    return false;
                }
            }
        }
        con.commit();
        con.setAutoCommit(true);
        return true;
    }

    @Override
    public String generateNewOrderId() throws SQLException, ClassNotFoundException {
        return orderDAO.genarateNewId();
    }

    @Override
    public ArrayList<CustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException {
        ArrayList<CustomerDTO> all = new ArrayList<>();
        ArrayList<Customer> a = customerDAO.getAll();
        for (Customer e: a) {
            all.add(new CustomerDTO(e.getId(),e.getTitle(),e.getName(),e.getAddress(),e.getCity(),e.getProvince(),e.getPostalCode()
                 ));
        }
        return all;
    }

    @Override
    public ArrayList<ItemDTO> getAllItems() throws SQLException, ClassNotFoundException {
        ArrayList<ItemDTO> allItems = new ArrayList<>();
        ArrayList<Item> all = itemDAO.getAll();
        for (Item e : all) {
            allItems.add(new ItemDTO(e.getCode(),e.getDescription(),e.getPackSize(),e.getUnitPrice(),e.getQtyOnHand()));
        }
        return allItems;
    }

    @Override
    public ItemDTO searchItem(String code) throws SQLException, ClassNotFoundException {
        Item e = itemDAO.search(code);
        return new ItemDTO(e.getCode(),e.getDescription(),e.getPackSize(),e.getUnitPrice(),e.getQtyOnHand());
    }

    @Override
    public boolean ifItemExist(String code) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean ifCustomerExist(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public CustomerDTO searchCustomer(String s) throws SQLException, ClassNotFoundException {
        Customer e = customerDAO.search(s);
        return new CustomerDTO(e.getId(),e.getTitle(),e.getName(),e.getAddress(),e.getProvince(),
                e.getCity(),e.getPostalCode());
    }

}
