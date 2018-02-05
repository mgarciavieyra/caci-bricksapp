package com.caci.test.bricks.data.util;

public class SqlScriptHelper {
	
	public static StringBuilder getLastOrder() {
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT TOP 1 * FROM SALE_ORDER ORDER BY ID DESC");
				
		return sb;
	}	
	
}
