package com.caci.test.bricks.data.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.caci.test.bricks.data.domain.Order;


@Repository
@Transactional(readOnly= true)
public interface OrderRepository extends CrudRepository<Order, BigInteger> {
	
	@Query("Select o from Order o where (o.referenceNumber = :refNumber)")
	List<Order> findByRefNumber(@Param("refNumber") BigInteger refNumber);
	
	
}