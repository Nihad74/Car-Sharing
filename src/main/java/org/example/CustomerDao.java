package org.example;

import java.util.List;

public interface CustomerDao {
    public void add(Customer customer);

    public Customer findById(int id);

    public List<Customer> findAll();

    public void deleteById(int id);

    public void update(Customer customer);

    public List<Car> rentedCars(int id);

    public void updateRentedCar_ToNull(int id);
    public void updateRentedCar(int company_id, int id);

    public Customer controlRentedCar(int id);
}
