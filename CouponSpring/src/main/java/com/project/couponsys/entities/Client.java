package com.project.couponsys.entities;

import javax.persistence.Entity;


public abstract class Client {
	private String email;		
	private String password;		
	
	public Client (String email, String password)
	{
		this.email = email;
		this.password = password;
		
	}
	

	public Client() {
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
	
	public String printInfo()
	{
		String str = " Email:"+getEmail()+" Password:"+getPassword();
		return str;
	}
	
	
	
	

}
