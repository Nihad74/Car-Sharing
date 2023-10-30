package org.example;

import java.util.List;

public class DbCustomerDao implements CustomerDao{
    private DbClient dbClient;
    private final String JDBC_Driver = "org.h2.Driver";
    private final String createCustomerDB ="CREATE TABLE IF NOT EXISTS CUSTOMER(" +
            "ID INTEGER PRIMARY KEY AUTO_INCREMENT," +
            "NAME VARCHAR(40) UNIQUE NOT NULL," +
            "RENTED_CAR_ID INTEGER," +
            "CONSTRAINT RENTED_CAR FOREIGN KEY (RENTED_CAR_ID)" +
            "REFERENCES CAR(id)" +
            "); ";

    private final String insertData_if_rented_Car_id_exists = "INSERT INTO CUSTOMER(NAME, RENTED_CAR_ID) VALUES" +
            "('%s', %d);";

    private final String insertData_without_car_id = "INSERT INTO CUSTOMER(NAME) VALUES" +
            "('%s');";

    private final String findById = "SELECT * FROM CUSTOMER WHERE ID = %d";

    private final String findAll = "SELECT * FROM CUSTOMER";

    private final String deleteById = "DELETE FROM CUSTOMER WHERE ID = %d ";

    private final String update= "UPDATE CUSTOMER SET NAME = '%s' WHERE ID = %d";
    private final String update_rentedCar_toNull ="UPDATE CUSTOMER SET RENTED_CAR_ID = NULL WHERE ID = %d";

    private final String rentedCars = "SELECT * FROM CAR,CUSTOMER WHERE CAR.ID = CUSTOMER.RENTED_CAR_ID";

    private final String controlRentedCar = "SELECT * FROM CUSTOMER WHERE ID = %d";
    private final String update_rentedCar ="UPDATE CUSTOMER SET RENTED_CAR_ID = %d WHERE ID = %d";


    public DbCustomerDao(){
        dbClient = new DbClient(JDBC_Driver);
        dbClient.run(createCustomerDB);
    }

    @Override
    public void add(Customer customer) {
        if(customer.getRented_car_id() == 0){
            dbClient.run(String.format(insertData_without_car_id, customer.getName()));
        }else{
            dbClient.run(String.format(insertData_if_rented_Car_id_exists, customer.getName(),
                    customer.getRented_car_id()));
        }
    }

    @Override
    public Customer findById(int id) {
        return dbClient.selectCustomer(String.format(findById, id));
    }

    @Override
    public List<Customer> findAll() {
        return dbClient.selectForListCustomer(findAll);
    }

    @Override
    public void deleteById(int id) {
        dbClient.run(String.format(deleteById, id));
    }

    @Override
    public void update(Customer customer) {
        dbClient.run(String.format(update, customer.getName(), customer.getId()));
    }

    public List<Car> rentedCars(int id){
        return dbClient.selectForListCars(String.format(rentedCars));
    }

    public void updateRentedCar_ToNull(int id){
        dbClient.run(String.format(update_rentedCar_toNull, id));
    }

    @Override
    public void updateRentedCar(int rented_car_id, int id) {
        dbClient.run(String.format(update_rentedCar,rented_car_id, id));
    }

    @Override
    public Customer controlRentedCar(int id) {
        return dbClient.selectCustomer(String.format(controlRentedCar, id));
    }
}
