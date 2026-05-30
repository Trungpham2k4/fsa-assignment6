package fa.training.dao.impl;

import fa.training.common.ConnectionManager;
import fa.training.dao.ProductDAO;
import fa.training.entities.Product;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOImpl implements ProductDAO {
    @Override
    public boolean save(Product product) {
        String sql = """
                INSERT INTO dbo.product (product_name, list_price) VALUES (?, ?)
                """;
        try(Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, product.getProductName());
            preparedStatement.setBigDecimal(2, BigDecimal.valueOf(product.getListPrice()));
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Product product) {
        String sql = """
                UPDATE dbo.product SET product_name = ?, list_price = ? WHERE product_id = ?
                """;
        try(Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, product.getProductName());
            preparedStatement.setBigDecimal(2, BigDecimal.valueOf(product.getListPrice()));
            preparedStatement.setInt(3, product.getProductId());
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        String sql = """
                DELETE FROM dbo.product WHERE product_id = ?""";
        try(Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Product> findAll() {
        String sql = """
                SELECT * FROM dbo.product""";
        try(Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Product> products = new ArrayList<>();
            while (resultSet.next()) {
                Product product = new Product();
                product.setProductId(resultSet.getInt("product_id"));
                product.setProductName(resultSet.getString("product_name"));
                product.setListPrice(resultSet.getBigDecimal("list_price").doubleValue());
                products.add(product);
            }
            return products;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return List.of();
    }

    @Override
    public Product findById(int id) {
        String sql = """
                SELECT * FROM dbo.product WHERE product_id = ?""";
        try(Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Product product = new Product();
                product.setProductId(resultSet.getInt("product_id"));
                product.setProductName(resultSet.getString("product_name"));
                product.setListPrice(resultSet.getBigDecimal("list_price").doubleValue());
                return product;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
