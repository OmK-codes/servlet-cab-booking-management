package com.omkcodes.cab_booking.model;

import com.omkcodes.cab_booking.enums.PassengerStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Passenger {
    private String passengerId;
    private String passengerName;
    private String email;
    private String phone;
    private String address;
    private PassengerStatus passengerStatus;
}