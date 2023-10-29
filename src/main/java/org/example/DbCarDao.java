package org.example;

import java.util.List;

public class DbCarDao implements CarDao{
    private static final String JDBC_Driver = "org.h2.Driver";

    private static final String create_CarDB = "CREATE TABLE IF NOT EXISTS CAR(" +
            "ID INTEGER PRIMARY KEY AUTO_INCREMENT," +
            "NAME VARCHAR(40) UNIQUE NOT NULL," +
            "COMPANY_ID INTEGER NOT NULL," +
            "CONSTRAINT COMPANY FOREIGN KEY (COMPANY_ID)" +
            "REFERENCES COMPANY(id)"+
            ");";

    private static final String insertCar ="INSERT INTO CAR(NAME,COMPANY_ID)  VALUES('%s', %d);";

    private static String updateCar = "UPDATE CAR SET NAME = '%s' WHERE ID = %d;";

    private static String findById = "SELECT  * FROM CAR WHERE ID = %d";
    private static String select_all ="SELECT* FROM CAR";

    private static String deleteById = "DELETE FROM CAR WHERE ID = %d";

    private static String findByCompanyId = "SELECT * FROM CAR WHERE COMPANY_ID = %d";


    private DbClient dbClient;
    public DbCarDao(){
        dbClient = new DbClient(JDBC_Driver);
        dbClient.run(create_CarDB);
    }
    @Override
    public void add(Car car) {
        dbClient.run(String.format(insertCar, car.getName(), car.getCompany_id()));
    }

    @Override
    public void update(Car car) {
        dbClient.run(String.format(updateCar, car.getName(), car.getId()));
    }

    @Override
    public Car findById(int id) {
        return dbClient.selectCar(String.format(findById, id));
    }

    @Override
    public List<Car> findAll() {
        return dbClient.selectForListCars(select_all);
    }

    @Override
    public void deleteById(int id) {
        dbClient.run(String.format(deleteById, id));
    }

    @Override
    public List<Car> findByCompanyID(int id) {
        return dbClient.selectForListCars(String.format(findByCompanyId, id));
    }
}
