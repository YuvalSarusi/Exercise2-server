package com.dev;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.sql.*;

@Component
public class Persist {
    private Connection connection;

    @PostConstruct
    public void createConnectionToDatabase () {
        try {
            this.connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/ashCollege", "root", "1234");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




}
