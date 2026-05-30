package fa.training.dao.impl;

import fa.training.common.ConnectionManager;
import fa.training.dao.OrderDAO;
import fa.training.entities.Order;
import fa.training.entities.Product;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {
    @Override
    public List<Order> findByCustomerId(int customerId) {
        String sql = """
                SELECT * FROM dbo.orders WHERE customer_id = ?
                """;
        try(Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, customerId);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Order> orders = new ArrayList<>();
            while (resultSet.next()) {
                Order order = new Order();
                order.setCustomerId(resultSet.getInt("customer_id"));
                order.setOrderId(resultSet.getInt("order_id"));
                order.setTotal(resultSet.getBigDecimal("total").doubleValue());
                order.setOrderDate(resultSet.getDate("order_date").toLocalDate());
                order.setEmployeeId(resultSet.getInt("employee_id"));
                orders.add(order);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return List.of();
    }

    @Override
    public Double computeOrderTotal(int orderId) {
        String sql = """
                SELECT dbo.compute_order_total(?) AS total_price
                """;
        try(Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, orderId);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getBigDecimal("total_price").doubleValue();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    @Override
    public boolean updateOrderTotal(int orderId) {
        Double totalPrice = computeOrderTotal(orderId);
        String sql = """
                UPDATE dbo.orders
                SET total = ?
                WHERE order_id = ?
                """;
        try(Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setBigDecimal(1, BigDecimal.valueOf(totalPrice));
            preparedStatement.setInt(2, orderId);
            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean save(Order order) {
        String sql = """
                INSERT INTO dbo.orders (order_date, customer_id, employee_id, total)
                VALUES (?, ?, ?, ?)
                """;
        try(Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setDate(1, java.sql.Date.valueOf(order.getOrderDate()));
            preparedStatement.setInt(2, order.getCustomerId());
            preparedStatement.setInt(3, order.getEmployeeId());
            preparedStatement.setBigDecimal(4, BigDecimal.valueOf(order.getTotal()));
            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Order order) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public List<Order> findAll() {
        return List.of();
    }

    @Override
    public Order findById(int id) {
        String sql = """
                SELECT * FROM dbo.orders WHERE order_id = ?""";
        try(Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Order order = new Order();
                order.setOrderId(resultSet.getInt("order_id"));
                order.setCustomerId(resultSet.getInt("customer_id"));
                order.setEmployeeId(resultSet.getInt("employee_id"));
                order.setTotal(resultSet.getBigDecimal("total").doubleValue());
                order.setOrderDate(resultSet.getDate("order_date").toLocalDate());
                return order;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
