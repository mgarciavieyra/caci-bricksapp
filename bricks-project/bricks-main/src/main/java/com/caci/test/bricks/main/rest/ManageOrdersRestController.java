package com.caci.test.bricks.main.rest;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.caci.test.bricks.data.domain.Order;
import com.caci.test.bricks.data.service.OrderService;
import com.caci.test.bricks.data.util.StringUtils;
import com.caci.test.bricks.main.util.OrderStatusTypes;

@RestController
public class ManageOrdersRestController {
	
	private static final Logger logger = LoggerFactory.getLogger(ManageOrdersRestController.class);
	
	@Autowired
	private OrderService orderService;

    @RequestMapping("/createOrder")
    public BigInteger createOrder(@RequestParam(value="amount", defaultValue="0") String amount) {
    	
		//DEFAULT VALUE AUTOMATICALLY PROVIDE A VALID VALUE IF NOT PRESENT (NULL OR "")
    	//BUT I STILL NEED TO CHECK FOR NUMERIC (POSITIVE INTEGERS) VALUES ONLY
    	if (StringUtils.isPositiveInteger(amount)) {
			
    		BigInteger amountBI = new BigInteger(amount);
			
			if  (amountBI.intValue() > 0) {
				
				//CREATE ORDER
				Order order = new Order();
				order.setAmount(amountBI);
				order.setDetails("Red Bricks");
				order.setStatus(OrderStatusTypes.READY.getStatus());
				
				//SET AUDIT FIELDS
				order.setCreatedBy("BRICKS-APP");
				order.setCreatedDate(Timestamp.from(Instant.now()));
				order.setModifiedBy("BRICKS-APP");
				order.setModifiedDate(Timestamp.from(Instant.now()));
				
				//STORE ORDER
				BigInteger refNumber = orderService.save(order);
				
				//RETURN THE CREATED ORDER REFERENCE NUMBER AS A JSON RESPONSE TO THE CLIENT
				return refNumber;
			}
	    	else {
	    		logger.warn("The bricks amount must be > 0");
	    	}
    	}  	else {
    		logger.warn("The bricks amount must be a positive integer");
    	}
    	
    	return null;
    }

    @RequestMapping("/getOrder")
    public Order getOrder(@RequestParam(value="refNumber", defaultValue="0") String refNumber) {
    	
		//DEFAULT VALUE AUTOMATICALLY PROVIDE A VALID VALUE IF NOT PRESENT (NULL OR "")
    	//BUT I STILL NEED TO CHECK FOR NUMERIC (POSITIVE INTEGERS) VALUES ONLY
    	if (StringUtils.isPositiveInteger(refNumber)) {
    		
    		BigInteger refNumberBI = new BigInteger(refNumber);
    		
    		if  (refNumberBI.intValue() > 0) {
    			
    			//GET ORDER BY REFERENCE NUMBER
    			Order order = orderService.getOrderByRefNumber(refNumberBI);
    			
    			//RETURN REQUESTED ORDER AS A JSON RESPONSE TO THE CLIENT
    			return order;
    		}
        	else {
        		logger.warn("The refernce number must be > 0");
        	}
    	}  	else {
    		logger.warn("The refernce number must be a positive integer");
    	}
    	
    	return null;
    }

    @RequestMapping("/getOrders")
    public List<Order> getOrders() {
    	
		//GET ALL ORDERS
		List<Order> orders = orderService.getAllOrders();
    			
    	return orders;
    }

