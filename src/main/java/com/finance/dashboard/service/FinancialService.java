package com.finance.dashboard.service;

import com.finance.dashboard.entity.FinancialRecord;
import com.finance.dashboard.repository.FinancialRecordRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FinancialService {

    private final FinancialRecordRepository repo;

    public FinancialService(FinancialRecordRepository repo) {
        this.repo = repo;
    }

    
    public FinancialRecord createRecord(FinancialRecord record) {
        System.out.println("Saving record: " + record); 
        return repo.save(record); 
    }

    public Page<FinancialRecord> getAllRecords(Pageable pageable) {
        Page<FinancialRecord> page = repo.findAll(pageable);
        System.out.println("Total Records : " + page.getTotalElements());
        return page;
    }
    
 // Get record by ID
    public FinancialRecord getById(Long id) {
        return repo.findById(id)
            .orElseThrow(() -> new RuntimeException("Record not found"));
    }

    // Update record
    public FinancialRecord updateRecord(Long id, FinancialRecord record) {
        FinancialRecord existing = getById(id);

        existing.setAmount(record.getAmount());
        existing.setType(record.getType());
        existing.setCategory(record.getCategory());
        existing.setDate(record.getDate());
        existing.setNotes(record.getNotes());

        return repo.save(existing);
    }

    // Delete record
    public void deleteRecord(Long id) {
        FinancialRecord existing = getById(id);
        repo.delete(existing);
    }
    
    public double totalIncome() {
        return repo.findAll().stream()
            .filter(r -> r.getType().equalsIgnoreCase("INCOME"))
            .mapToDouble(FinancialRecord::getAmount)
            .sum();
    }

    public double totalExpense() {
    	return repo.findByTypeIgnoreCase("EXPENSE")
                .stream()
                .mapToDouble(FinancialRecord::getAmount)
                .sum();
    }

    public double netBalance() {
        return totalIncome() - totalExpense();
    }
    
    public Map<String, Double> getCategorySummary() {

        List<Object[]> results = repo.getCategorySummary();

        System.out.println("DEBUG DATA: " + results); // 👈 IMPORTANT

        Map<String, Double> map = new HashMap<>();

        for (Object[] row : results) {
            String category = (String) row[0];
            Double total = (Double) row[1];
            map.put(category, total);
        }

        return map;
    }
    
    public Map<String, Double> getSummary() {

        List<FinancialRecord> records = repo.findAll();

        double income = 0;
        double expense = 0;

        for (FinancialRecord r : records) {
            if ("INCOME".equalsIgnoreCase(r.getType())) {
                income += r.getAmount();
            } else if ("EXPENSE".equalsIgnoreCase(r.getType())) {
                expense += r.getAmount();
            }
        }

        Map<String, Double> result = new HashMap<>();
        result.put("income", income);
        result.put("expense", expense);

        return result;
    }
}