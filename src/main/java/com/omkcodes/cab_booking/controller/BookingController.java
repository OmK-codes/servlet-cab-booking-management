package com.omkcodes.cab_booking.controller;

import com.omkcodes.cab_booking.exception.InvalidBookingIDException;
import com.omkcodes.cab_booking.model.Booking;
import com.omkcodes.cab_booking.service.BookingService;
import com.omkcodes.cab_booking.service.impl.BookingServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/booking")
public class BookingControllerServlet extends HttpServlet {

    private BookingService bookingService;

    @Override
    public void init() throws ServletException {
        bookingService = new BookingServiceImpl(); // Adjust if constructor requires repositories
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get form parameters
        String bookingId = request.getParameter("bookingId");
        String passengerId = request.getParameter("passengerId");
        String passengerName = request.getParameter("passengerName");
        String driverId = request.getParameter("driverId");
        String driverName = request.getParameter("driverName");
        String vehicleId = request.getParameter("vehicleId");
        String pickupLocation = request.getParameter("pickupLocation");
        String dropLocation = request.getParameter("dropLocation");
        double fare = Double.parseDouble(request.getParameter("fare"));
        double distance = Double.parseDouble(request.getParameter("distance"));
        String status = request.getParameter("status");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            bookingService.createNewBooking(
                    bookingId, passengerId, passengerName, driverId, driverName,
                    vehicleId, pickupLocation, dropLocation, fare, distance, status
            );

            out.println("<h2 style='color:green;'>Booking created successfully!</h2>");
            out.println("<p><a href='booking-form.html'>Back to Booking Form</a></p>");

        } catch (InvalidBookingIDException e) {
            out.println("<h2 style='color:red;'>Error: " + e.getMessage() + "</h2>");
        } catch (Exception e) {
            out.println("<h2 style='color:red;'>Unexpected Error: " + e.getMessage() + "</h2>");
        }
    }

    // Optional: handle GET requests to show all bookings
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<h2>All Bookings</h2>");
        for (Booking booking : bookingService.getBookingList()) {
            out.println("<p>" + booking + "</p>");
        }
    }
}