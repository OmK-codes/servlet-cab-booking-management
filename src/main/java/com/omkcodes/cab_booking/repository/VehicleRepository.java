package com.omkcodes.cab_booking.repository;

import com.omkcodes.cab_booking.enums.VehicleStatus;
import com.omkcodes.cab_booking.model.Vehicle;
import com.omkcodes.cab_booking.service.ConnectionService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VehicleRepository {
    private final ConnectionService connectionService = new ConnectionService();

    // Save a new vehicle
    public boolean saveVehicle(Vehicle vehicle) {
        String query = "INSERT INTO vehicle (vehicle_id, model, registration_number, color, available, seat_capacity, per_km_rate, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = connectionService.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, vehicle.getVehicleId());
            preparedStatement.setString(2, vehicle.getModel());
            preparedStatement.setString(3, vehicle.getRegistrationNumber());
            preparedStatement.setString(4, vehicle.getColor());
            preparedStatement.setBoolean(5, vehicle.isAvailable());
            preparedStatement.setInt(6, vehicle.getSeatCapacity());
            preparedStatement.setDouble(7, vehicle.getPerKmRate());
            preparedStatement.setString(8, vehicle.getStatus().name());

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error saving vehicle: " + e.getMessage());
            return false;
        }
    }

    // Find vehicle by ID
    public Vehicle findVehicleById(String vehicleId) {
        String query = "SELECT * FROM vehicle WHERE vehicle_id = ?";
        try (Connection connection = connectionService.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, vehicleId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new Vehicle(
                        resultSet.getString("vehicle_id"),
                        resultSet.getString("model"),
                        resultSet.getString("registration_number"),
                        resultSet.getString("color"),
                        resultSet.getBoolean("available"),
                        resultSet.getInt("seat_capacity"),
                        resultSet.getDouble("per_km_rate"),
                        VehicleStatus.valueOf(resultSet.getString("status"))
                );
            }
        } catch (SQLException e) {
            System.err.println("Error finding vehicle: " + e.getMessage());
        }
        return null;
    }

    // Get all vehicles
    public List<Vehicle> getAllVehicles() {
        List<Vehicle> vehicles = new ArrayList<>();
        String query = "SELECT * FROM vehicle";
        try (Connection connection = connectionService.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                vehicles.add(new Vehicle(
                        resultSet.getString("vehicle_id"),
                        resultSet.getString("model"),
                        resultSet.getString("registration_number"),
                        resultSet.getString("color"),
                        resultSet.getBoolean("available"),
                        resultSet.getInt("seat_capacity"),
                        resultSet.getDouble("per_km_rate"),
                        VehicleStatus.valueOf(resultSet.getString("status"))
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving vehicles: " + e.getMessage());
        }
        return vehicles;
    }

    // Delete vehicle
    public boolean deleteVehicle(String vehicleId) {
        String query = "DELETE FROM vehicle WHERE vehicle_id = ?";
        try (Connection connection = connectionService.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, vehicleId);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting vehicle: " + e.getMessage());
            return false;
        }
    }
}
