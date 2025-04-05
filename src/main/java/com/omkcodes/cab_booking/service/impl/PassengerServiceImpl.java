package com.omkcodes.cab_booking.service.impl;

import com.omkcodes.cab_booking.enums.PassengerStatus;
import com.omkcodes.cab_booking.exception.InvalidPassengerIDException;
import com.omkcodes.cab_booking.model.Passenger;
import com.omkcodes.cab_booking.repository.PassengerRepository;
import com.omkcodes.cab_booking.service.PassengerService;

import java.util.List;
import java.util.stream.Collectors;

public class PassengerServiceImpl implements PassengerService {
    private final PassengerRepository passengerRepository;

    public PassengerServiceImpl(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }

    @Override
    public void displayPassengerDetails(String passengerId) {
        Passenger passenger = findPassengerById(passengerId);
        if (passenger != null) {
            System.out.println("Passenger Details: " + passenger);
        } else {
            System.out.println("No passenger found with ID: " + passengerId);
        }
    }

    @Override
    public Passenger findPassengerById(String passengerId) {
        return passengerRepository.findPassengerById(passengerId);
    }

    @Override
    public List<Passenger> getPassengersByStatus(PassengerStatus status) {
        return passengerRepository.getAllPassengers().stream()
                .filter(passenger -> passenger.getPassengerStatus() == status)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getAllPassengerNames() {
        return passengerRepository.getAllPassengers().stream()
                .map(Passenger::getPassengerName)
                .collect(Collectors.toList());
    }

    @Override
    public void showAllPassengers() {
        List<Passenger> passengers = passengerRepository.getAllPassengers();
        if (passengers.isEmpty()) {
            System.out.println("No passengers available.");
        } else {
            passengers.forEach(System.out::println);
        }
    }

    @Override
    public Passenger createNewPassenger(String passengerId, String passengerName, String phone, String email, String address, String statusInput)
            throws InvalidPassengerIDException {
        if (passengerId == null || passengerId.isEmpty()) {
            throw new InvalidPassengerIDException("Passenger ID cannot be null or empty.");
        }
        if (passengerRepository.findPassengerById(passengerId) != null) {
            throw new InvalidPassengerIDException("Passenger with ID " + passengerId + " already exists.");
        }
        PassengerStatus status;
        try {
            status = PassengerStatus.valueOf(statusInput.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidPassengerIDException("Invalid Passenger Status: " + statusInput);
        }
        Passenger newPassenger = new Passenger(passengerId, passengerName, email, phone, address, status);
        boolean success = passengerRepository.savePassenger(newPassenger);
        if (success) {
            System.out.println("Passenger created successfully!");
            return newPassenger;
        } else {
            throw new InvalidPassengerIDException("Failed to create passenger.");
        }
    }

    @Override
    public boolean updatePassenger(String passengerId, String name, String phone, String email, String address, String statusInput) {
        Passenger passenger = findPassengerById(passengerId);
        if (passenger == null) {
            System.out.println("Passenger not found.");
            return false;
        }
        if (!name.isEmpty()) passenger.setPassengerName(name);
        if (!phone.isEmpty()) passenger.setPhone(phone);
        if (!email.isEmpty()) passenger.setEmail(email);
        if (!address.isEmpty()) passenger.setAddress(address);
        if (!statusInput.isEmpty()) {
            try {
                PassengerStatus status = PassengerStatus.valueOf(statusInput.toUpperCase());
                passenger.setPassengerStatus(status);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid status provided. Keeping existing status.");
            }
        }
        return passengerRepository.updatePassenger(passenger);
    }

    @Override
    public boolean deletePassenger(String passengerId) {
        return passengerRepository.deletePassenger(passengerId);
    }
}