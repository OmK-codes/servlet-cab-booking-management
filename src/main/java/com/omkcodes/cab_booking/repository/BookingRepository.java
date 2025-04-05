package com.omkcodes.cab_booking.repository;

import com.omkcodes.cab_booking.enums.BookingStatus;
import com.omkcodes.cab_booking.model.Booking;
import com.omkcodes.cab_booking.service.ConnectionService;

import java.sql.*;
import java.util.*;

public class BookingRepository {
    private final ConnectionService connectionService = new ConnectionService();


    public boolean saveBooking(Booking booking) {
        String query = "INSERT INTO booking (booking_id, passenger_id, passenger_name, driver_id, driver_name, vehicle_id, pickup_location, drop_location, fare, distance, booking_status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = connectionService.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, booking.getBookingId());
            preparedStatement.setString(2, booking.getPassengerId());
            preparedStatement.setString(3, booking.getPassengerName());
            preparedStatement.setString(4, booking.getDriverId());
            preparedStatement.setString(5, booking.getDriverName());
            preparedStatement.setString(6, booking.getVehicleId());
            preparedStatement.setString(7, booking.getPickupLocation());
            preparedStatement.setString(8, booking.getDropLocation());
            preparedStatement.setDouble(9, booking.getFare());
            preparedStatement.setDouble(10, booking.getDistance());
            preparedStatement.setString(11, booking.getStatus().name());

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error saving booking: " + e.getMessage());
            return false;
        }
    }

    // Find a booking by ID
    public Booking findBookingById(String bookingId) {
        String query = "SELECT * FROM booking WHERE booking_id = ?";
        try (Connection connection = connectionService.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, bookingId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new Booking(
                        resultSet.getString("booking_id"),
                        resultSet.getString("passenger_id"),
                        resultSet.getString("passenger_name"),
                        resultSet.getString("driver_id"),
                        resultSet.getString("driver_name"),
                        resultSet.getString("vehicle_id"),
                        resultSet.getString("pickup_location"),
                        resultSet.getString("drop_location"),
                        resultSet.getDouble("fare"),
                        resultSet.getDouble("distance"),
                        BookingStatus.valueOf(resultSet.getString("booking_status"))
                );
            }
        } catch (SQLException e) {
            System.err.println("Error fetching booking: " + e.getMessage());
        }
        return null;
    }

    // Get all bookings
    public List<Booking> getAllBookings() {
        List<Booking> bookings = new ArrayList<>();
        String query = "SELECT * FROM booking";
        try (Connection connection = connectionService.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                bookings.add(new Booking(
                        resultSet.getString("booking_id"),
                        resultSet.getString("passenger_id"),
                        resultSet.getString("passenger_name"),
                        resultSet.getString("driver_id"),
                        resultSet.getString("driver_name"),
                        resultSet.getString("vehicle_id"),
                        resultSet.getString("pickup_location"),
                        resultSet.getString("drop_location"),
                        resultSet.getDouble("fare"),
                        resultSet.getDouble("distance"),
                        BookingStatus.valueOf(resultSet.getString("booking_status"))
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching all bookings: " + e.getMessage());
        }
        return bookings;
    }

    // Update booking status
    public boolean updateBookingStatus(String bookingId, BookingStatus newStatus) {
        String query = "UPDATE booking SET booking_status = ? WHERE booking_id = ?";
        try (Connection connection = connectionService.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, newStatus.name());
            preparedStatement.setString(2, bookingId);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating booking status: " + e.getMessage());
            return false;
        }
    }

    // Delete a booking by ID
    public boolean deleteBooking(String bookingId) {
        String query = "DELETE FROM booking WHERE booking_id = ?";
        try (Connection connection = connectionService.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, bookingId);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting booking: " + e.getMessage());
            return false;
        }
    }
}