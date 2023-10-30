package org.example;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

public class DbCompanyDao implements CompanyDao {
    private static final String JDBC_Driver = "org.h2.Driver";

    private static final String create_CompanyDB = "CREATE TABLE IF NOT EXISTS COMPANY(" +
            "id INTEGER PRIMARY KEY AUTO_INCREMENT," +
            "name VARCHAR(40) UNIQUE NOT NULL" +
            ");";

    private static final String select_all ="SELECT * FROM COMPANY";
    private static final String select_by_ID = "SELECT * FROM COMPANY WHERE id = %d";
    private static final String insertData = "INSERT INTO COMPANY(name) VALUES('%s');";

    private static final String update_data = "UPDATE COMPANY SET name = '%s' WHERE id = %d";
    private static final String delete_data = "DELETE FROM COMPANY WHERE id = %d";

    private final DbClient dbClient;

    public DbCompanyDao(){
        dbClient = new DbClient(JDBC_Driver);
        dbClient.run(create_CompanyDB);
    }

    public void terminate(){
        dbClient.terminate();
    }

    @Override
    public List<Company> findAll() {
        return dbClient.selectForListCompany(select_all);
    }

    @Override
    public Company findById(int id) {
        return dbClient.selectCompany(String.format(select_by_ID,id));
    }

    @Override
    public void add(Company company) {
        dbClient.run(String.format(insertData,company.getName()));
    }

    @Override
    public void update(Company company) {
        dbClient.run(String.format(update_data, company.getName(), company.getId()));
    }

    @Override
    public void deleteById(int id) {
        dbClient.run(String.format(delete_data, id));
    }
}
