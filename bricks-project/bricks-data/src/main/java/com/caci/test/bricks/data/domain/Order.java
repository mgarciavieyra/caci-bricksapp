package com.caci.test.bricks.data.domain;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "sale_order")
@SequenceGenerator(name="seq", initialValue=1, allocationSize=100)
public class Order extends BaseEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private BigInteger id;

	@Column(name = "reference_number", nullable = true)
	private BigInteger referenceNumber;

	@Column(name = "amount", nullable = false)
	private BigInteger amount;
	
	@Column(name = "details", nullable = false)
	private String details;

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public BigInteger getReferenceNumber() {
		return referenceNumber;
	}

	public void setReferenceNumber(BigInteger referenceNumber) {
		this.referenceNumber = referenceNumber;
	}

	public BigInteger getAmount() {
		return amount;
	}

	public void setAmount(BigInteger amount) {
		this.amount = amount;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", referenceNumber=" + referenceNumber + ", amount=" + amount + ", details="
				+ details + "]";
	}

}
