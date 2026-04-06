package com.finance.dashboard.repository;

import com.finance.dashboard.entity.FinancialRecord;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FinancialRecordRepository extends JpaRepository<FinancialRecord, Long> {

    List<FinancialRecord> findByTypeIgnoreCase(String type);

    List<FinancialRecord> findByCategory(String category);
    
    Page<FinancialRecord>findAll(Pageable pageable);
    
    Page<FinancialRecord> findByTypeIgnoreCase(String type, Pageable pageable);
    Page<FinancialRecord> findByCategory(String category, Pageable pageable);
    
    @Query("SELECT r.category, SUM(r.amount) FROM FinancialRecord r GROUP BY r.category")
    List<Object[]> getCategorySummary();
}
