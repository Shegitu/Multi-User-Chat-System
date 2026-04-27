package com.chat.database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Db {
    private static final String URL =  "jdbc:mysql://localhost:3306/chatapp";
    private static final String USER = "root";
    private static final String PASSWORD ="";

    public static Connection getConnection(){
        Connection conn = null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL,USER,PASSWORD);
            System.out.println("Database connected Successfully");

        } catch(ClassNotFoundException e){
            System.out.println("MySQL not found");
            e.printStackTrace();
        } catch (SQLException e){
            System.out.println("Connection failed");
            e.printStackTrace();
        } 
        return conn;
    }



}
