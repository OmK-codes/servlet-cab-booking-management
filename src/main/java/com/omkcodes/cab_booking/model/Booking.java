package com.omkcodes.cab_booking.model;

import com.omkcodes.cab_booking.enums.BookingStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Booking {
    private String bookingId;
    private String passengerId;
    private String passengerName;
    private String driverId;
    private String driverName;
    private String vehicleId;
    private String pickupLocation;
    private String dropLocation;
    private double fare;
    private double distance;
    private BookingStatus status;
}