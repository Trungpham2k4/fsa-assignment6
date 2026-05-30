package fa.training.main;

import fa.training.common.Validator;
import fa.training.entities.Customer;
import fa.training.entities.LineItem;
import fa.training.entities.Order;
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

    public static void showMenu(){
        String menu = """
                ===== Sales Management System =====
                Choose one of the following options:
                0. Exit
                1. List all customers
                2. List all orders by customer id
                3. List all line items by order id
                4. Compute order total
                5. Add a customer
                6. Delete a customer
                7. Update a customer
                8. Create an order
                9. Create a line item
                10. Update an order total
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
            choice = inputValidOption(0, 10);
            switch (choice){
                case 1 -> displayAllCustomers();
                case 2 -> displayAllOrdersByCustomerId();
                case 3 -> displayAllLineItemsByOrderId();
                case 4 -> computeOrderTotal();
                case 5 -> createCustomer();
                case 6 -> deleteCustomer();
                case 7 -> updateCustomer();
                case 8 -> createOrder();
                case 9 -> createLineItem();
                case 10 -> updateOrderTotal();
            }
        }while (choice != 0);
    }

    private static void displayAllCustomers(){
        delimiter();
        System.out.println("Display all customers");
        delimiter();
        List<Customer> customers = customerService.getAllCustomers();
        customers.forEach(System.out::println);
    }

    private static void displayAllOrdersByCustomerId(){
        delimiter();
        System.out.println("Display all orders");
        delimiter();
        int customerId = inputValidIntField("Enter customer id: ", "Customer id must be a positive integer", Validator::isValidPositiveNumber);
        List<Order> orders = orderService.getAllOrdersByCustomerId(customerId);
        orders.forEach(System.out::println);
    }

    private static void displayAllLineItemsByOrderId(){
        delimiter();
        System.out.println("Display all line items");
        delimiter();
        int orderId = inputValidIntField("Enter order id: ", "Order id must be a positive integer", Validator::isValidPositiveNumber);
        lineItemService.getAllItemsByOrderId(orderId).forEach(System.out::println);
    }

    private static void computeOrderTotal(){
        delimiter();
        System.out.println("Compute order total");
        delimiter();
        int orderId = inputValidIntField("Enter order id: ", "Order id must be a positive integer", Validator::isValidPositiveNumber);
        double total = orderService.computeOrderTotal(orderId);
        System.out.println("Total for order id " + orderId + " is: " + total);
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