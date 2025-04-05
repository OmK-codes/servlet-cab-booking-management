package com.omkcodes.cab_booking.model;

import com.omkcodes.cab_booking.enums.DriverStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Driver {
    private String driverId;
    private String driverName;
    private String phone;
    private String licenseNumber;
    private int totalTrips;
    private boolean onlineStatus;
    private DriverStatus driverStatus;
}