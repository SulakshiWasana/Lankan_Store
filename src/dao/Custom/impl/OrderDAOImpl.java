package dao.Custom.impl;

import entity.Order;
import dao.CrudUtil;
import dao.Custom.OrderDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDAOImpl implements OrderDAO {


    @Override
    public String genarateNewId() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM `Order` ORDER BY OrderID DESC LIMIT 1");
        if (rst.next()){
            String OrderId=rst.getString("OrderID");
            int newOrderId=Integer.parseInt(OrderId.replace("O-","")) +1;
            return String.format("O-%03d",newOrderId);
        }else {
            return "O-001";
        }
    }

    @Override
    public boolean save(Order order) throws SQLException, ClassNotFoundException {
       return CrudUtil.executeUpdate("INSERT INTO `Order` VALUES (?,?,?,?)",order.getOrderID(),order.getCID(),order.getOrderDate(),order.getCost());
    }

    @Override
    public boolean update(Order order) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String s) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public ArrayList<Order> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public Order search(String s) throws SQLException, ClassNotFoundException {
        return null;
    }
}
