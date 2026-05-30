package fa.training.service;

import fa.training.dao.EmployeeDAO;
import fa.training.dao.impl.EmployeeDAOImpl;
import fa.training.entities.Employee;

import java.util.List;

public class EmployeeService {
    private final EmployeeDAO employeeDAO;
    public EmployeeService(){
        employeeDAO = new EmployeeDAOImpl();
    }
    public boolean addEmployee(Employee employee){
        return employeeDAO.save(employee);
    }
    public boolean updateEmployee(Employee employee){
        return employeeDAO.update(employee);
    }
    public boolean deleteEmployee(int id){
        return employeeDAO.delete(id);
    }
    public Employee getEmployee(int id){
        return employeeDAO.findById(id);
    }
    public List<Employee> getAllEmployees(){
        return employeeDAO.findAll();
    }
}
