package com.project.couponsys.entities;

import javax.persistence.Entity;


public class Administrator extends Client {
	//inherits email and password, doesn't have its own properties.

	public Administrator() {
		setEmail("admin@admin.com");
		setPassword("admin");
		
	}
}
	
	
