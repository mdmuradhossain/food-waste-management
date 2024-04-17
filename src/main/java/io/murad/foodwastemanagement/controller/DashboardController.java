package io.murad.foodwastemanagement.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@AllArgsConstructor
@Controller
public class DashboardController {

    @GetMapping("/donor-dashboard")
    public String donorDashboard() {
        return "dashboard/donor_dashboard";
    }

    @GetMapping("/consumer-dashboard")
    public String consumerDashboard() {
        return "dashboard/consumer_dashboard";
    }
}
