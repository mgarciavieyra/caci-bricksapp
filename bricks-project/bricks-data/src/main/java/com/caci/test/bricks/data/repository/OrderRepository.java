package com.caci.test.bricks.data.repository;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.caci.test.bricks.data.domain.Order;


@Repository
@Transactional
public interface OrderRepository extends CrudRepository<Order, BigInteger>, CustomOrderRepository {
	
	//CUSTOM QUERY (JPQL)
	//@Query("Select o from Order o where o.referenceNumber = :refNumber")
	//Order findByReferenceNumber(@Param("refNumber") BigInteger refNumber);

	//SPRING DATA JPA AUTOMATIC IMPLEMENTATION
	Order findByReferenceNumber(BigInteger refNumber);
	
	//GET ORDERS SAMPLE REPORT (NATIVE QUERY)
	@Query(value="select count(1) AS TOTAL_DISPATCHED from SALE_ORDER WHERE ORDER_STATUS = :status", nativeQuery=true)
	Object[] getTotalOrdersByStatus(@Param("status") String status);
	
}