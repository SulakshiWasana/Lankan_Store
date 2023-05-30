package service.custom.impl;

import entity.Item;
import dto.ItemDTO;
import view.tm.ItemTM;
import service.custom.ItemService;
import dao.Custom.ItemDAO;
import dao.DAOFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.ArrayList;

public class ItemServiceImpl implements ItemService {

    private final ItemDAO itemDAO = (ItemDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ITEM);

    @Override
    public ObservableList<ItemTM> getAllItem() throws SQLException, ClassNotFoundException {
        ArrayList<Item> allItemDAO=itemDAO.getAll();
        ObservableList<ItemTM> observableList= FXCollections.observableArrayList();
        allItemDAO.forEach(e->observableList.addAll(new ItemTM(
                e.getCode(),e.getDescription(),e.getPackSize(),e.getUnitPrice(),e.getQtyOnHand())));
        return observableList;
    }

    @Override
    public boolean saveItem(ItemDTO itemDTO) throws SQLException, ClassNotFoundException {
        return itemDAO.save(new Item(itemDTO.getCode(),itemDTO.getDescription(),itemDTO.getPackSize(),itemDTO.getUnitPrice(),itemDTO.getQtyOnHand()));
    }

    @Override
    public boolean updateItem(ItemDTO itemDTO) throws SQLException, ClassNotFoundException {
        return itemDAO.update(new Item(itemDTO.getCode(),itemDTO.getDescription(),itemDTO.getPackSize(),itemDTO.getUnitPrice(),itemDTO.getQtyOnHand()));
    }

    @Override
    public boolean deleteItem(String id) throws SQLException, ClassNotFoundException {
        return itemDAO.delete(id);
    }
}
