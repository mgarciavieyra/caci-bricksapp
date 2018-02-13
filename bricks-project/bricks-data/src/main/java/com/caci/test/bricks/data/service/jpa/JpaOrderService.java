package com.caci.test.bricks.data.service.jpa;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.caci.test.bricks.data.domain.Order;
import com.caci.test.bricks.data.repository.OrderRepository;
import com.caci.test.bricks.data.service.OrderService;

@Repository
@Transactional
public class JpaOrderService implements OrderService {

	@Autowired
	private OrderRepository orderRepo;
	
	@PersistenceContext(unitName = "BRICKS-DATA")
	private EntityManager manager;
	
	/*
	 * TRANSACTIONAL METHOD - BOTH OPERATIONS MUST SUCCEED IN ORDER TO COMMIT
	 * */
	@Override
	public BigInteger save(Order order) {

		//STORE ORDER IN THE DB
		Order savedOrder = orderRepo.save(order);
		
		//UPDATE SEQUENTIAL REFERENCE NUMBER
		BigInteger refNumber = updateRefNumber(savedOrder);
		
		return refNumber;
	}

	@Override
	public void update(Order order) {

		//STORE ORDER IN THE DB
		orderRepo.save(order);
	}

	@Override
	public Order getOrderByRefNumber(BigInteger refNumber) {
		
		Order order = orderRepo.findByReferenceNumber(refNumber);
		return order;
	}

	@Override
	public List<Order> getAllOrders() {
		
		List<Order> orders = (List<Order>) orderRepo.findAll();
		return orders;
	}
	
	
	private BigInteger updateRefNumber(Order savedOrder) {
		
		//USE GENERATED ID + 1000 TO GENERATE REFERENCE NUMBER
		BigInteger id = savedOrder.getId();
		int refNumberInt = 1000 + id.intValue();
		BigInteger refNumber = BigInteger.valueOf(refNumberInt);

		//UPDATE REFERENCE NUMBER
		savedOrder.setReferenceNumber(refNumber);
		
		//ADDED JUST TO TEST TRANSACTIONAL SAVE METHOD WORKS AS EXPECTED - PLEASE DELETE
/*		if (refNumber.intValue() > 1000) {
			throw new RuntimeException();
		}
*/		
		//RETURN REFERENCE NUMBER TO THE CLIENT
		return refNumber;
	}

	@Override
	public long countOrders() {
		
		long orderCount = orderRepo.count();
		return orderCount;
	}

	@Override
	public Object getOrdersByStatus(String status) {
		
		Object result = orderRepo.getTotalOrdersByStatus(status);
		//Object result = orderRepo.getTotalOrdersByStatusV2(status);

		return result;
	}

}
