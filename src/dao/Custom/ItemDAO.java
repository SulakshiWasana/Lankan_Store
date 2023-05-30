package dao.Custom;

import entity.Item;
import dao.CrudDAO;

import java.sql.SQLException;

public interface ItemDAO extends CrudDAO<Item,String> {

    boolean updateQty(String id , int qty)throws SQLException, ClassNotFoundException;
}
