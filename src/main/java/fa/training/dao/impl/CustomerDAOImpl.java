package fa.training.dao.impl;

import fa.training.common.ConnectionManager;
import fa.training.dao.CustomerDAO;
import fa.training.entities.Customer;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {
    @Override
    public boolean save(Customer customer) {
        String sql = """
                CALL dbo.insert_customer(?);
                """;
        try(Connection connection = ConnectionManager.getConnection();
            CallableStatement callableStatement = connection.prepareCall(sql)) {
            callableStatement.setString(1, customer.getCustomerName());
            callableStatement.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Customer customer) {
        String sql = """
                CALL dbo.update_customer(?, ?);
                """;
        try(Connection connection = ConnectionManager.getConnection();
            CallableStatement callableStatement = connection.prepareCall(sql)) {
            callableStatement.setInt(1, customer.getCustomerId());
            callableStatement.setString(2, customer.getCustomerName());
            callableStatement.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(int customerId) {
        String sql = """
                CALL dbo.delete_customer(?);
                """;
        try(Connection connection = ConnectionManager.getConnection();
            CallableStatement callableStatement = connection.prepareCall(sql)) {
            callableStatement.setInt(1, customerId);
            callableStatement.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Customer> findAll() {
        String sql = """
                SELECT * FROM dbo.customer
                """;
        try(Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Customer> customers = new ArrayList<>();
            while(resultSet.next()) {
                Customer customer = new Customer();
                customer.setCustomerId(resultSet.getInt("customer_id"));
                customer.setCustomerName(resultSet.getString("customer_name"));
                customers.add(customer);
            }
            return customers;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return List.of();
    }

    @Override
    public Customer findById(int id) {
        String sql = """
                SELECT * FROM dbo.customer WHERE customer_id = ?
                """;
        try(Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                Customer customer = new Customer();
                customer.setCustomerId(resultSet.getInt("customer_id"));
                customer.setCustomerName(resultSet.getString("customer_name"));
                return customer;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
