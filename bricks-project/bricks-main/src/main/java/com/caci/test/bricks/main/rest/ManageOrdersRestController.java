package com.caci.test.bricks.main.rest;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.caci.test.bricks.data.domain.Order;
import com.caci.test.bricks.data.service.OrderService;

@RestController
public class ManageOrdersRestController {
	
	@Autowired
	OrderService orderService;

    @RequestMapping("/createOrder")
    public BigInteger createOrder(@RequestParam(value="amount", defaultValue="0") String amount) {
    	
    	if (amount != null && amount != "") {
    		BigInteger amountBI = new BigInteger(amount);
    		
    		if  (amountBI.intValue() > 0) {
    			
    			//CREATE ORDER
    			Order order = new Order();
    			order.setAmount(amountBI);
    			order.setDetails("Red Bricks");
    			
    			//SET AUDIT FIELDS
    			order.setCreatedBy("BRICKS-APP");
    			order.setCreatedDate(Timestamp.from(Instant.now()));
    			order.setModifiedBy("BRICKS-APP");
    			order.setModifiedDate(Timestamp.from(Instant.now()));
    			
    			//STORE ORDER
    			Order savedOrder = orderService.save(order);
    			
    			//UPDATE SEQUENTIAL REFERENCE NUMBER
    			BigInteger refNumber = orderService.updateRefNumber(savedOrder);
    			
    			//RETURN THE CREATED ORDER REFERENCE NUMBER AS A JSON RESPONSE TO THE CLIENT
    			return refNumber;
    		}
        	else {
        		System.out.println("The bricks amount must be > 0");
        	}
    	}
    	else {
    		System.out.println("Please enter a valid bricks amount");
    	}
    	
    	return null;
    }

    @RequestMapping("/getOrder")
    public Order getOrder(@RequestParam(value="refNumber", defaultValue="0") String refNumber) {
    	
    	if (refNumber != null && refNumber != "") {
    		BigInteger refNumberBI = new BigInteger(refNumber);
    		
    		if  (refNumberBI.intValue() > 0) {
    			
    			//GET ORDER BY REFERENCE NUMBER
    			Order order = orderService.getOrderByRefNumber(refNumberBI);
    			
    			//RETURN REQUESTED ORDER AS A JSON RESPONSE TO THE CLIENT
    			return order;
    		}
        	else {
        		System.out.println("The bricks amount must be > 0");
        	}
    	}
    	else {
    		System.out.println("Please enter a valid bricks amount");
    	}
    	
    	return null;
    }

    @RequestMapping("/getOrders")
    public List<Order> getOrders() {
    	
		//GET ALL ORDERS
		List<Order> orders = orderService.getAllOrders();
    			
    	return orders;
    }

}



