package com.omkcodes.cab_booking.controller;

import com.omkcodes.cab_booking.exception.InvalidVehicleIDException;
import com.omkcodes.cab_booking.model.Vehicle;
import com.omkcodes.cab_booking.repository.VehicleRepository;
import com.omkcodes.cab_booking.service.VehicleService;
import com.omkcodes.cab_booking.service.impl.VehicleServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/vehicle")
public class VehicleController extends HttpServlet {

    private VehicleService vehicleService;

    @Override
    public void init() throws ServletException {
        this.vehicleService = new VehicleServiceImpl(new VehicleRepository());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String vehicleId = request.getParameter("vehicleId");
        String model = request.getParameter("model");
        String registrationNumber = request.getParameter("registrationNumber");
        String color = request.getParameter("color");
        boolean available = Boolean.parseBoolean(request.getParameter("available"));
        int seatCapacity = Integer.parseInt(request.getParameter("seatCapacity"));
        double perKmRate = Double.parseDouble(request.getParameter("perKmRate"));
        String status = request.getParameter("status");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            Vehicle vehicle = vehicleService.createNewVehicle(vehicleId, model, registrationNumber,
                    color, available, seatCapacity, perKmRate, status);
            out.println("<h3>Vehicle added successfully!</h3>");
            out.println("<p>" + vehicle + "</p>");
        } catch (InvalidVehicleIDException e) {
            out.println("<h3 style='color:red;'>Error: " + e.getMessage() + "</h3>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Vehicle> vehicles = vehicleService.getVehicleList();

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<h2>All Vehicles</h2>");

        if (vehicles.isEmpty()) {
            out.println("<p>No vehicles found.</p>");
        } else {
            for (Vehicle v : vehicles) {
                out.println("<p>" + v + "</p>");
            }
        }
    }
}
