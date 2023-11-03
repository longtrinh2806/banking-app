package com.trinhkimlong.banking.controller;

import com.trinhkimlong.banking.exception.UserNotFoundException;
import com.trinhkimlong.banking.response.DashboardResponse;
import com.trinhkimlong.banking.service.implementation.DashboardServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {
    private final DashboardServiceImpl dashboardService;

    @Autowired
    public DashboardController(DashboardServiceImpl dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("")
    public ResponseEntity<DashboardResponse> getDashboard(@RequestHeader("Authorization") String token) throws UserNotFoundException {
        return ResponseEntity.ok(dashboardService.getDashboardInfo(token));
    }
}
