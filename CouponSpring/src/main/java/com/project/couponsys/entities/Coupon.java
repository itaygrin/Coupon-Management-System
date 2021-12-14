
package com.project.couponsys.entities;

import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity (name = "Coupons")
public class Coupon {

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	@Column (name = "coupon_id")
	private Integer couponid;			
	
	@Column (name = "company_id")
	private Integer companyid;
	
	@Column
	private CouponCategory category;
	
	@Column
	private String title;			//name of coupon
	
	@Column
	private String description;
	
	@Column (name = "start_date")
	private Date startDate;			//date of creation
	
	@Column (name = "end_date")
	private Date endDate;			//expiry date. 	(class/method/DAO "JOB" will delete every expired coupon)
	
	@Column
	private int amount;			//how much coupons in stock
	
	@Column
	private double price;		//cost of coupon for customer
	
	@Column
	private String img;			//path name of coupon image
	
	@ManyToMany(mappedBy = "coupons", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	private List<Customer> customers;
	
	public Coupon(Integer company_id, int category_id,CouponCategory category,String title, String description, Date start_date,
			Date end_date, int amount, double price, String img) {
		this.companyid = company_id;
		this.category = category;
		this.title = title;
		this.description = description;
		this.startDate = start_date;
		this.endDate = end_date;
		this.amount = amount;
		this.price = price;
		this.img = img;
	}
	
	

	public Coupon() {			//empty constructor 
	}



	public Integer getCouponid() {
		return couponid;
	}
	

	public void setCouponid(Integer coupon_id) {
		this.couponid = coupon_id;
	}

	public Integer getCompanyid() {
		return companyid;
	}

	public void setCompanyid(Integer company_id) {
		this.companyid = company_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date start_date) {
		this.startDate = start_date;
	}

	public Date getEndDate() {
		return endDate;
	}

	public CouponCategory getCategory() {
		return category;
	}

	public void setCategory(CouponCategory category) {
		this.category = category;
	}

	public void setEndDate(Date end_date) {
		this.endDate = end_date;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}
	
	public String printInfo ()
	{
		String str = "Coupon ID:"+getCouponid()+" Company ID:"+getCompanyid()+
				" Category type:"+getCategory()+" Title:"+getTitle()+" Description:"+getDescription()+" Start Date:"+
				getStartDate()+" End date:"+getEndDate()+" Amount:"+getAmount()
				+" Price:"+getPrice()+" IMG path:"+getImg();
		return str;
	}
	

	
}
