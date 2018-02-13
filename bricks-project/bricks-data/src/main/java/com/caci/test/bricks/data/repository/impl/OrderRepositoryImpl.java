package com.caci.test.bricks.data.repository.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.caci.test.bricks.data.repository.CustomOrderRepository;

public class OrderRepositoryImpl implements CustomOrderRepository {

	@PersistenceContext(unitName = "BRICKS-DATA")
	private EntityManager manager;

	@Override
	public Object getTotalOrdersByStatusV2(String status) {
		
		String qlString = "select count(1) AS TOTAL_DISPATCHED from SALE_ORDER WHERE ORDER_STATUS = '" + status + "'";
		Query q = manager.createNativeQuery(qlString);
		Object results = q.getSingleResult();
		
		return results;
	}

}
