package fa.training.dao;

import fa.training.entities.Order;

import java.util.List;

public interface OrderDAO extends CommonDAO<Order> {
    List<Order> findByCustomerId(int customerId);
    Double computeOrderTotal(int orderId);
    boolean updateOrderTotal(int orderId);
}
