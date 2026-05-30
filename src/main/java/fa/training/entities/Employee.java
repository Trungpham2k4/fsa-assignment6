package fa.training.entities;

public class Employee {
    private int employeeId;
    private String employeeName;
    private double salary;
    private int sprvId;

    public Employee() {}

    public Employee(int employeeId, String employeeName, double salary, int sprvId) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.salary = salary;
        this.sprvId = sprvId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public int getSprvId() {
        return sprvId;
    }

    public void setSprvId(int sprvId) {
        this.sprvId = sprvId;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeId=" + employeeId +
                ", employeeName='" + employeeName + '\'' +
                ", salary=" + salary +
                ", sprvId=" + sprvId +
                '}';
    }
}
