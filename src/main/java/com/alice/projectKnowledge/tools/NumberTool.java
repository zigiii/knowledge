package com.alice.projectKnowledge.tools;

import java.math.BigDecimal;

public class NumberTool {

	public static Integer bigDecimalToInteger(Object obj){
		if (obj instanceof BigDecimal) {
			BigDecimal bd = (BigDecimal) obj;
			return bd.intValue();
		}
		return null;
	}
	
	public static Long bigDecimalToLong(Object obj){
		if (obj instanceof BigDecimal) {
			BigDecimal bd = (BigDecimal) obj;
			return bd.longValue();
		}
		return null;
	}
}
