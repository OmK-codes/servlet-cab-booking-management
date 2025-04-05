package com.omkcodes.cab_booking.controller;

import com.omkcodes.cab_booking.exception.InvalidBookingIDException;
import com.omkcodes.cab_booking.service.BookingService;

import java.util.Scanner;

public class BookingController {
    private final Scanner scanner;
    private final BookingService bookingService;

    public BookingController(Scanner scanner, BookingService bookingService) {
        this.scanner = scanner;
        this.bookingService = bookingService; // Use the provided instance instead of creating a new one.
    }

    public void run() {
        int option;
        do {
            displayMenu();
            option = getIntInput("Enter your choice:");

            switch (option) {
                case 1 -> createBooking();
                case 2 -> bookingService.showAllBookings();
                case 3 -> displayBookingDetails();
                case 9 -> System.out.println("Returning to main menu...");
                default -> System.out.println("Invalid option. Please try again.");
            }
        } while (option != 9);
    }

    private void displayMenu() {
        System.out.println("""
                \n=== Booking Management ===
                1. Create a new booking
                2. Show all bookings
                3. Display booking details
                9. Return to main menu
                """);
    }

    private void createBooking() {
        String bookingId = getStringInput("Enter Booking ID:");
        String passengerId = getStringInput("Enter Passenger ID:");
        String passengerName = getStringInput("Enter Passenger Name:");
        String driverId = getStringInput("Enter Driver ID:");
        String driverName = getStringInput("Enter Driver Name:");
        String vehicleId = getStringInput("Enter Vehicle ID:");
        String pickupLocation = getStringInput("Enter Pickup Location:");
        String dropLocation = getStringInput("Enter Drop Location:");
        double fare = getDoubleInput("Enter Fare:");
        double distance = getDoubleInput("Enter Distance:");
        String status = getStringInput("Enter Booking Status (PENDING/CONFIRMED/COMPLETED/CANCELLED):");

        try {
            bookingService.createNewBooking(
                    bookingId, passengerId, passengerName, driverId, driverName, vehicleId,
                    pickupLocation, dropLocation, fare, distance, status
            );
            System.out.println("Booking created successfully!");
        } catch (Exception e) {
            System.out.println("Error creating booking: " + e.getMessage());
        }
    }

    private void displayBookingDetails() {
        String bookingId = getStringInput("Enter Booking ID:");
        try {
            bookingService.displayBookingDetails(bookingService.getBookingById(bookingId));
        } catch (InvalidBookingIDException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private String getStringInput(String message) {
        System.out.print(message + " ");
        return scanner.nextLine().trim();
    }

    private int getIntInput(String message) {
        while (true) {
            try {
                System.out.print(message + " ");
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    private double getDoubleInput(String message) {
        while (true) {
            try {
                System.out.print(message + " ");
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid decimal number.");
            }
        }
    }
}