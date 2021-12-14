package com.project.couponsys.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.project.couponsys.entities.Coupon;
import com.project.couponsys.entities.CouponCategory;

public interface CouponRepo extends CrudRepository<Coupon, Integer>{
	
	//findAll - defined method for spring JPA
	
	//findById - defined method for spring jpa
	
	//get all coupons from specific company
	public List<Coupon> findByCompanyid (Integer compid);
	
	//get specific coupon by company id and coupon id (on index == 0)
	public List<Coupon> findByCouponidAndCompanyid (Integer coupid, Integer compid);
	
	//get company coupons of a category
	public List<Coupon> findByCompanyidAndCategory (Integer compid, CouponCategory cat);
	
	//get company coupons by max price
	public List<Coupon> findByCompanyidAndPriceLessThan (Integer compid, double maxprice);
	
	public List<Coupon> findByCategory (CouponCategory cat);
	
	public List<Coupon> findByPriceLessThan (double maxprice);
	
	public Optional<Coupon> findByTitle (String title);
	
	
	
}



