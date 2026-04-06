package com.finance.dashboard.controller;

import com.finance.dashboard.dto.CreateRecordDTO;
import com.finance.dashboard.entity.FinancialRecord;
import com.finance.dashboard.service.FinancialService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/records")
public class FinancialController {

    private final FinancialService service;

    public FinancialController(FinancialService service) {
        this.service = service;
    }
    
    //create
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FinancialRecord create(@Valid @RequestBody CreateRecordDTO dto) {

        FinancialRecord record = new FinancialRecord();
        record.setAmount(dto.getAmount());
        record.setType(dto.getType());
        record.setCategory(dto.getCategory());
        record.setDate(dto.getDate());
        record.setNotes(dto.getNotes());

        FinancialRecord saved=service.createRecord(record);
        return saved;
    }
    
    //read
    @GetMapping
    public Page<FinancialRecord> getAll(Pageable pageable) {
        return service.getAllRecords(pageable);
    }
    
  
    //update
    @PutMapping("/{id}")
    public FinancialRecord update(@PathVariable Long id,
                                  @RequestBody FinancialRecord record) {
    	

        return service.updateRecord(id, record);
    }
    
    //delete
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        service.deleteRecord(id);
        return "Record deleted successfully";
    }
    
    @GetMapping("/summary")
    public Map<String, Double> summary() {

        Map<String, Double> data = new HashMap<>();
        data.put("income", service.totalIncome());
        data.put("expense", service.totalExpense());
        data.put("balance", service.netBalance());

        return data;
    }
    
    @GetMapping("/category-summary")
    public Map<String, Double>getCategorySummary() {
        return service.getCategorySummary();
    }
}