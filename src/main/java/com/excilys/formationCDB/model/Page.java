package com.excilys.formationCDB.model;

import java.util.ArrayList;
import java.util.List;

public class Page<E> {
	private int size;
	private int number;
	private String table;
	private List<E> content;
	private Page nextPage;
	private Page previousPage;

	public Page(int size, int number, String table) {
		content = new ArrayList<>();
		this.size = size;
		this.number = number;
		this.table = table;
	}

	public int getSize() {
		return size;
	}

	public int getNumber() {
		return number;
	}

	public void setContent(List<E> content) {
		this.content = content;
	}

	public String getTable() {
		return table;
	}

	public List<E> getContent() {
		return content;
	}

	public Page getPreviousPage() {
		Page previousPage = this;
		if (this.previousPage == null) {
			if (number > 1) {
				previousPage = new Page(this.size, this.number - 1, this.table);
			}
		} else {
			previousPage = this.previousPage;
		}
		return previousPage;
	}

	public Page getNextPage() {
		Page nextPage;
		if (this.nextPage == null) {
				nextPage = new Page(this.size, this.number + 1, this.table);
		} else {
			nextPage = this.nextPage;
		}
		return nextPage;
	}
	
	public void setNextPage(Page nextPage) {
		this.nextPage = nextPage;
	}

	public void setPreviousPage(Page previousPage) {
		this.previousPage = previousPage;
	}
}
