package com.caci.test.bricks.data.service;

import java.math.BigInteger;
import java.util.List;

import com.caci.test.bricks.data.domain.Order;

public interface OrderService {
	
	public BigInteger save(Order order);
	public Order getOrderByRefNumber(BigInteger refNumber);
	public List<Order> getAllOrders();
	public void update(Order order);
	public long countOrders();
	public Object getOrdersByStatus(String status);

}
