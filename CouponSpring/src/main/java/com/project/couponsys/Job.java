package com.project.couponsys;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.couponsys.entities.Coupon;
import com.project.couponsys.entities.Purchase;
import com.project.couponsys.repository.CouponRepo;
import com.project.couponsys.repository.PurchaseRepo;
import com.project.couponsys.repository.CouponRepo;

@Service
public class Job implements Runnable{
	
	@Autowired
	CouponRepo couponRepo;
	@Autowired
	PurchaseRepo purchaseRepo;
	public Job ()
	{
	}

		
	@Override
	public void run() {
		while (true)
			{
			Calendar tomorrow = Calendar.getInstance();
			//tomorrows day of month
			long currentTime = System.currentTimeMillis();
			int dayOfMonth = tomorrow.get(Calendar.DAY_OF_MONTH) + 1;
			tomorrow.set(Calendar.HOUR_OF_DAY, 0);
			tomorrow.set(Calendar.MINUTE, 0);
			tomorrow.set(Calendar.SECOND, 0);
			tomorrow.set(Calendar.DAY_OF_MONTH, dayOfMonth);
		    long runAt = tomorrow.getTimeInMillis();
		    //long wakeUpTime = 86400000;
		    List<Coupon> allCoupons = (List<Coupon>) couponRepo.findAll();
		    if (!allCoupons.isEmpty()) {
			    for (Coupon coupon : allCoupons) {
					if (currentTime<coupon.getEndDate().getTime()) {
						for (Purchase pur : purchaseRepo.findByCouponid(coupon.getCouponid())) {
							purchaseRepo.delete(pur);
						}
						couponRepo.delete(coupon);
					}
				}
			}
		    try {
				Thread.sleep(runAt);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
}


