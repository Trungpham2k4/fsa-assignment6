package fa.training.main;

import fa.training.common.Validator;
import fa.training.entities.*;
import fa.training.service.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

import static fa.training.common.Constants.DATE_FORMATTER;
import static fa.training.common.Constants.DELIMITER;

public class SaleManagement {

    private static final Scanner scanner = new Scanner(System.in);
    private static final CustomerService customerService = new CustomerService();
    private static final EmployeeService employeeService = new EmployeeService();
    private static final LineItemService lineItemService = new LineItemService();
    private static final OrderService orderService = new OrderService();
    private static final ProductService productService = new ProductService();


    public static void showCustomerMenu(){
        String menu = """
                ===== Customer Menu =====
                Choose one of the following options:
                1. Add a customer
                2. Update a customer
                3. Delete a customer
                4. Show all customers
                """;
        System.out.print(menu);
    }
    public static void showEmployeeMenu(){
        String menu = """
                ===== Employee Menu =====
                Choose one of the following options:
                1. Add an employee
                2. Show all employees
                3. Update an employee
                4. Delete an employee
                """;
        System.out.print(menu);
    }
    public static void showLineItemMenu(){
        String menu = """
                ===== Line Item Menu =====
                Choose one of the following options:
                1. Add a line item
                2. Show all line items by order id
                """;
        System.out.print(menu);
    }
    public static void showOrderMenu(){
        String menu = """
                ===== Order Menu =====
                Choose one of the following options:
                1. Create an order
                2. Show all orders by customer id
                3. Compute order total
                4. Update order total
                """;
        System.out.print(menu);
    }
    public static void showProductMenu(){
        String menu = """
                ===== Product Menu =====
                Choose one of the following options:
                1. Add a product
                2. Show all products
                3. Update a product
                4. Delete a product
                """;
        System.out.print(menu);
    }

    public static void showMenu(){
        String menu = """
                ===== Sales Management System =====
                Choose one of the following options:
                0. Exit
                1. Customer
                2. Employee
                3. Line Item
                4. Order
                5. Product
                """;
        System.out.print(menu);
    }

    public static void main(String[] args) {
        start();
    }

    public static void start(){
        int choice;
        do{
            showMenu();
            System.out.print("Enter choice: ");
            choice = inputValidOption(0, 5);
            switch (choice){
                case 1 -> handleCustomerMenu();
                case 2 -> handleEmployeeMenu();
                case 3 -> handleLineItemMenu();
                case 4 -> handleOrderMenu();
                case 5 -> handleProductMenu();
            }
        }while (choice != 0);
    }

    public static void handleCustomerMenu(){
        showCustomerMenu();
        int choice = inputValidOption(1,4);
        switch (choice){
            case 1 -> createCustomer();
            case 2 -> updateCustomer();
            case 3 -> deleteCustomer();
            case 4 -> displayAllCustomers();
        }
    }

    public static void handleEmployeeMenu(){
        showEmployeeMenu();
        int choice = inputValidOption(1,4);
        switch (choice){
            case 1 -> addEmployee();
            case 2 -> displayAllEmployees();
            case 3 -> updateEmployee();
            case 4 -> deleteEmployee();
        }
    }

    public static void handleLineItemMenu(){
        showLineItemMenu();
        int choice = inputValidOption(1,2);
        switch (choice){
            case 1 -> createLineItem();
            case 2 -> displayAllLineItemsByOrderId();
        }
    }

    public static void handleOrderMenu(){
        showOrderMenu();
        int choice = inputValidOption(1,4);
        switch (choice){
            case 1 -> createOrder();
            case 2 -> displayAllOrdersByCustomerId();
            case 3 -> computeOrderTotal();
            case 4 -> updateOrderTotal();
        }
    }

    public static void handleProductMenu(){
        showProductMenu();
        int choice = inputValidOption(1,4);
        switch (choice){
            case 1 -> createProduct();
            case 2 -> displayAllProducts();
            case 3 -> updateProduct();
            case 4 -> deleteProduct();
        }
    }

    private static void createCustomer() {
        delimiter();
        System.out.println("Create a new customer");
        delimiter();
        String customerName = inputValidStringField("Enter customer name: ", "Customer name cannot be blank", Validator::isNotBlank);
        Customer customer = new Customer();
        customer.setCustomerName(customerName);
        if(customerService.addCustomer(customer)){
            System.out.println("Customer " + customerName + " created");
        }else{
            System.out.println("Customer " + customerName + " could not be created");
        }
    }

    private static void deleteCustomer() {
        delimiter();
        System.out.println("Delete a customer");
        delimiter();
        int customerId = inputValidIntField("Enter customer id: ", "Customer id must be a positive integer", Validator::isValidPositiveNumber);
        if(customerService.deleteCustomer(customerId)){
            System.out.println("Customer with id " + customerId + " deleted");
        }else {
            System.out.println("Customer with id " + customerId + " could not be deleted");
        }
    }

