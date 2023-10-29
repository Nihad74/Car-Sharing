package org.example;

import java.util.List;

public interface CarDao {
    public void add(Car car);

    public void update(Car car);

    public Car findById(int id);
    public List<Car> findAll();
    public void deleteById(int id);

    public List<Car> findByCompanyID(int id);
}
