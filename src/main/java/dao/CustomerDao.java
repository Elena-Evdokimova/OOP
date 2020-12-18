package dao;

import entity.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDao {

    private static final String CREATE = "insert into customer(customer_name, bank_requisites, c_phone_number, contact_person) values (?, ?, ?, ?);";
    private static final String DELETE_BY_NAME = "delete from customer where customer_name = ?;";
    private static final String GET_BY_NAME = "select * from customer where customer_name = ?;";
    private static final String GET_ALL = "select * from customer;";

    private static final String NAME = "customer_name";
    private static final String REQUISITES = "bank_requisites";
    private static final String PHONE = "c_phone_number";
    private static final String PERSON = "contact_person";

    private ConnectionBuilder connectionBuilder;

    public void setConnectionBuilder(ConnectionBuilder connectionBuilder) {
        this.connectionBuilder = connectionBuilder;
    }

    private Connection getConnection() throws SQLException {
        return connectionBuilder.getConnection();
    }

    public List<Customer> getAll(){
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL)){
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                List<Customer> customers = new ArrayList<>();
                do {
                    Customer customer = new Customer();
                    customer.setCustomerName(resultSet.getString(NAME));
                    customer.setBankRequisites(resultSet.getString(REQUISITES));
                    customer.setCustomerName(resultSet.getString(PHONE));
                    customer.setContactPerson(resultSet.getString(PERSON));
                    customers.add(customer);
                }while (resultSet.next());
                return customers;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return new ArrayList<>();
    }

    public Customer getByName(String name){
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_NAME)){
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                Customer customer = new Customer();
                customer.setCustomerName(resultSet.getString(NAME));
                customer.setBankRequisites(resultSet.getString(REQUISITES));
                customer.setCustomerName(resultSet.getString(PHONE));
                customer.setContactPerson(resultSet.getString(PERSON));
                return customer;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return new Customer();
    }

    public int delete(String name){
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_NAME)){
            preparedStatement.setString(1, name);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    public int create(Customer customer){
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE)){
            int counter = 1;
            preparedStatement.setString(counter++, customer.getCustomerName());
            preparedStatement.setString(counter++, customer.getBankRequisites());
            preparedStatement.setString(counter++, customer.getCustomerPhoneNumber());
            preparedStatement.setString(counter, customer.getContactPerson());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

}
