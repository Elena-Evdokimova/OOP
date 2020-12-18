package main.task.three;

import dao.ConnectionBuilderImpl;
import dao.CustomerDao;
import entity.Customer;

import java.util.List;

public class MainCustomer {

    private static final String NAME = "some";

    private static CustomerDao customerDao;

    public static void main(String[] args) {
        getAll();
        getByName();

        Customer customer = new Customer();
        customer.setCustomerName("Z-corp");
        customer.setBankRequisites("1234-3214-9876-1234");
        customer.setCustomerPhoneNumber("+7-987-912-1234");
        customer.setContactPerson("John");
        createCustomer(customer);
        getAll();
    }

    private static void createCustomer(Customer customer){
        System.out.println("Creating new customer");
        customerDao.setConnectionBuilder(new ConnectionBuilderImpl());
        customerDao.create(customer);
    }

    private static void getAll(){
        System.out.println("Get all customers");
        customerDao = new CustomerDao();
        customerDao.setConnectionBuilder(new ConnectionBuilderImpl());
        List<Customer> customers = customerDao.getAll();
        customers.forEach(System.out::println);
    }

    private static void getByName(){
        System.out.println("Get customer with name: " + NAME);
        customerDao.setConnectionBuilder(new ConnectionBuilderImpl());
        Customer customer = customerDao.getByName(NAME);
        System.out.println(customer);
    }

}
