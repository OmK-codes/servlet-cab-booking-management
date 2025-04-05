package com.omkcodes.cab_booking.service;

import com.omkcodes.cab_booking.enums.PassengerStatus;
import com.omkcodes.cab_booking.exception.InvalidPassengerIDException;
import com.omkcodes.cab_booking.model.Passenger;

import java.util.List;

public interface PassengerService {
    void displayPassengerDetails(String passengerId);
    Passenger findPassengerById(String passengerId);
    List<Passenger> getPassengersByStatus(PassengerStatus status);
    List<String> getAllPassengerNames();
    void showAllPassengers();
    Passenger createNewPassenger(String passengerId, String passengerName, String phone, String email, String address, String status) throws InvalidPassengerIDException;
    boolean updatePassenger(String passengerId, String name, String phone, String email, String address, String status);
    boolean deletePassenger(String passengerId);
}

// details are in notepad for ref.