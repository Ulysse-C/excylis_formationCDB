package com.excilys.formationcdb.model;

import java.util.ArrayList;
import java.util.List;

public class Page<E> {

	public static final int DEFAULT_PAGE_SIZE = 10;
	public static final int PAGEINDEX_SIZE = 10;
	public static final int PAGEINDEX_BEFORE_CURRENT_PAGE = 5;

	private int size;
	private int number;
	private List<E> content = new ArrayList<E>();
	private Page<E> nextPage;
	private Page<E> previousPage;
	private SortAttribute sortName = SortAttribute.COMPUTER_ID;
	private SortOrder sortOrder = SortOrder.DESC;
	private String search = "";
	private String table;

	public enum SortOrder {
		ASC, DESC
	}

	public enum SortAttribute {
		COMPANY_NAME("company.name"), COMPUTER_NAME("name"), COMPUTER_ID("id"), COMPUTER_INTRODUCED("introduced"),
		COMPUTER_DISCONTINUED("discontinued");

		private String attribute;

		SortAttribute(String attribute) {
			this.attribute = attribute;
		}

		public String getAttribute() {
			return this.attribute;
		}
	}

	public static class PageBuilder {
		private int size = DEFAULT_PAGE_SIZE;
		private int number = 1;
		private SortAttribute sortName = SortAttribute.COMPUTER_ID;
		private SortOrder sortOrder = SortOrder.DESC;
		private String search = "";

		public Page build() {
			return new Page(size, number, sortName, sortOrder, search);
		}

		public void setSize(Integer size) {
			if (size != null) {
				this.size = size;
			}
		}

		public void setNumber(Integer number) {
			if (number != null) {
				this.number = number;
			}
		}

		public void setSortName(SortAttribute sortName) {
			if (sortName != null) {
				this.sortName = sortName;
			}
		}

		public void setSortOrder(SortOrder sortOrder) {
			if (sortOrder != null) {
				this.sortOrder = sortOrder;
			}
		}

		public void setSearch(String search) {
			if (search != null) {
				this.search = search;
			}
		}

		public String getSearch() {
			return search;
		}
	}

	public Page(int size, int number) {
		content = new ArrayList<>();
		if (size < 0) {
			this.size = DEFAULT_PAGE_SIZE;
		} else {
			this.size = size;
		}
		this.number = number;
	}

	public Page(int size, int number, SortAttribute sortName, SortOrder sortOrder, String search) {
		this.size = size;
		this.number = number;
		this.sortName = sortName;
		this.sortOrder = sortOrder;
		this.search = search;
	}

	public int getSize() {
		return size;
	}

	public int getNumber() {
		return number;
	}

	public void setContent(List<E> list) {
		this.content = list;
	}

	public List<E> getContent() {
		return content;
	}

	public Page<E> getPreviousPage() {
		Page<E> previousPage = this;
		if (this.previousPage == null) {
			if (number > 1) {
				previousPage = new Page<>(this.size, this.number - 1);
				this.previousPage = previousPage;
			}
		} else {
			previousPage = this.previousPage;
		}
		return previousPage;
	}

	public Page<E> getNextPage() {
		if (this.nextPage == null) {
			this.nextPage = new Page<>(this.size, this.number + 1);
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

	public String getSortNameString() {
		return sortName.getAttribute();
	}

	public String getSortOrderString() {
		return sortOrder.name();
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

	public int getOffset() {
		return (number - 1) * size;
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
		if (this == obj) {
			return true;
		} else if (obj == null) {
			return false;
		} else if (getClass() != obj.getClass()) {
			return false;
		}
		Page<E> other = (Page<E>) obj;
		if (number != other.number) {
			return false;
		} else if (size != other.size) {
			return false;
		}
		return true;
	}

	public String getSearchQuery() {
		return "%" + search + "%";
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public String getSearch() {
		return search;
	}
}
