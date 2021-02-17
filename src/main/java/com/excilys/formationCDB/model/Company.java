package com.excilys.formationCDB.model;

public class Company {
	
	private int id;
	private String name;
	
	public Company(String name, int id) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(name).append(", id: ").append(id);
		return stringBuilder.toString();
	}
}
