package com.excilys.formationCDB.model;

public class Company {
	
	private int id;
	private String name;
	
	public static class CompanyBuilder{
		private int id;
		private String name;
		
		public CompanyBuilder(int id) {
			this.id = id;
		}
		
		public CompanyBuilder setName(String name) {
			this.name = name;
			return this;
		}
		
		public Company build() {
			return new Company(id, name);
		}
	}
	
	private Company(int id, String name) {
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
