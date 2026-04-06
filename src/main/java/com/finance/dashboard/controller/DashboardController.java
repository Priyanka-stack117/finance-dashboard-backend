package com.finance.dashboard.controller;

import com.finance.dashboard.service.DashboardService;

import java.util.Map;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService service;

    public DashboardController(DashboardService service) {
        this.service = service;
    }

    @GetMapping("/summary")
    public String summary() {
        return "Income: " + service.totalIncome() +
                " Expense: " + service.totalExpense() +
                " Balance: " + service.netBalance();
    }
    
    @GetMapping("/category-summary")
    public Map<String, Double> getCategorySummary() {
        System.out.println("API HIT");   // 👈 ADD THIS
        return service.getCategorySummary();
    }
}