    private static void updateCustomer() {
        delimiter();
        System.out.println("Update a customer");
        delimiter();
        int customerId = inputValidIntField("Enter customer id: ", "Customer id must be a positive integer", Validator::isValidPositiveNumber);
        Customer existingCustomer = customerService.getCustomer(customerId);
        if(existingCustomer == null) {
            System.out.println("Customer with id " + customerId + " not found");
            return;
        }
        String updateName = inputValidStringField("Enter new customer name: ", "Customer name cannot be blank", Validator::isNotBlank);
        existingCustomer.setCustomerName(updateName);
        if(customerService.updateCustomer(existingCustomer)){
            System.out.println("Customer with id " + customerId + " updated");
        }else {
            System.out.println("Customer with id " + customerId + " could not be updated");
        }
    }

    private static void displayAllCustomers(){
        delimiter();
        System.out.println("Display all customers");
        delimiter();
        List<Customer> customers = customerService.getAllCustomers();
        customers.forEach(System.out::println);
    }

    private static void addEmployee(){
        delimiter();
        System.out.println("Add a new employee");
        delimiter();
        String employeeName = inputValidStringField("Enter employee name: ", "Employee name cannot be blank", Validator::isNotBlank);
        double salary = inputValidDoubleField("Enter salary: ", "Salary must be a positive number", Validator::isValidPositiveNumber);
        int sprvId = inputValidIntField("Enter supervisor id: ", "Supervisor id must be a positive integer", Validator::isValidPositiveNumber);
        Employee employee = new Employee();
        employee.setEmployeeName(employeeName);
        employee.setSalary(salary);
        employee.setSprvId(sprvId);
        if(employeeService.addEmployee(employee)){
            System.out.println("Employee " + employeeName + " added");
        }else{
            System.out.println("Employee " + employeeName + " could not be added");
        }
    }

    private static void updateEmployee(){
        delimiter();
        System.out.println("Update a employee");
        delimiter();
        int employeeId = inputValidIntField("Enter employee id: ", "Employee id must be a positive integer", Validator::isValidPositiveNumber);
        Employee existingEmployee = employeeService.getEmployee(employeeId);
        if(existingEmployee == null) {
            System.out.println("Employee with id " + employeeId + " not found");
            return;
        }
        String employeeName = inputValidStringField("Enter employee name: ", "Employee name cannot be blank", Validator::isNotBlank);
        double salary = inputValidDoubleField("Enter salary: ", "Salary must be a positive) number", Validator::isValidPositiveNumber);
        int sprvId = inputValidIntField("Enter supervisor id: ", "Supervisor id must be a positive integer", Validator::isValidPositiveNumber);
        existingEmployee.setEmployeeName(employeeName);
        existingEmployee.setSalary(salary);
        existingEmployee.setSprvId(sprvId);
        if(employeeService.updateEmployee(existingEmployee)){
            System.out.println("Employee " + employeeName + " updated");
        }else {
            System.out.println("Employee " + employeeName + " could not be updated");
        }
    }

    private static void deleteEmployee(){
        delimiter();
        System.out.println("Delete a employee");
        delimiter();
        int employeeId = inputValidIntField("Enter employee id: ", "Employee id must be a positive integer", Validator::isValidPositiveNumber);
        if(employeeService.deleteEmployee(employeeId)){
            System.out.println("Employee with id " + employeeId + " deleted");
        }else {
            System.out.println("Employee with id " + employeeId + " could not be deleted");
        }
    }

    private static void displayAllEmployees(){
        delimiter();
        System.out.println("Display all employees");
        delimiter();
        List<Employee> employees = employeeService.getAllEmployees();
        employees.forEach(System.out::println);
    }

    private static void createLineItem(){
        delimiter();
        System.out.println("Create a new line item");
        delimiter();
        int orderId = inputValidIntField("Enter order id: ", "Order id must be a positive integer", Validator::isValidPositiveNumber);
        int productId = inputValidIntField("Enter product id: ", "Product id must be a positive integer", Validator::isValidPositiveNumber);
        int quantity = inputValidIntField("Enter quantity: ", "Quantity must be a positive integer", Validator::isValidPositiveNumber);
        double price = inputValidDoubleField("Enter price: ", "Price must be a positive number", Validator::isValidPositiveNumber);
        LineItem lineItem = new LineItem(orderId, productId, quantity, price);
        if(lineItemService.addLineItem(lineItem)){
            System.out.println("Line item created");
        }else {
            System.out.println("Line item could not be created");
        }
    }

    private static void displayAllLineItemsByOrderId(){
        delimiter();
        System.out.println("Display all line items");
        delimiter();
        int orderId = inputValidIntField("Enter order id: ", "Order id must be a positive integer", Validator::isValidPositiveNumber);
        lineItemService.getAllItemsByOrderId(orderId).forEach(System.out::println);
    }

    private static void createOrder(){
        delimiter();
        System.out.println("Create a new order");
        delimiter();
        int customerId = inputValidIntField("Enter customer id: ", "Customer id must be a positive integer", Validator::isValidPositiveNumber);
        int employeeId = inputValidIntField("Enter employee id: ", "Employee id must be a positive integer", Validator::isValidPositiveNumber);
        LocalDate orderDate = LocalDate.parse(inputValidStringField("Enter order date (YYYY-MM-DD): ", "Invalid date format. Please enter in YYYY-MM-DD format", Validator::isValidDate), DATE_FORMATTER);
        double total = 0.0;
        Order order = new Order();
        order.setCustomerId(customerId);
        order.setEmployeeId(employeeId);
        order.setOrderDate(orderDate);
        order.setTotal(total);
        if(orderService.addOrder(order)){
            System.out.println("Order created");
        }else {
            System.out.println("Order could not be created");
        }
    }

