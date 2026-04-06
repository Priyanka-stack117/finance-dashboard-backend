package com.finance.dashboard.service;

import com.finance.dashboard.entity.FinancialRecord;
import com.finance.dashboard.repository.FinancialRecordRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DashboardService {

    private final FinancialRecordRepository repo;

    public DashboardService(FinancialRecordRepository repo) {
        this.repo = repo;
    }

    public double totalIncome() {
        return repo.findAll()
                .stream()
                .filter(r -> r.getType().equalsIgnoreCase("INCOME"))
                .mapToDouble(FinancialRecord::getAmount)
                .sum();
    }

    public double totalExpense() {
        return repo.findAll()
                .stream()
                .filter(r -> r.getType().equalsIgnoreCase("EXPENSE"))
                .mapToDouble(FinancialRecord::getAmount)
                .sum();
    }

    public double netBalance() {
        return totalIncome() - totalExpense();
    }
    
    public Map<String, Double> getCategorySummary() {

        System.out.println("SERVICE HIT");

        List<Object[]> results = repo.getCategorySummary();

        System.out.println("DATA: " + results);

        Map<String, Double> map = new HashMap<>();

        for (Object[] row : results) {
            String category = (String) row[0];
            Double total = (Double) row[1];
            map.put(category, total);
        }

        return map;
    }
}
