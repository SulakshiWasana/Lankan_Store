package dao.Custom;

import entity.Order;
import dao.CrudDAO;

import java.sql.SQLException;

public interface OrderDAO extends CrudDAO<Order,String> {

    String genarateNewId() throws SQLException, ClassNotFoundException;
}
