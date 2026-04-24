package com.localservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.localservice.repository.ServicePartnerRepository;
import com.localservice.repository.ServicePartnerRequestRepository;
import com.localservice.service.AdminService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ServicePartnerRequestRepository requestRepo;

    @Autowired
    private ServicePartnerRepository partnerRepo;

    @Autowired
    private AdminService adminService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("requests", requestRepo.findAll());
        model.addAttribute("partners", partnerRepo.findAll());

        long totalRequests = requestRepo.count();
        long pending = requestRepo.findAll().stream()
                        .filter(r -> r.getStatus().equals("PENDING")).count();
        long approved = partnerRepo.count();
        long rejected = totalRequests - pending - approved;

        model.addAttribute("totalRequests", totalRequests);
        model.addAttribute("pendingCount", pending);
        model.addAttribute("approvedCount", approved);
        model.addAttribute("rejectedCount", rejected);

        return "admin-dashboard";
    }

    // ✅ THIS WAS MISSING — add these two back
    @GetMapping("/approve/{id}")
    public String approvePartner(@PathVariable Long id) {
        adminService.approvePartner(id);
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/reject/{id}")
    public String reject(@PathVariable Long id) {
        adminService.rejectPartner(id);
        return "redirect:/admin/dashboard";
    }
}