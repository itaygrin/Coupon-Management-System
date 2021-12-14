package com.project.couponsys.entities;

import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.Id;


public class BusinessClass extends Client {
	@Id
	private Integer id;			//general id property, will inherit to COMPANY and CUSTOMER.	also inherits email and password
	protected ArrayList <Coupon> coupArr = new ArrayList<Coupon>(); 
	
	public BusinessClass(String email, String password,Integer id) {

		super(email, password);
		this.id = id;
		
	}
	
	
	
	public BusinessClass() {
		super();
	}



	public Integer getId() {

		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public ArrayList<Coupon> getCoupArr() {

		return coupArr;
	}
	
	public void setCoupArr(ArrayList<Coupon> coupArr) {
		this.coupArr = coupArr;
	}
	
	@Override
	public String printInfo() {
		
		String str = "ID:"+getId();
		str += super.printInfo();
		return str;
	}
	
	
	
	
	



}
