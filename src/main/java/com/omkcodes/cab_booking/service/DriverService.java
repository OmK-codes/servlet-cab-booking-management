package com.omkcodes.cab_booking.service;

import com.omkcodes.cab_booking.model.Driver;
import java.util.List;

public interface DriverService {
    Driver createNewDriver(String driverId, String driverName, String phone,
                           String licenseNumber, int totalTrips, boolean onlineStatus,
                           String statusInput);

    void showAllDrivers();

    List<Driver> getOnlineDrivers();

    List<Driver> getTopDriversByTrips(int limit);

    List<Driver> getDriverList();

    void displayDriverDetails(Driver driver);

    Driver getDriverById(String driverId);
}