    @RequestMapping("/updateOrder")
    public ResponseEntity<BigInteger> updateOrder(@RequestParam(value="refNumber", defaultValue="0") String refNumber, 
    					  @RequestParam(value="amount", defaultValue="0") String amount) {
    	
		//DEFAULT VALUE AUTOMATICALLY PROVIDE A VALID VALUE IF NOT PRESENT (NULL OR "")
    	//BUT I STILL NEED TO CHECK FOR NUMERIC (POSITIVE INTEGERS) VALUES ONLY
    	if (StringUtils.isPositiveInteger(refNumber) && StringUtils.isPositiveInteger(amount)) {
    		
    		BigInteger refNumberBI = new BigInteger(refNumber);
    		BigInteger amountBI = new BigInteger(amount);
    		BigInteger result = null;
    		
    		if  (refNumberBI.intValue() > 0 && amountBI.intValue() > 0) {
    			
    			//GET ORDER BY REFERENCE NUMBER
    			Order order = orderService.getOrderByRefNumber(refNumberBI);
    			
    			if (order != null) {
    				
    				//IF ORDER IS NOT DISPATCHED, UPDATE
    				if (!order.getStatus().equalsIgnoreCase(OrderStatusTypes.DISPATCHED.getStatus())) {
    			
		    			//UPDATE ORDER WITH NEW NUMBER OF BRICKS
		    			order.setAmount(amountBI);
		    			
						//SET AUDIT FIELDS
						order.setModifiedBy("BRICKS-APP");
						order.setModifiedDate(Timestamp.from(Instant.now()));
		
		    			orderService.update(order);
		    			
		    			//RETURN ORDER REFERENCE NUMBER AS A JSON RESPONSE TO THE CLIENT
		    			result = order.getReferenceNumber();
		    			return new ResponseEntity<BigInteger>(result, HttpStatus.OK);
    			} else {
	          		logger.warn("The order has been already dispatched");
	    			//RETURN HTTP 400 BAD REQUEST ERROR MESSAGE
	    			return new ResponseEntity<BigInteger>(result, HttpStatus.BAD_REQUEST);
          		}
        	  } else {
        		logger.warn("The order does not exists");
        		}
    		} else {
        		logger.warn("The refernce number and the bricks amount must be > 0");
        	}
    	} else {
    		logger.warn("The refernce number and the bricks amount must be a positive integer");
    	}
    	
    	return null;
    }

    @RequestMapping("/fulfillOrder")
    public ResponseEntity<BigInteger> fulfillOrder(@RequestParam(value="refNumber", defaultValue="0") String refNumber) {
    	
		//DEFAULT VALUE AUTOMATICALLY PROVIDE A VALID VALUE IF NOT PRESENT (NULL OR "")
    	//BUT I STILL NEED TO CHECK FOR NUMERIC (POSITIVE INTEGERS) VALUES ONLY
    	if (StringUtils.isPositiveInteger(refNumber)) {
    		
    		BigInteger refNumberBI = new BigInteger(refNumber);
    		BigInteger result = null;
    		
    		if  (refNumberBI.intValue() > 0) {
    			
    			//GET ORDER BY REFERENCE NUMBER
    			Order order = orderService.getOrderByRefNumber(refNumberBI);
    			
    			if (order != null) {

	    			//UPDATE ORDER STATUS TO 'DISPATCHED'
    				order.setStatus(OrderStatusTypes.DISPATCHED.getStatus());
	    			
					//SET AUDIT FIELDS
					order.setModifiedBy("BRICKS-APP");
					order.setModifiedDate(Timestamp.from(Instant.now()));
	
	    			orderService.update(order);
	    			
	    			result = order.getReferenceNumber();
	    			
	    			//RETURN ORDER REFERENCE NUMBER AS A JSON RESPONSE TO THE CLIENT
	    			return new ResponseEntity<BigInteger>(result, HttpStatus.OK);

    			} else {
	          		logger.warn("The order does not exists");
	    			//RETURN HTTP 400 BAD REQUEST ERROR MESSAGE
	    			return new ResponseEntity<BigInteger>(result, HttpStatus.BAD_REQUEST);
          		}
    		}
        	else {
        		logger.warn("The refernce number and the bricks amount must be > 0");
        	}
    	}  	else {
    		logger.warn("The refernce number and the bricks amount must be a positive integer");
    	}
    	
    	return null;
    }

    @RequestMapping("/countOrders")
    public long countOrders() {
    	
		//GET ORDERS COUNT
		long orderCount = orderService.countOrders();
    			
    	return orderCount;
    }

    @RequestMapping("/ordersReport")
    public Object ordersReport(@RequestParam(value="status", defaultValue="") String status) {
    	
		//GET ORDERS REPORT
		Object ordersReport = orderService.getOrdersByStatus(status);
    	return ordersReport;
    }

}



