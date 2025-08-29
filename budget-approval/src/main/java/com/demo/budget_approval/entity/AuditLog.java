package com.demo.budget_approval.entity;

import jakarta.persistence.*;
import java.time.Instant;

import org.springframework.data.annotation.CreatedDate;

@Entity
public class AuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String action;       
    @Column(nullable = false)
    private Long entityId;       
    @Column(nullable = false)
    private String entityType;   
    @Lob
    private String oldValue;                          
    @Lob
    private String newValue;                          
    private String changedBy;                               
    @CreatedDate
    @Column(updatable = false, nullable = false)
    private Instant timestamp;

    @PrePersist public void onCreate() {
    	timestamp = Instant.now();
    	
    
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Long getEntityId() {
		return entityId;
	}

	public void setEntityId(Long entityId) {
		this.entityId = entityId;
	}

	public String getEntityType() {
		return entityType;
	}

	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

	public String getOldValue() {
		return oldValue;
	}

	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}

	public String getNewValue() {
		return newValue;
	}

	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}

	public String getChangedBy() {
		return changedBy;
	}

	public void setChangedBy(String changedBy) {
		this.changedBy = changedBy;
	}

	public Instant getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Instant timestamp) {
		this.timestamp = timestamp;
	}
    
}
