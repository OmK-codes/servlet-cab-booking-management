package com.omkcodes.cab_booking.repository;

import com.omkcodes.cab_booking.model.Driver;
import com.omkcodes.cab_booking.enums.DriverStatus;
import com.omkcodes.cab_booking.service.ConnectionService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DriverRepository {
    private final ConnectionService connectionService = new ConnectionService();

    // Save a new driver (INSERT or UPDATE)
    public boolean saveDriver(Driver driver) {
        String query = """
                INSERT INTO drivers (driver_id, driver_name, phone, license_number, total_trips, online_status, driver_status)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                ON DUPLICATE KEY UPDATE
                driver_name = VALUES(driver_name), phone = VALUES(phone),
                license_number = VALUES(license_number), total_trips = VALUES(total_trips),
                online_status = VALUES(online_status), driver_status = VALUES(driver_status)
                """;
        try (Connection connection = connectionService.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, driver.getDriverId());
            preparedStatement.setString(2, driver.getDriverName());
            preparedStatement.setString(3, driver.getPhone());
            preparedStatement.setString(4, driver.getLicenseNumber());
            preparedStatement.setInt(5, driver.getTotalTrips());
            preparedStatement.setBoolean(6, driver.isOnlineStatus());
            preparedStatement.setString(7, driver.getDriverStatus().name());

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error saving driver: " + e.getMessage());
            return false;
        }
    }

    // Find a driver by ID
    public Driver findDriverById(String driverId) {
        String query = "SELECT * FROM drivers WHERE driver_id = ?";
        try (Connection connection = connectionService.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, driverId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new Driver(
                        resultSet.getString("driver_id"),
                        resultSet.getString("driver_name"),
                        resultSet.getString("phone"),
                        resultSet.getString("license_number"),
                        resultSet.getInt("total_trips"),
                        resultSet.getBoolean("online_status"),
                        DriverStatus.valueOf(resultSet.getString("driver_status"))
                );
            }
        } catch (SQLException e) {
            System.err.println("Error fetching driver: " + e.getMessage());
        }
        return null;
    }

    // Get all drivers
    public List<Driver> getAllDrivers() {
        List<Driver> drivers = new ArrayList<>();
        String query = "SELECT * FROM drivers";
        try (Connection connection = connectionService.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                drivers.add(new Driver(
                        resultSet.getString("driver_id"),
                        resultSet.getString("driver_name"),
                        resultSet.getString("phone"),
                        resultSet.getString("license_number"),
                        resultSet.getInt("total_trips"),
                        resultSet.getBoolean("online_status"),
                        DriverStatus.valueOf(resultSet.getString("driver_status"))
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching all drivers: " + e.getMessage());
        }
        return drivers;
    }

    // Update driver status
    public boolean updateDriverStatus(String driverId, DriverStatus newStatus) {
        String query = "UPDATE drivers SET driver_status = ? WHERE driver_id = ?";
        try (Connection connection = connectionService.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, newStatus.name());
            preparedStatement.setString(2, driverId);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating driver status: " + e.getMessage());
            return false;
        }
    }

    // Update driver online status
    public boolean updateOnlineStatus(String driverId, boolean newStatus) {
        String query = "UPDATE drivers SET online_status = ? WHERE driver_id = ?";
        try (Connection connection = connectionService.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setBoolean(1, newStatus);
            preparedStatement.setString(2, driverId);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating online status: " + e.getMessage());
            return false;
        }
    }

    // Delete driver by ID
    public boolean deleteDriver(String driverId) {
        String query = "DELETE FROM drivers WHERE driver_id = ?";
        try (Connection connection = connectionService.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, driverId);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting driver: " + e.getMessage());
            return false;
        }
    }
}
