package com.project.couponsys.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.project.couponsys.entities.Purchase;

public interface PurchaseRepo extends CrudRepository<Purchase, Integer> {
	
	//get all coupons purchased by a customer (find by id)
	public List<Purchase> findByCustomerid (Integer customerId);
	public List<Purchase> findByCouponid (Integer coupid);
	public List<Purchase> findByCustomeridAndCouponid(Integer customerId, Integer couponId);
}
