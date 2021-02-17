package com.excilys.formationCDB.model;

import java.util.ArrayList;
import java.util.List;

public class Page<E> {
	private int size;
	private int number;
	private String table;
	private List<E> content;

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

	public Page previousPage() {
		Page page = this;
		if (number > 1) {
			page = new Page(this.size, this.getNumber()-1, this.table);
		}
		return page;
	}

	public Page nextPage() {
		Page page = this;
		if (number > 0) {
			page = new Page(this.size, this.getNumber()+1, this.table);
		}
		return page;
	}
}
