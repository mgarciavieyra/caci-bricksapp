package com.caci.test.bricks.data.domain;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;


/**
 * 
 * Wraps the core audit values for the database entities in the bricks system
 *
 */
@MappedSuperclass
public class BaseEntity {

	@Column(name = "created_date" , nullable = true)
	private Timestamp createdDate;
	
	@Column(name = "created_by" , nullable = true)
	private String createdBy;
	
	@Column(name = "modified_date" , nullable = true)
	private Timestamp modifiedDate;
	
	@Column(name = "modified_by" , nullable = true)
	private String modifiedBy;

	/**
	 * 
	 * @return CreateDate the date the record was first created
	 */
	public Timestamp getCreatedDate() {
		if(createdDate==null){
			LocalDateTime timePoint = LocalDateTime.now();
			createdDate = new Timestamp(java.sql.Date.valueOf(timePoint.toLocalDate()).getTime());
		}
		return createdDate;
	}
	
	/*
	 * @param createDate the date the record was first created
	 */
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	
	/*
	 * @returns the person that created the record
	 */
	public String getCreatedBy() {
		return createdBy;
	}
	
	/*
	 * @param createdBy the person that created the record
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	/*
	 * @returns the date the records was modified
	 */
	@Version
	public Timestamp getModifiedDate() {
		return modifiedDate;
	}

	/*
	 * @param modifiedDate the date the records was modified
	 */
	public void setModifiedDate(Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	/*
	 * @returns the person that created the record
	 */
	public String getModifiedBy() {
		return modifiedBy;
	}
	
	/*
	 * @param modifiedBy the person that modified the record
	 */
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public boolean isNew() {

		return this.getCreatedBy() == null;
	}
	
	@Override
	public String toString() {
		return "BaseEntity [createdDate=" + createdDate + ", createdBy=" + createdBy + ", modifiedDate=" + modifiedDate
				+ ", modifiedBy=" + modifiedBy + "]";
	}
}
