package fa.training.service;

import fa.training.dao.CustomerDAO;
import fa.training.dao.EmployeeDAO;
import fa.training.dao.OrderDAO;
import fa.training.dao.impl.CustomerDAOImpl;
import fa.training.dao.impl.EmployeeDAOImpl;
import fa.training.dao.impl.OrderDAOImpl;
import fa.training.entities.Order;

import java.util.List;

public class OrderService {
    private final OrderDAO orderDAO;
    private final CustomerDAO customerDAO;
    private final EmployeeDAO employeeDAO;
    public OrderService(){
        orderDAO = new OrderDAOImpl();
        customerDAO = new CustomerDAOImpl();
        employeeDAO = new EmployeeDAOImpl();
    }
    public boolean addOrder(Order order){
        if(customerDAO.findById(order.getCustomerId()) == null) {
            return false;
        }
        if(employeeDAO.findById(order.getEmployeeId()) == null) {
            return false;
        }
        return orderDAO.save(order);
    }
    public boolean updateOrderTotal(int id){
        return orderDAO.updateOrderTotal(id);
    }
    public List<Order> getAllOrdersByCustomerId(int customerId){
        return orderDAO.findByCustomerId(customerId);
    }
    public Double computeOrderTotal(int orderId){
        return orderDAO.computeOrderTotal(orderId);
    }
    public Order getOrderById(int id){
        return orderDAO.findById(id);
    }

}
