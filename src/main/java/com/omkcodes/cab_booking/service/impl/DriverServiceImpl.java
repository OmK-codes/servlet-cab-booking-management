package com.omkcodes.cab_booking.service.impl;

import com.omkcodes.cab_booking.model.Driver;
import com.omkcodes.cab_booking.enums.DriverStatus;
import com.omkcodes.cab_booking.repository.DriverRepository;
import com.omkcodes.cab_booking.service.DriverService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DriverServiceImpl implements DriverService {
    private final DriverRepository driverRepository;

    public DriverServiceImpl(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    @Override
    public Driver createNewDriver(String driverId, String driverName, String phone,
                                  String licenseNumber, int totalTrips, boolean onlineStatus,
                                  String statusInput) {
        DriverStatus status;
        try {
            status = DriverStatus.valueOf(statusInput.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid driver status! Defaulting to INACTIVE.");
            status = DriverStatus.INACTIVE;
        }

        Driver driver = new Driver(driverId, driverName, phone, licenseNumber, totalTrips, onlineStatus, status);
        driverRepository.saveDriver(driver);
        return driver;
    }

    @Override
    public void showAllDrivers() {
        List<Driver> drivers = driverRepository.getAllDrivers();
        if (drivers.isEmpty()) {
            System.out.println("No drivers found.");
        } else {
            drivers.forEach(System.out::println);
        }
    }

    @Override
    public List<Driver> getOnlineDrivers() {
        return driverRepository.getAllDrivers().stream()
                .filter(Driver::isOnlineStatus) // Corrected method reference
                .collect(Collectors.toList());
    }

    @Override
    public List<Driver> getTopDriversByTrips(int limit) {
        return driverRepository.getAllDrivers().stream()
                .sorted((d1, d2) -> Integer.compare(d2.getTotalTrips(), d1.getTotalTrips()))
                .limit(limit)
                .collect(Collectors.toList());
    }

    @Override
    public List<Driver> getDriverList() { // Changed return type to List<Driver>
        return driverRepository.getAllDrivers();
    }

    @Override
    public void displayDriverDetails(Driver driver) {
        if (driver != null) {
            System.out.println(driver);
        } else {
            System.out.println("Driver not found!");
        }
    }

    @Override
    public Driver getDriverById(String driverId) {
        return Optional.ofNullable(driverRepository.findDriverById(driverId)).orElse(null);
    }
}