package fa.training.service;

import fa.training.dao.OrderDAO;
import fa.training.dao.impl.OrderDAOImpl;
import fa.training.entities.Order;

import java.util.List;

public class OrderService {
    private final OrderDAO orderDAO;
    public OrderService(){
        orderDAO = new OrderDAOImpl();
    }
    public boolean addOrder(Order order){
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
