package com.project.couponsys.facade;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import com.project.couponsys.PrintingUtils;
import com.project.couponsys.entities.Coupon;
import com.project.couponsys.entities.CouponCategory;
import com.project.couponsys.entities.Customer;
import com.project.couponsys.entities.Purchase;
import com.project.couponsys.repository.CouponRepo;
import com.project.couponsys.repository.CustomerRepo;
import com.project.couponsys.repository.PurchaseRepo;
@Primary
@Component
public class CustomerFacade extends LoginFacade{

	@Autowired
	CouponRepo couponRepo;
	
	@Autowired
	CustomerRepo customerRepo;
	
	@Autowired
	PurchaseRepo purchaseRepo;
	
	/*********CONSTRUCTOR FOR CUSTOMER***********/
	
	public CustomerFacade() {
	}
	
	/*****************MAIN MENU******************/
	
	public void mainMenu (Customer cust)
	{
		final String custIdName = "ID: "+cust.getCustomerId()+" Name: "+cust.getFname()+" "+cust.getLname();
		boolean end = false;
		System.out.println("Hello "+cust.getFname()+" "+cust.getLname()+"! What would you like to do?\n********************");

		while(!end)
		{
			try {
			System.out.println("*Purchase a coupon (0)\n"
				+ "*Return ALL coupon purchase history (1)\n"
				+ "*Return coupon purchase history by CATEGORY (2)\n"
				+ "*Return coupon purchase history by PRICE (3)\n"
				+ "*Get client info (4)"
				+ "*Log out (9)");
			Integer mainMenuChoice = PrintingUtils.getIntFromUser();
			switch (mainMenuChoice) {
			case 0:
				System.out.println("***PURCHASE A COUPON***");
				System.out.println("MENU: *View by company (0)\n"
						+ "      *View by Category (1)\n"
						+ "      *View by Price (2)"
						+ "      *Back (any other key)");
				Integer uc = PrintingUtils.getIntFromUser();
					switch (uc) {
					case 0:
						while (true)
						{
							
							System.out.print("Type in the ID of the company: ");
								System.out.println();
								Integer compid = userChoice();
								if (compid == null) {
									break;
								}
								try {
								List<Coupon> couponsFromCompany = couponRepo.findByCompanyid(compid);
								for (Coupon coupon : couponsFromCompany) {
									System.out.println(coupon.getTitle()+" ("+coupon.getCouponid()+"), ");
								}
								System.out.println("Type in the ID of the coupon: ");
								Integer coupid = PrintingUtils.getIntFromUser();
								if (couponRepo.findById(coupid).isPresent())
								{
									purchaseCoupon(couponRepo.findById(coupid).get(), cust);
									break;
								}
								else
									System.out.println("The name of the coupon doesn't exist, try again");
							}catch (Exception e) {
								System.out.println("Can't find a company with that name");
							}
					}
					
						break;
					case 1:
						while(true)
						{
							System.out.print("Type in the name of the category: ");
							System.out.print("(");
							for (int i = 0; i < CouponCategory.values().length; i++) {
								System.out.print(CouponCategory.values()[i]+", ");
							}
							System.out.print(")");
							String catstring = strUserChoice();
							if (catstring == null) {
								break;
							}
							catstring.toUpperCase();
							CouponCategory cat = isCatExists(catstring);
							if (cat == null) {
								System.err.println("Category doesn't exists");
								continue;
							}
							List<Coupon> couponsFromCat = couponRepo.findByCategory(cat);
							if (couponsFromCat.isEmpty()) {
								System.out.println("Could not find any coupons from the given category");
							}
							else
							{
								for (Coupon coupon : couponsFromCat) {
									System.out.print(coupon.getTitle()+"("+coupon.getCouponid()+"), ");
								}
							}
							
							System.out.println();
							System.out.print("Type the id of the coupon you wish to purchase: ");
							Integer coupid = PrintingUtils.getIntFromUser();
							if (couponRepo.findById(coupid).isPresent())
							{
								purchaseCoupon(couponRepo.findById(coupid).get(), cust);
								break;
							}
							else
								System.out.println("The id of the coupon you chose does not exist");
						}
						break;
					case 2:
						while(true)
						{
							System.out.print("Type the maximum price: ");
							uc = userChoice();
							if (uc == null) {
								break;
							}
							double price = 0;
							price = (double) uc;
							List<Coupon> coupsByPrice = couponRepo.findByPriceLessThan(price);
							if(coupsByPrice.isEmpty())
							{
								System.out.println("Couldn't find any coupons in the given range");
							}
							else
							{
								for (Coupon coupon : coupsByPrice) {
									System.out.print(coupon.getTitle()+"("+coupon.getCouponid()+"), ");
								}
							}
							System.out.println("Type ID of the coupon you want to purchase: ");
							Integer coupid = PrintingUtils.getIntFromUser();
							if (couponRepo.findById(coupid).isPresent()) {
								purchaseCoupon(couponRepo.findById(coupid).get(), cust);
								break;
								
							}
						}
						break;
					default:
						break;
				}
				break;
				
			case 1:
				List<Purchase> purchaseHistory = purchaseRepo.findByCustomerid(cust.getCustomerId());
				System.out.println("***RETURN PURCHASE HISTORY***");
				if (!purchaseHistory.isEmpty()) {
					System.out.println(custIdName+" has bought: ");
					for (Purchase pur : purchaseHistory) {
						System.out.println(pur.printInfo());
					}
				}
				else
					System.out.println("Couldn't find any purchase history for customer ID: "+cust.getCustomerId());
				break;
			case 2:
				List<Purchase> purHistory = purchaseRepo.findByCustomerid(cust.getCustomerId());
				System.out.println("***RETURN PURCHASE HISTORY BY CATEGORY***");
				System.out.print("Type in the name of the category: ");
				System.out.print("(");
				for (int i = 0; i < CouponCategory.values().length; i++) {
					System.out.print(CouponCategory.values()[i]+", ");
				}
				System.out.print(")");
				String catstring = strUserChoice();
				if (catstring == null) {
					break;
				}
				catstring.toUpperCase();
				CouponCategory cat = isCatExists(catstring);
				if (cat == null) {
					System.err.println("Category doesn't exists");
					continue;
				}
				List<Coupon> couponsFromCat = couponRepo.findByCategory(cat);
				if (!purHistory.isEmpty()) 
				{
					for (Purchase pur : purHistory) {
						for (Coupon coupon : couponsFromCat) {
							if (coupon.getCouponid() == pur.getCouponid()) {
								System.out.println(pur.printInfo());
							}
						}
					}
				}
				
				else
					System.out.println("Couldn't find any purchase history for customer ID: "+cust.getCustomerId());
				break;
				
			case 3:
				System.out.println("***RETURN PURCHASE HISTORY BY PRICE***");
				uc = userChoice();
				if (uc == null) {
					break;
				}
				double price = (double) uc;
				List<Purchase> purchaseHistoryPrice = purchaseRepo.findByCustomerid(cust.getCustomerId());
				List<Coupon> couponsByPrice = couponRepo.findByPriceLessThan(price);
				if (!purchaseHistoryPrice.isEmpty() && !couponsByPrice.isEmpty()) {
					for (Coupon coupon : couponsByPrice) {
						for (Purchase pur : purchaseHistoryPrice) {
							if (pur.getCouponid() == coupon.getCouponid()) {
								System.out.println(pur.printInfo());
							}
						}
					}
				}
				else
					System.out.println("Couldn't find any purchase history for customer ID: "+cust.getCustomerId());
				
				break;
			case 4:
				System.out.println("***RETURN CUSTOMER INFO***");
				System.out.println(cust.printInfo());
				break;
			case 9:
				System.out.println("LOGGING OUT . . .");
				end = true;
				break;
			default:
				System.out.println("you didn't enter a valid choice, try again");
				break;
			}
			
			}catch (Exception e) {
				String exMsg = "You entered the wrong type of variable (only number in range is acceptable)";
				System.out.println(exMsg+"\n"+e.getMessage());
			}

		}
	}
		
		
		
	private void purchaseCoupon(Coupon coup, Customer cust)
	{

		Purchase purchase = new Purchase();
		purchase.setCouponid(coup.getCouponid());
		purchase.setCustomerid(cust.getCustomerId());
		System.out.println("Are you sure you want to purchase "+coup.getTitle()+"? (y/n)");
		char yn = PrintingUtils.getStringFromUser().charAt(0);
		while (true)
			{
			switch (yn) {
			case 'y':
				if (!isPurchased(coup, cust)) {
					purchaseRepo.save(purchase);
					System.out.println("Coupon ID: "+coup.getCouponid()+", Name: "+coup.getTitle()+" Was purchased succesfuly!");
				}

				else
					System.out.println("Can only purchase a specific coupon once");
				break;
			case 'n':
				System.out.println("Going back to the main menu");
				break;
			default:
				System.out.println("invalid choice, try again");
				yn = PrintingUtils.getStringFromUser().charAt(0);
				continue;

			}
			break;
			}
	}
	
	private boolean isPurchased(Coupon coup, Customer cust) 
	{
		if (purchaseRepo.findByCustomeridAndCouponid(cust.getCustomerId(), coup.getCouponid()).isEmpty()) {
			return false;
		}
		return true;
	}

}
