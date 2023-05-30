package service.custom;

import dto.CustomerDTO;
import view.tm.CustomerTM;
import service.SuperService;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface CustomerService extends SuperService {

    ObservableList<CustomerTM> getAllCustomer()throws SQLException, ClassNotFoundException;

    boolean saveCustomer(CustomerDTO customerDTO) throws SQLException, ClassNotFoundException;

    boolean updateCustomer(CustomerDTO customerDTO) throws SQLException, ClassNotFoundException;

    boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException;
}
