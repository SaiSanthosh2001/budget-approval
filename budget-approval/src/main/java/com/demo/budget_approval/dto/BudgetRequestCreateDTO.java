package com.demo.budget_approval.dto;

import java.math.BigDecimal;

public class BudgetRequestCreateDTO {
    private Long departmentId;
    private BigDecimal requestedAmount;
    private String purpose;
    private String requestedBy;
	public Long getDepartmentId() {
 
		return departmentId;
	}
	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}
	public BigDecimal getRequestedAmount() {
		return requestedAmount;
	}
	public void setRequestedAmount(BigDecimal requestedAmount) {
		this.requestedAmount = requestedAmount;
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	public String getRequestedBy() {
		return requestedBy;
	}
	public void setRequestedBy(String requestedBy) {
		this.requestedBy = requestedBy;
	}
    

    

}
