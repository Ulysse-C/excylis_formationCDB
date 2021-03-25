//package com.excilys.formationcdb.dto.dao;
//
//import java.util.Set;
//
//import javax.persistence.CascadeType;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.ManyToMany;
//import javax.persistence.Table;
//
//@Entity
//@Table(name = "user_authorities")
//public class UserAuthPersist {
//
//	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO) 
//	@Column(name = "id")
//	private int auth_id;
//	private int user_id;
//	private String authority;
//
//	@ManyToMany(cascade = CascadeType.PERSIST, mappedBy = "id")
//	private Set<UserPersist> userPersistList;
//
//	public int getId() {
//		return auth_id;
//	}
//
//	public void setId(int id) {
//		this.auth_id = id;
//	}
//
//	public int getUser_id() {
//		return user_id;
//	}
//
//	public void setUser_id(int user_id) {
//		this.user_id = user_id;
//	}
//
//	public String getAuthority() {
//		return authority;
//	}
//
//	public void setAuthority(String authority) {
//		this.authority = authority;
//	}
//}
