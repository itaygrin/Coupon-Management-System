package com.project.couponsys.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Customers")
public class Customer { 									//INHERITS FROM BUSINESS 
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column (name = "customer_id")	
	/*
	 * ^^^ CHECK WHY customerRepo.findAll() - throws resultSet Exception ^^
	 *  because of column name
	 */
	private Integer customerId;
	
	@Column
	private String email;
	
	@Column
	private String password;		
	
	@Column (name = "first_name")
	private String fname;
	@Column (name = "last_name")
	private String lname;
//	private ArrayList<Coupon> coupofcustArr = new ArrayList<Coupon>();
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "Purchase",
            joinColumns = {@JoinColumn(name = "customer_id")},
            inverseJoinColumns = {@JoinColumn(name = "coupon_id")}
    )
	private List<Coupon> coupons;
	
	public Customer(String email, String password, Integer customerId, String fname, String lname) {
		this.customerId = customerId;
		this.email = email;
		this.password = password;
		this.fname = fname;
		this.lname = lname;
	}
	
	

	public Customer() {
		super();
	}



	public String getFname() {
		return fname;
	}
	
	

	public Integer getCustomerId() {
		return customerId;
	}



	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
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



	public List<Coupon> getCoupons() {
		return coupons;
	}



	public void setCoupons(List<Coupon> coupons) {
		this.coupons = coupons;
	}



	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}
	
	public void setLname(String lname) {
		this.lname = lname;
	}
		
	/*public String arrtoString ()
	{
		String b = "";
		SmartArray arr = new SmartArray();
		for (int i=0; i<coupofcustArr.size(); i++){
			arr.addItem("\nID: "+coupofcustArr.get(i).getCoupon_id()+"\nName: "+coupofcustArr.get(i).getTitle()
			+"\nStart Date: "+coupofcustArr.get(i).getStart_date()+"\nEnd Date: "+coupofcustArr.get(i).getEnd_date()+"\n***********************");
			try {
				b += arr.getItem(i);
			} catch (Exception e) {
				
				e.printStackTrace();
			}
			
		}
		return b;
		
		
	}
		
	
	public ArrayList<Coupon> getCoupofcustArr() {
		return coupofcustArr;
	}

	public void setCoupofcustArr(ArrayList<Coupon> coupofcustArr) {
		this.coupofcustArr = coupofcustArr;
	}
	
	public void addToCoupofcustArr(Coupon coup)
	{
		this.coupofcustArr.add(coup);
	}

	*/
	
	public String printInfo() {
		
			String str = "ID: "+getCustomerId()+" Email:"+getEmail()+" Password:"+getPassword();
			str += " First name:"+getFname()+" Last name:"+getLname();
			return str;
		}

	
}