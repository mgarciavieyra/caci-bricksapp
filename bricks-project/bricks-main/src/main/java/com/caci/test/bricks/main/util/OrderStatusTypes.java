package com.caci.test.bricks.main.util;

public enum OrderStatusTypes {

	READY("READY"),
	CANCELED("CANCELED"),
	DISPATCHED("DISPATCHED"),
	DELIVERED("DELIVERED");
	
	private String status;
	
	private OrderStatusTypes(String status) {
		this.status = status;
	}
	
	public String getStatus() {
		return this.status;
	}
}
