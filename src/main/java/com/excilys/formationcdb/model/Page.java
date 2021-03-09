package com.excilys.formationcdb.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Page<E> {

	public static final int DEFAULT_PAGE_SIZE = 10;
	public final static int PAGEINDEX_SIZE = 10;
	public final static int PAGEINDEX_BEFORE_CURRENT_PAGE = 5;

	private int size;
	private int number;
	private String table;
	private List<Optional<E>> content;
	private Page<E> nextPage;
	private Page<E> previousPage;
	private SortAttribute sortName = SortAttribute.COMPUTER_ID;
	private SortOrder sortOrder = SortOrder.DESC;

	public enum SortOrder {
		ASC, DESC
	}

	public enum SortAttribute {
		COMPANY_NAME("company.name"), COMPUTER_NAME("computer.name"), COMPUTER_ID("computer.id"),
		COMPUTER_INTRODUCED("computer.introduced"), COMPUTER_DISCONTINUED("computer.discontinued");

		private String attribute;

		private SortAttribute(String attribute) {
			this.attribute = attribute;
		}

		public String getAttribute() {
			return this.attribute;
		}
	}

	public Page(int size, int number, String table) {
		content = new ArrayList<>();
		if (size < 0) {
			this.size = DEFAULT_PAGE_SIZE;
		} else {
			this.size = size;
		}
		this.number = number;
		this.table = table;
	}

	public int getSize() {
		return size;
	}

	public int getNumber() {
		return number;
	}

	public void setContent(List<Optional<E>> list) {
		this.content = list;
	}

	public String getTable() {
		return table;
	}

	public List<Optional<E>> getContent() {
		return content;
	}

	public Page<E> getPreviousPage() {
		Page<E> previousPage = this;
		if (this.previousPage == null) {
			if (number > 1) {
				previousPage = new Page<>(this.size, this.number - 1, this.table);
				this.previousPage = previousPage;
			}
		} else {
			previousPage = this.previousPage;
		}
		return previousPage;
	}

	public Page<E> getNextPage() {
		if (this.nextPage == null) {
			this.nextPage = new Page<>(this.size, this.number + 1, this.table);
		}
		return nextPage;
	}

	public void setNextPage(Page<E> nextPage) {
		this.nextPage = nextPage;
	}

	public void setPreviousPage(Page<E> previousPage) {
		this.previousPage = previousPage;
	}

	public int getPageIndexFrom() {
		int indexFrom = 0;
		while (indexFrom + number > 1 && indexFrom + PAGEINDEX_BEFORE_CURRENT_PAGE > 0) {
			indexFrom--;
		}
		return indexFrom + number;
	}

	public int getPageIndexTo(int computerNB) {
		int indexTo = 0;
		int compensation = 0;
		if (number <= PAGEINDEX_BEFORE_CURRENT_PAGE) {
			compensation = PAGEINDEX_BEFORE_CURRENT_PAGE - number + 1;
		}
		while (computerNB / size > indexTo + number
				&& indexTo + 1 + PAGEINDEX_BEFORE_CURRENT_PAGE - compensation < PAGEINDEX_SIZE) {
			indexTo++;
		}
		return indexTo + number;
	}

	public SortAttribute getSortName() {
		return sortName;
	}

	public void setSortName(SortAttribute sortName) {
		this.sortName = sortName;
	}

	public SortOrder getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(SortOrder sortOrder) {
		this.sortOrder = sortOrder;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + number;
		result = prime * result + size;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Page<E> other = (Page<E>) obj;
		if (number != other.number)
			return false;
		if (size != other.size)
			return false;
		return true;
	}
}