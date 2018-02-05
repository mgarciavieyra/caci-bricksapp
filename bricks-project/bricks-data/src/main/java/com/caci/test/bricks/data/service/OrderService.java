package com.caci.test.bricks.data.service;

import java.math.BigInteger;
import java.util.List;

import com.caci.test.bricks.data.domain.Order;


public interface OrderService {
	
	public Order save(Order order);
	public BigInteger updateRefNumber(Order savedOrder);
	public Order getOrderByRefNumber(BigInteger refNumber);
	public List<Order> getAllOrders();

}
