package service.custom;

import dto.ItemDTO;
import view.tm.ItemTM;
import service.SuperService;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface ItemService extends SuperService {

    ObservableList<ItemTM> getAllItem()throws SQLException, ClassNotFoundException;

    boolean saveItem(ItemDTO itemDTO) throws SQLException, ClassNotFoundException;

    boolean updateItem(ItemDTO itemDTO) throws SQLException, ClassNotFoundException;

    boolean deleteItem(String id) throws SQLException, ClassNotFoundException;
}
