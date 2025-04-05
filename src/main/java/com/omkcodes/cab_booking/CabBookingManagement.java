package com.omkcodes.cab_booking;

import com.omkcodes.cab_booking.controller.BookingController;
import com.omkcodes.cab_booking.controller.DriverController;
import com.omkcodes.cab_booking.controller.PassengerController;
// Removed VehicleController import since it's now a servlet

import com.omkcodes.cab_booking.repository.BookingRepository;
import com.omkcodes.cab_booking.repository.DriverRepository;
import com.omkcodes.cab_booking.repository.PassengerRepository;
import com.omkcodes.cab_booking.repository.VehicleRepository;
import com.omkcodes.cab_booking.service.impl.BookingServiceImpl;
import com.omkcodes.cab_booking.service.impl.DriverServiceImpl;
import com.omkcodes.cab_booking.service.impl.PassengerServiceImpl;
import com.omkcodes.cab_booking.service.impl.VehicleServiceImpl;

import java.util.Scanner;

public class CabBookingManagement {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        DriverRepository driverRepository = new DriverRepository();
        PassengerRepository passengerRepository = new PassengerRepository();
        VehicleRepository vehicleRepository = new VehicleRepository();
        BookingRepository bookingRepository = new BookingRepository();

        // Passing repository instances to service implementations
        DriverServiceImpl driverService = new DriverServiceImpl(driverRepository);
        PassengerServiceImpl passengerService = new PassengerServiceImpl(passengerRepository);
        VehicleServiceImpl vehicleService = new VehicleServiceImpl(vehicleRepository);
        BookingServiceImpl bookingService = new BookingServiceImpl(bookingRepository);

        DriverController driverController = new DriverController(scanner, driverService);
        PassengerController passengerController = new PassengerController(scanner, passengerService);

        // VehicleController is now managed by servlet, not CLI and only vehicle controller is updated other are remaining.

        BookingController bookingController = new BookingController(scanner, bookingService);

        int mainOption;
        do {
            displayMainMenu();
            mainOption = getIntInput(scanner);
            switch (mainOption) {
                case 1 -> driverController.run();
                case 2 -> passengerController.run();
                case 3 -> {
                    System.out.println("\n Vehicle management is now web-based.");
                    System.out.println("Please access it via: http://localhost:8080/your-project-name/vehicle");
                }
                case 4 -> bookingController.run();
                case 0 -> System.out.println("Exiting... Thank you for using Cab Booking Management!");
                default -> System.out.println("Invalid option, please try again.");
            }
        } while (mainOption != 0);
        scanner.close();
    }

    private static void displayMainMenu() {
        System.out.println("\n=== CAB BOOKING MANAGEMENT ===");
        System.out.println("1. Manage Drivers");
        System.out.println("2. Manage Passengers");
        System.out.println("3. Manage Vehicles (Now Web-Based)");
        System.out.println("4. Manage Bookings");
        System.out.println("0. Exit");
    }

    private static int getIntInput(Scanner scanner) {
        while (true) {
            try {
                System.out.print("Select an option: ");
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a valid number.");
            }
        }
    }
}