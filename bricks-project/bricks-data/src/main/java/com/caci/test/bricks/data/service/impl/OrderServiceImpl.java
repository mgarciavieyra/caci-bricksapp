package com.caci.test.bricks.data.service.impl;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.caci.test.bricks.data.domain.Order;
import com.caci.test.bricks.data.repository.OrderRepository;
import com.caci.test.bricks.data.service.OrderService;


@Repository
@Transactional(readOnly= true)
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository orderRepo;

	@PersistenceContext(unitName = "BRICKS-DATA")
	private EntityManager manager;

	@Override
	public Order save(Order order) {
		
		//STORE ORDER IN THE DB
		Order savedOrder = orderRepo.save(order);
		
		return savedOrder;
	}

	@Override
	public BigInteger updateRefNumber(Order savedOrder) {
		
		//USE GENERATED ID + 1000 TO GENERATE REFERENCE NUMBER
		BigInteger id = savedOrder.getId();
		int refNumberInt = 1000 + id.intValue();
		BigInteger refNumber = BigInteger.valueOf(refNumberInt);

		//UPDATE REFERENCE NUMBER
		savedOrder.setReferenceNumber(refNumber);
		orderRepo.save(savedOrder);
		
		//COMMIT UPDATE
		manager.flush();
		
		//RETURN REFERENCE NUMBER TO THE CLIENT
		return refNumber;
	}

	@Override
	public Order getOrderByRefNumber(BigInteger refNumber) {
		
		List<Order> orders = orderRepo.findByRefNumber(refNumber);
		if (orders != null && orders.size() > 0)
			return orders.get(0);
		else 
			return null;
	}

	@Override
	public List<Order> getAllOrders() {
		
		List<Order> orders = (List<Order>) orderRepo.findAll();
		return orders;
	}
}
