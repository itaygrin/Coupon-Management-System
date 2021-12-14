package com.project.couponsys.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Purchase")
public class Purchase {

	@Id
	@Column (name = "purchase_id")
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	Integer purchaseId;
	
	@Column (name = "customer_id")
	Integer customerid;
	
	@Column (name = "coupon_id")
	Integer couponid;

	public Integer getPurchaseId() {
		return purchaseId;
	}

	public void setPurchaseId(Integer purchaseId) {
		this.purchaseId = purchaseId;
	}

	public Integer getCustomerid() {
		return customerid;
	}

	public void setCustomerid(Integer customerid) {
		this.customerid = customerid;
	}

	public Integer getCouponid() {
		return couponid;
	}

	public void setCouponid(Integer couponid) {
		this.couponid = couponid;
	}
	
	public String printInfo()
	{
		String info = "||COUPON ID: "+getCouponid()+"		CUSTOMER ID: "+getCustomerid()+"||";
		return info;
	}
	
	
	
	
}
