package com.excilys.formationcdb.dto.dao.mapper;

import com.excilys.formationcdb.dto.dao.QComputerPersist;
import com.excilys.formationcdb.model.Page.SortOrder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.Expressions;

public class DaoMapper {
	public static Order getOrder(SortOrder sortOrder) {
		Order result;
		if (sortOrder == SortOrder.ASC) {
			result = Order.ASC;
		} else {
			result = Order.DESC;
		}
		return result;
	}
	
	public static OrderSpecifier<?> getSortedColumn(Order order, String fieldName){
	    Path<Object> fieldPath = Expressions.path(Object.class, QComputerPersist.computerPersist, fieldName);     
	    return new OrderSpecifier(order, fieldPath);
	}
}
