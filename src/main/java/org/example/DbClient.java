package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbClient {
    private static final String path_To_Database = "jdbc:h2:./src/main/java/db/carsharing";
    private Connection connection;
    private Statement statement;

    public DbClient(String driver){
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(path_To_Database);
            statement = connection.createStatement();
            connection.setAutoCommit(true);
        }catch(ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }
    }

    public void run(String str) {
        try{
            statement.executeUpdate(str);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public Company selectCompany(String query){
        List<Company> companies = selectForListCompany(query);
        if(companies.size() == 1){
            return companies.get(0);
        }else if(companies.isEmpty()){
            return null;
        }else{
            throw new IllegalStateException("Query return more than one object");
        }
    }
    public Car selectCar(String query){
        List<Car> cars = selectForListCars(query);
        if(cars.size() == 1){
            return cars.get(0);
        }else if(cars.isEmpty()){
            return null;
        }else{
            throw new IllegalStateException("Query return more than one object");
        }
    }

    public List<Car> selectForListCars(String query) {
        List<Car> cars = new ArrayList<>();
        try(ResultSet resultSetItem = statement.executeQuery(query)){
            while(resultSetItem.next() ){
                //Retrieve column values
                int id = resultSetItem.getInt("id");
                String name = resultSetItem.getString("name");
                int company_id = resultSetItem.getInt("COMPANY_ID");
                Car car = new Car(id, name,company_id);
                cars.add(car);
            }
            return cars;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return cars;
    }


    public List<Company> selectForListCompany(String query)  {
        List<Company> companies = new ArrayList<>();
        try(ResultSet resultSetItem = statement.executeQuery(query)){
            while(resultSetItem.next() ){
                //Retrieve column values
                int id = resultSetItem.getInt("id");
                String name = resultSetItem.getString("name");
                Company company = new Company(id, name);
                companies.add(company);
            }
            return companies;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return companies;
    }

    public Customer selectCustomer(String query){
        List<Customer> customers = selectForListCustomer(query);
        if(customers.isEmpty()){
            return null;
        }else{
            return customers.get(0);
        }
    }

    public List<Customer> selectForListCustomer(String query) {
        List<Customer> customers = new ArrayList<>();
        try(ResultSet resultSetItem = statement.executeQuery(query);){
            while(resultSetItem.next()){
                int id = resultSetItem.getInt("ID");
                String name = resultSetItem.getString("NAME");
                int rented_car_id = resultSetItem.getInt("RENTED_CAR_ID");
                Customer customer = new Customer(id, name, rented_car_id);
                customers.add(customer);
            }
            return customers;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return customers;
    }

    public void terminate(){
        try {
            connection.close();
            statement.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}
