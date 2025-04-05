package com.omkcodes.cab_booking.service.impl;

import com.omkcodes.cab_booking.enums.VehicleStatus;
import com.omkcodes.cab_booking.exception.InvalidVehicleIDException;
import com.omkcodes.cab_booking.model.Vehicle;
import com.omkcodes.cab_booking.repository.VehicleRepository;
import com.omkcodes.cab_booking.service.VehicleService;

import java.util.List;

public class VehicleServiceImpl implements VehicleService {
    private final VehicleRepository vehicleRepository;

    public VehicleServiceImpl(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public Vehicle createNewVehicle(String vehicleId, String model, String registrationNumber,
                                    String color, boolean availability, int seatCapacity,
                                    double perKmRate, String status) throws InvalidVehicleIDException {
        if (vehicleId == null || vehicleId.isEmpty()) {
            throw new InvalidVehicleIDException("Vehicle ID cannot be null or empty.");
        }
        if (vehicleRepository.findVehicleById(vehicleId) != null) {
            throw new InvalidVehicleIDException("Vehicle with ID " + vehicleId + " already exists.");
        }

        VehicleStatus vehicleStatus;
        try {
            vehicleStatus = VehicleStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidVehicleIDException("Invalid Vehicle Status: " + status);
        }

        Vehicle newVehicle = new Vehicle(vehicleId, model, registrationNumber, color, availability, seatCapacity, perKmRate, vehicleStatus);
        if (!vehicleRepository.saveVehicle(newVehicle)) {
            throw new InvalidVehicleIDException("Failed to save vehicle.");
        }
        return newVehicle;
    }

    @Override
    public void showAllVehicles() {
        List<Vehicle> vehicles = getVehicleList();
        if (vehicles.isEmpty()) {
            System.out.println("No vehicles found.");
        } else {
            vehicles.forEach(System.out::println);
        }
    }

    @Override
    public void displayVehicleDetails(String vehicleId) {
        Vehicle vehicle = vehicleRepository.findVehicleById(vehicleId);
        if (vehicle != null) {
            System.out.println("Vehicle Details: " + vehicle);
        } else {
            System.out.println("Vehicle details not available.");
        }
    }

    @Override
    public List<Vehicle> getVehicleList() {
        return vehicleRepository.getAllVehicles();
    }
}