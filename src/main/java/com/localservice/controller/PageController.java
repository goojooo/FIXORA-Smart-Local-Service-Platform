package com.localservice.controller;

import ch.qos.logback.core.model.Model;
import com.localservice.model.Booking;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public class PageController {
    @GetMapping("/payment-page/{bookingId}")
    public String paymentPage(@PathVariable Long bookingId, Model model) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        model.addAttribute("bookingId", bookingId);
        model.addAttribute("serviceType", booking.getServiceType());
        model.addAttribute("bookingDate", booking.getBookingDate());
        model.addAttribute("timeSlot", booking.getTimeSlot());

        return "payment-page";
    }
}
