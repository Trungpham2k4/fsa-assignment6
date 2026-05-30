package fa.training.dao.impl;

import fa.training.common.ConnectionManager;
import fa.training.dao.LineItemDAO;
import fa.training.entities.LineItem;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class LineItemDAOImpl implements LineItemDAO {
    @Override
    public List<LineItem> findByOrderId(int orderId) {
        String sql = """
                SELECT * FROM dbo.lineitem WHERE order_id = ?;
                """;
        try(Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<LineItem> lineItems = new ArrayList<>();
            while (resultSet.next()) {
                LineItem lineItem = new LineItem();
                lineItem.setOrderId(orderId);
                lineItem.setProductId(resultSet.getInt("product_id"));
                lineItem.setQuantity(resultSet.getInt("quantity"));
                lineItem.setPrice(resultSet.getBigDecimal("price").doubleValue());
                lineItems.add(lineItem);
            }
            return lineItems;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return List.of();
    }

    @Override
    public boolean save(LineItem lineItem) {
        String sql = """
                INSERT INTO dbo.lineitem (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)
                """;
        try(Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, lineItem.getOrderId());
            preparedStatement.setInt(2, lineItem.getProductId());
            preparedStatement.setInt(3, lineItem.getQuantity());
            preparedStatement.setBigDecimal(4, BigDecimal.valueOf(lineItem.getPrice()));
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(LineItem lineItem) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public List<LineItem> findAll() {
        return List.of();
    }

    @Override
    public LineItem findById(int id) {
        return null;
    }
}
