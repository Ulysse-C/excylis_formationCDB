package com.excilys.formationcdb.controller.web.session;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import com.excilys.formationcdb.model.Page.SortAttribute;
import com.excilys.formationcdb.model.Page.SortOrder;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class DashBoardSessionVariable {
	private int pageSize = 10;
	private SortAttribute sortAttribute = SortAttribute.COMPUTER_ID;
	private SortOrder sortOrder = SortOrder.DESC;

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int variable) {
		this.pageSize = variable;
	}

	public SortAttribute getSortAttribute() {
		return sortAttribute;
	}

	public void setSortAttribute(SortAttribute sortAttribute) {
		this.sortAttribute = sortAttribute;
		sortOrder = SortOrder.DESC;
	}

	public SortOrder getSortOrder() {
		return sortOrder;
	}
	
	public void flipSortOrder() {
		if (sortOrder == SortOrder.ASC) {
			sortOrder = SortOrder.DESC;
		} else {
			sortOrder = SortOrder.ASC;
		}
	}
	
}
