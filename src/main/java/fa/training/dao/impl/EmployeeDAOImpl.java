package fa.training.dao.impl;

import fa.training.common.ConnectionManager;
import fa.training.dao.EmployeeDAO;
import fa.training.entities.Employee;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAOImpl implements EmployeeDAO {
    @Override
    public boolean save(Employee employee) {
        String sql = """
                INSERT INTO dbo.employee (employee_name, salary, supervisor_id) VALUES (?, ?, ?)
                """;
        try(Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, employee.getEmployeeName());
            preparedStatement.setBigDecimal(2, BigDecimal.valueOf(employee.getSalary()));
            preparedStatement.setInt(3, employee.getSprvId());
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Employee employee) {
        String sql = """
                UPDATE dbo.employee SET employee_name = ?, salary = ?, supervisor_id = ? WHERE employee_id = ?
                """;
        try(Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, employee.getEmployeeName());
            preparedStatement.setBigDecimal(2, BigDecimal.valueOf(employee.getSalary()));
            preparedStatement.setInt(3, employee.getSprvId());
            preparedStatement.setInt(4, employee.getEmployeeId());
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
                DELETE FROM dbo.employee WHERE employee_id = ?""";
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
    public List<Employee> findAll() {
        String sql = """
                SELECT employee_id, employee_name, salary, supervisor_id FROM dbo.employee""";
        try(Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Employee> employees = new ArrayList<>();
            while (resultSet.next()) {
                Employee employee = new Employee();
                employee.setEmployeeId(resultSet.getInt("employee_id"));
                employee.setEmployeeName(resultSet.getString("employee_name"));
                employee.setSalary(resultSet.getBigDecimal("salary").doubleValue());
                employee.setSprvId(resultSet.getInt("supervisor_id"));
                employees.add(employee);
            }
            return employees;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return List.of();
    }

    @Override
    public Employee findById(int id) {
        String sql = """
                SELECT employee_id, employee_name, salary, supervisor_id FROM dbo.employee WHERE employee_id = ?
                """;
        try(Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Employee employee = new Employee();
                employee.setEmployeeId(resultSet.getInt("employee_id"));
                employee.setEmployeeName(resultSet.getString("employee_name"));
                employee.setSalary(resultSet.getBigDecimal("salary").doubleValue());
                employee.setSprvId(resultSet.getInt("supervisor_id"));
                return employee;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
