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

    public Company select(String query){
        List<Company> companies = selectForList(query);
        if(companies.size() == 1){
            return companies.get(0);
        }else if(companies.isEmpty()){
            return null;
        }else{
            throw new IllegalStateException("Query return more than one object");
        }
    }

    public List<Company> selectForList(String query)  {
        List<Company> companies = new ArrayList<>();
        try(ResultSet resultSetItem = statement.executeQuery(query)){
            while(resultSetItem.next() ){
                //Retrieve column values
                String name = resultSetItem.getString("name");
                Company company = new Company(name);
                companies.add(company);
            }
            return companies;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return companies;
    }
}
