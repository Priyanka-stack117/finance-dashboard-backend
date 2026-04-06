package com.finance.dashboard.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;

@Data
public class CreateRecordDTO {

    @NotNull
    private Double amount;

    @NotNull
    private String type;

    @NotNull
    private String category;
    
    @NotNull
    private LocalDate date;
    
    @NotNull
    private String notes;

	public LocalDate getDate() {
		// TODO Auto-generated method stub
		return date;
	}

	public String getNotes() {
		// TODO Auto-generated method stub
		return notes;
	}
}
