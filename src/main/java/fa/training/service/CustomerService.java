package fa.training.service;

import fa.training.dao.CustomerDAO;
import fa.training.dao.impl.CustomerDAOImpl;
import fa.training.entities.Customer;

import java.util.List;

public class CustomerService {
    private final CustomerDAO customerDAO;
    public CustomerService() {
        customerDAO = new CustomerDAOImpl();
    }
    public boolean addCustomer(Customer customer) {
        return customerDAO.save(customer);
    }
    public boolean updateCustomer(Customer customer) {
        return customerDAO.update(customer);
    }
    public boolean deleteCustomer(int customerId) {
        return customerDAO.delete(customerId);
    }
    public Customer getCustomer(int id) {
        return customerDAO.findById(id);
    }
    public List<Customer> getAllCustomers() {
        return customerDAO.findAll();
    }
}
