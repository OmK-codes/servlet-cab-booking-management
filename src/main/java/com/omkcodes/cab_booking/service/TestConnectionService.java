package com.omkcodes.cab_booking.service;

import java.sql.Connection;

public class TestConnectionService {
    public static void main(String[] args) {
        ConnectionService connectionService = new ConnectionService();
        Connection connection = connectionService.getConnection();

        if (connection != null) {
            System.out.println("Database connection test successful!");
        } else {
            System.out.println("Database connection test failed!");
        }
    }
}
