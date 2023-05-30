package service.custom.impl;

import entity.Customer;
import dto.CustomerDTO;
import view.tm.CustomerTM;
import service.custom.CustomerService;
import dao.Custom.CustomerDAO;
import dao.DAOFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerServiceImpl implements CustomerService {

    private final CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);

    @Override
    public ObservableList<CustomerTM> getAllCustomer() throws SQLException, ClassNotFoundException {
        ArrayList<Customer> allCustomerDAO = customerDAO.getAll();
        ObservableList<CustomerTM> observableList = FXCollections.observableArrayList();
        allCustomerDAO.forEach(e -> observableList.addAll(new CustomerTM(
                e.getId(), e.getTitle(), e.getName(), e.getAddress(), e.getCity(), e.getProvince(), e.getPostalCode())));
        return observableList;
    }

    @Override
    public boolean saveCustomer(CustomerDTO customerDTO) throws SQLException, ClassNotFoundException {
        return customerDAO.save(new Customer(customerDTO.getId(), customerDTO.getTitle(), customerDTO.getName(), customerDTO.getAddress(), customerDTO.getCity(), customerDTO.getProvince(), customerDTO.getPostalCode()));
    }

    @Override
    public boolean updateCustomer(CustomerDTO customerDTO) throws SQLException, ClassNotFoundException {
        return customerDAO.update(new Customer(customerDTO.getId(), customerDTO.getTitle(), customerDTO.getName(), customerDTO.getAddress(), customerDTO.getCity(), customerDTO.getProvince(), customerDTO.getPostalCode()));
    }

    @Override
    public boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException {
        return customerDAO.delete(id);
    }
}