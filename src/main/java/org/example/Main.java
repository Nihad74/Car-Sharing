package org.example;

import java.sql.*;

public class Main {

    static final String JDBC_Driver ="org.h2.Driver";
    static final String DB_url = "jdbc:h2:./src/main/java/db/carsharing";
    //static final String DB_url ="jdbc:h2:~/test";
    public static void main(String[] args) {
        Connection connection = null;
        Statement statement = null;

        try{
            Class.forName(JDBC_Driver);

            System.out.println("Connecting to database...");
            connection = DriverManager.getConnection(DB_url);

            System.out.println("Creating table in database...");
            statement = connection.createStatement();
            String sql = "create table if not exists COMPANY(" +
                    "ID INTEGER," +
                    "NAME VARCHAR(20)" +
                    ");";

            statement.executeUpdate(sql);
            
            //end connection
            connection.close();
            statement.close();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}