package com.excilys.formationCDB.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.excilys.formationCDB.exception.CustomSQLException;

public class Page<E> {
	
	public static final int DEFAULT_PAGE_SIZE = 10;
	public final static int PAGEINDEX_SIZE = 7;
	public final static int PAGEINDEX_BEFORE_CURRENT_PAGE = 4;
	
	private int size;
	private int number;
	private String table;
	private List<Optional<E>> content;
	private Page nextPage;
	private Page previousPage;


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

	public Page getPreviousPage() {
		Page previousPage = this;
		if (this.previousPage == null) {
			if (number > 1) {
				previousPage = new Page(this.size, this.number - 1, this.table);
				this.previousPage = previousPage;
			}
		} else {
			previousPage = this.previousPage;
		}
		return previousPage;
	}

	public Page getNextPage() {
		if (this.nextPage == null) {
				this.nextPage = new Page(this.size, this.number + 1, this.table);
		}
		return nextPage;
	}
	
	public void setNextPage(Page nextPage) {
		this.nextPage = nextPage;
	}

	public void setPreviousPage(Page previousPage) {
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
			compensation = PAGEINDEX_BEFORE_CURRENT_PAGE - number +1;
		}
		while (computerNB / size >= indexTo + number && indexTo + 1 + PAGEINDEX_BEFORE_CURRENT_PAGE - compensation < PAGEINDEX_SIZE) {
			indexTo++;
		}
		return indexTo + number;
	}
}
