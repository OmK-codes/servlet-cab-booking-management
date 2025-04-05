package com.omkcodes.cab_booking.service.impl;

import com.omkcodes.cab_booking.enums.BookingStatus;
import com.omkcodes.cab_booking.exception.InvalidBookingIDException;
import com.omkcodes.cab_booking.model.Booking;
import com.omkcodes.cab_booking.repository.BookingRepository;
import com.omkcodes.cab_booking.service.BookingService;

import java.util.List;

public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;

    public BookingServiceImpl(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Override
    public void displayBookingDetails(Booking booking) {
        if (booking != null) {
            System.out.println("Booking Details: " + booking);
        } else {
            System.out.println("Booking details are not available.");
        }
    }

    @Override
    public void showAllBookings() {
        List<Booking> bookings = bookingRepository.getAllBookings();
        if (bookings.isEmpty()) {
            System.out.println("No bookings found.");
        } else {
            bookings.forEach(System.out::println);
        }
    }

    @Override
    public Booking createNewBooking(String bookingId, String passengerId, String passengerName,
                                    String driverId, String driverName, String vehicleId,
                                    String pickupLocation, String dropLocation, double fare,
                                    double distance, String statusInput) throws InvalidBookingIDException {

        BookingStatus status;
        try {
            status = BookingStatus.valueOf(statusInput.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidBookingIDException("Invalid booking status: " + statusInput);
        }

        Booking booking = new Booking(bookingId, passengerId, passengerName, driverId, driverName,
                vehicleId, pickupLocation, dropLocation, fare, distance, status);

        if (!bookingRepository.saveBooking(booking)) {
            throw new InvalidBookingIDException("Failed to save booking.");
        }
        return booking;
    }

    @Override
    public Booking updateBookingStatus(String bookingId, BookingStatus newStatus) throws InvalidBookingIDException {
        if (!bookingRepository.updateBookingStatus(bookingId, newStatus)) {
            throw new InvalidBookingIDException("Failed to update booking status.");
        }
        return bookingRepository.findBookingById(bookingId);
    }

    @Override
    public Booking getBookingById(String bookingId) throws InvalidBookingIDException {
        Booking booking = bookingRepository.findBookingById(bookingId);
        if (booking == null) {
            throw new InvalidBookingIDException("No booking found with ID: " + bookingId);
        }
        return booking;
    }

    @Override
    public List<Booking> getBookingsByStatus(BookingStatus status) {
        return bookingRepository.getAllBookings().stream()
                .filter(booking -> booking.getStatus() == status)
                .toList();
    }
}