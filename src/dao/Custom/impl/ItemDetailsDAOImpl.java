package dao.Custom.impl;

import entity.ItemDetails;
import dao.CrudUtil;
import dao.Custom.ItemDetailsDAO;

import java.sql.SQLException;
import java.util.ArrayList;

public class ItemDetailsDAOImpl implements ItemDetailsDAO{

    @Override
    public boolean save(ItemDetails i) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("INSERT INTO `Order Detail` VALUES (?,?,?,?)",i.getItemCode(),i.getOrderId(),i.getOrderQTY(),i.getUnitPrice());
    }

    @Override
    public boolean update(ItemDetails itemDetails) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String s) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public ArrayList<ItemDetails> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ItemDetails search(String s) throws SQLException, ClassNotFoundException {
        return null;
    }
}
