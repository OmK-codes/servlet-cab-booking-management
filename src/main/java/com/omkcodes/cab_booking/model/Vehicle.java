package com.omkcodes.cab_booking.model;

import com.omkcodes.cab_booking.enums.VehicleStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Vehicle {
    private String vehicleId;
    private String model;
    private String registrationNumber;
    private String color;
    private boolean available;
    private int seatCapacity;
    private double perKmRate;
    private VehicleStatus status;}
