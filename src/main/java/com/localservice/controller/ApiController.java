package com.localservice.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.localservice.model.Booking;
import com.localservice.model.ServicePartner;
import com.localservice.model.User;
import com.localservice.repository.BookingRepository;
import com.localservice.repository.ServicePartnerRepository;
import com.localservice.repository.UserRepository;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api")
public class ApiController {

	@Autowired
	private ServicePartnerRepository servicePartnerRepository;

	@Autowired
	private BookingRepository bookingRepository;

	@Autowired
	private UserRepository userRepository;

	// ✅ ONLY THIS ONE — removed the old findAll() version
	@GetMapping("/partners")
	public List<ServicePartner> getPartners(@RequestParam(required = false) String city, HttpSession session) {

		// Try city from param first
		if (city != null && !city.isEmpty()) {
			return servicePartnerRepository.findActivePartnersByCity(city);
		}

		// Try city from logged in user session
		User user = (User) session.getAttribute("loggedUser");
		if (user != null && user.getCity() != null && !user.getCity().isEmpty()) {
			return servicePartnerRepository.findActivePartnersByCity(user.getCity());
		}

		// No city — return all (fallback)
		return servicePartnerRepository.findActivePartners();
	}

	@GetMapping("/booking/{id}")
	public Booking getBooking(@PathVariable Long id) {
		return bookingRepository.findById(id).orElseThrow();
	}

	@GetMapping("/me")
	public ResponseEntity<?> getCurrentUser(HttpSession session) {
		User user = (User) session.getAttribute("loggedUser");
		if (user == null) {
			return ResponseEntity.status(401).body(null);
		}
		Map<String, Object> userData = new HashMap<>();
		userData.put("id", user.getId());
		userData.put("name", user.getName());
		userData.put("email", user.getEmail());
		return ResponseEntity.ok(userData);
	}

	@GetMapping("/check-email")
	public ResponseEntity<Map<String, Boolean>> checkEmail(@RequestParam String email) {
		boolean exists = userRepository.existsByEmail(email);
		Map<String, Boolean> response = new HashMap<>();
		response.put("available", !exists);
		return ResponseEntity.ok(response);
	}
}