    private static void displayAllOrdersByCustomerId(){
        delimiter();
        System.out.println("Display all orders");
        delimiter();
        int customerId = inputValidIntField("Enter customer id: ", "Customer id must be a positive integer", Validator::isValidPositiveNumber);
        List<Order> orders = orderService.getAllOrdersByCustomerId(customerId);
        orders.forEach(System.out::println);
    }

    private static void computeOrderTotal(){
        delimiter();
        System.out.println("Compute order total");
        delimiter();
        int orderId = inputValidIntField("Enter order id: ", "Order id must be a positive integer", Validator::isValidPositiveNumber);
        double total = orderService.computeOrderTotal(orderId);
        System.out.println("Total for order id " + orderId + " is: " + total);
    }

    private static void updateOrderTotal(){
        delimiter();
        System.out.println("Update order total");
        delimiter();
        int orderId = inputValidIntField("Enter order id: ", "Order id must be a positive integer", Validator::isValidPositiveNumber);
        if(orderService.updateOrderTotal(orderId)){
            Order order = orderService.getOrderById(orderId);
            System.out.println("Order total updated to " + order.getTotal());
        }else {
            System.out.println("Order total could not be updated");
        }
    }

    private static void createProduct(){
        delimiter();
        System.out.println("Create a new product");
        delimiter();
        String productName = inputValidStringField("Enter product name: ", "Product name cannot be blank", Validator::isNotBlank);
        double listPrice = inputValidDoubleField("Enter list price: ", "List price must be a positive number", Validator::isValidPositiveNumber);
        Product product = new Product();
        product.setProductName(productName);
        product.setListPrice(listPrice);
        if(productService.addProduct(product)){
            System.out.println("Product created");
        }else{
            System.out.println("Product could not be created");
        }
    }

    private static void displayAllProducts(){
        delimiter();
        System.out.println("Display all products");
        delimiter();
        List<Product> products = productService.getAllProducts();
        products.forEach(System.out::println);
    }

    private static void updateProduct(){
        delimiter();
        System.out.println("Update product");
        delimiter();
        int productId = inputValidIntField("Enter product id: ", "Product id must be a positive integer", Validator::isValidPositiveNumber);
        Product existingProduct = productService.getProductById(productId);
        if(existingProduct == null){
            System.out.println("Product could not be found");
            return;
        }
        String productName = inputValidStringField("Enter product name: ", "Product name cannot be blank", Validator::isNotBlank);
        double listPrice = inputValidDoubleField("Enter list price: ", "List price must) be a positive number", Validator::isValidPositiveNumber);
        existingProduct.setProductName(productName);
        existingProduct.setListPrice(listPrice);
        if(productService.updateProduct(existingProduct)){
            System.out.println("Product updated");
        }else {
            System.out.println("Product could not be updated");
        }
    }

    private static void deleteProduct(){
        delimiter();
        System.out.println("Delete a product");
        delimiter();
        int productId = inputValidIntField("Enter product id: ", "Product id must be a positive integer", Validator::isValidPositiveNumber);
        if(productService.deleteProduct(productId)){
            System.out.println("Product with id " + productId + " deleted");
        }else {
            System.out.println("Product with id " + productId + " could not be deleted");
        }
    }

    private static void delimiter(){
        System.out.println(DELIMITER);
    }


    private static int inputValidOption(int min, int max){
        while(true){
            int option = getIntInput("Please input an option from " + min + " to " + max + ": ");
            if(option < min || option > max){
                System.out.println("Invalid option. Please provide a number between " + min + " and " + max);
            }else{
                return option;
            }
        }
    }

    private static String inputValidStringField(String prompt, String message, Function<String, Boolean> validator){
        while(true){
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if(validator.apply(input)){
                return input;
            }
            System.out.println(message);
        }
    }

    private static double inputValidDoubleField(String prompt, String message, Function<Double, Boolean> validator){
        while(true){
            double input = getDoubleInput(prompt);
            if(validator.apply(input)){
                return input;
            }
            System.out.println(message);
        }
    }

    private static int inputValidIntField(String prompt, String message, Function<Integer, Boolean> validator){
        while(true){
            int input = getIntInput(prompt);
            if(validator.apply(input)){
                return input;
            }
            System.out.println(message);
        }
    }

    private static int getIntInput(String prompt){
        while(true){
            try{
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine());
            }catch (NumberFormatException e){
                System.out.println("Please enter an integer");
            }
        }
    }

    private static double getDoubleInput(String prompt){
        while(true){
            try{
                System.out.print(prompt);
                return Double.parseDouble(scanner.nextLine());
            }catch (NumberFormatException e){
                System.out.println("Please enter a double value");
            }
        }
    }
}