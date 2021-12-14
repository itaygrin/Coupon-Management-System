package com.project.couponsys.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Companies")
public class Company {				//INHERITS FROM BUSINESS 
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column (name = "company_id")
	private Integer companyId;
	
	@Column (name = "company_name")
	private  String name; 
	
	@Column
	private String email;
	
	@Column
	private String password;		
	
	public Company(String email, String password, Integer companyId, String name) {
		this.companyId = companyId;
		this.email = email;
		this.password = password;
		this.name = name;
	}
	
	public Company() {
		super();
	}



	public  String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	
	
	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String printInfo() {
		String str = " Email:"+getEmail()+" Password:"+getPassword();
		str += " ID:"+getCompanyId();
		str += " Name:"+getName();
		return str;
		
		}
	
}
