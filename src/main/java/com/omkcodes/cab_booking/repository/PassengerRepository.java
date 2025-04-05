package com.omkcodes.cab_booking.repository;

import com.omkcodes.cab_booking.model.Passenger;
import com.omkcodes.cab_booking.service.ConnectionService;
import com.omkcodes.cab_booking.enums.PassengerStatus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PassengerRepository {
    private final ConnectionService connectionService = new ConnectionService();

    // CREATE (Insert a new passenger)
    public boolean savePassenger(Passenger passenger) {
        String query = "INSERT INTO passenger (passenger_id, passenger_name, email, phone, address, passenger_status) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = connectionService.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, passenger.getPassengerId());
            preparedStatement.setString(2, passenger.getPassengerName());
            preparedStatement.setString(3, passenger.getEmail());
            preparedStatement.setString(4, passenger.getPhone());
            preparedStatement.setString(5, passenger.getAddress());
            preparedStatement.setString(6, passenger.getPassengerStatus().name());
            int rowsInserted = preparedStatement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            System.err.println("Error saving passenger: " + e.getMessage());
            return false;
        }
    }

    // READ (Find passenger by ID)
    public Passenger findPassengerById(String passengerId) {
        String query = "SELECT * FROM passenger WHERE passenger_id = ?";
        try (Connection connection = connectionService.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, passengerId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new Passenger(
                        resultSet.getString("passenger_id"),
                        resultSet.getString("passenger_name"),
                        resultSet.getString("email"),
                        resultSet.getString("phone"),
                        resultSet.getString("address"),
                        PassengerStatus.valueOf(resultSet.getString("passenger_status")) // Convert String to ENUM
                );
            }
        } catch (SQLException e) {
            System.err.println("Error fetching passenger: " + e.getMessage());
        }
        return null;
    }

    // UPDATE (Modify passenger details)
    public boolean updatePassenger(Passenger passenger) {
        String query = "UPDATE passenger SET passenger_name = ?, email = ?, phone = ?, address = ?, passenger_status = ? WHERE passenger_id = ?";
        try (Connection connection = connectionService.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, passenger.getPassengerName());
            preparedStatement.setString(2, passenger.getEmail());
            preparedStatement.setString(3, passenger.getPhone());
            preparedStatement.setString(4, passenger.getAddress());
            preparedStatement.setString(5, passenger.getPassengerStatus().name());
            preparedStatement.setString(6, passenger.getPassengerId());

            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            System.err.println("Error updating passenger: " + e.getMessage());
            return false;
        }
    }

    // DELETE (Remove passenger by ID)
    public boolean deletePassenger(String passengerId) {
        String query = "DELETE FROM passenger WHERE passenger_id = ?";
        try (Connection connection = connectionService.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, passengerId);
            int rowsDeleted = preparedStatement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting passenger: " + e.getMessage());
            return false;
        }
    }

    // GET ALL PASSENGERS
    public List<Passenger> getAllPassengers() {
        List<Passenger> passengers = new ArrayList<>();
        String query = "SELECT * FROM passenger";
        try (Connection connection = connectionService.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                passengers.add(new Passenger(
                        resultSet.getString("passenger_id"),
                        resultSet.getString("passenger_name"),
                        resultSet.getString("email"),
                        resultSet.getString("phone"),
                        resultSet.getString("address"),
                        PassengerStatus.valueOf(resultSet.getString("passenger_status"))
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching all passengers: " + e.getMessage());
        }
        return passengers;
    }
}
