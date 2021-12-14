package com.project.couponsys.facade;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import com.project.couponsys.PrintingUtils;
import com.project.couponsys.entities.Company;
import com.project.couponsys.entities.Coupon;
import com.project.couponsys.entities.CouponCategory;
import com.project.couponsys.repository.CompanyRepo;
import com.project.couponsys.repository.CouponRepo;

@Primary
@Component
public class CompanyFacade extends LoginFacade{
	
	@Autowired
	CouponRepo couponRepo;
	
	@Autowired
	CompanyRepo companyRepo;
	
//	private  Company comp;
//		public CompanyFacade(Company comp) {
//			super();
//			this.comp = comp;
//		}
	
		public CompanyFacade() {
		}
		
		
		/*****************MAIN MENU COMPANY******************/
		
		
		
		public void mainMenu (Company comp) {
			boolean end = false;
			System.out.println("Welcome "+comp.getName()+"! What would you like to do?\n***************");
			while(!end)
				{
				System.out.println("*Create a new coupon (0)\n"
						+ "*Update coupon details (1)\n"
						+ "*Delete coupon (2)\n"
						+ "*Return all coupons (3)\n"
						+ "*Return a specific coupon (by Category) (4)\n"
						+ "*Return a specific coupon (by Price) (5)\n"
						+ "*Get client info (6)\n"
						+ "*Log Out (9)\n");
				
				try {
					int mainMenuChoice = PrintingUtils.getIntFromUser();
					Coupon cp = null;
					switch (mainMenuChoice) {
						case 0:
							System.out.println("***CREATE NEW COUPON***");
							Integer choice = userChoice();
							if (choice == null) {
								break;
							}
							createCoupon(comp);
							break;
						case 1:
							//System.out.println(getComp().printInfo());
							System.out.println("***UPDATE A COUPON***");
							while (cp == null) {
							System.out.println("type in the ID of the coupon you wish to change: ");
							Integer idCoupUp = userChoice();
							if (idCoupUp == null) {
								break;
							}
//							cp = CouponDAO.getCouponByCompany(idCoupUp, getComp().getId());
							cp = couponRepo.findById(idCoupUp).get();
/*								idCoupUp = userChoice();
								if (idCoupUp == null) {
									break;
								}*/
							}
							if (cp!=null) {
								updateCoupon(cp);
							}
							break;
						case 2:
							System.out.println("***DELETE A COUPON***");
							while (cp == null) {
							System.out.println("type in the ID of the coupon you wish to delete: ");
							Integer idCoupDel = userChoice();
							if (idCoupDel == null) {
								break;
							}
//							cp = CouponDAO.getCouponByCompany(idCoupDel, getComp().getId());
							cp = couponRepo.findById(idCoupDel).get();
/*							idCoupDel = userChoice();
								if (idCoupDel == null) {
									break;
								}*/
							}
							if (cp!=null) {
								deleteCoupon(cp);
							}
							break;
							
						case 3:
							System.out.println("***RETURN ALL COUPONS***");
							List<Coupon> coupArr = couponRepo.findByCompanyid(comp.getCompanyId());
							for (Coupon coupon : coupArr) {
								System.out.println(coupon.printInfo()+"\n");
							}
							System.out.println();
							break;
						case 4:
							
							System.out.println("***RETURN COUPONS BY CATEGORY***");
							System.out.println("type in the Category you wish to view: ");
							while (true) {
								String catstring = strUserChoice();	
								if (catstring == null) {
									break;
								}
								catstring.toUpperCase();
								CouponCategory cat = isCatExists(catstring);
								if (cat != null) {
									List<Coupon> catCoupArr = couponRepo.findByCompanyidAndCategory(comp.getCompanyId(), cat);
									for (Coupon coupon : catCoupArr) {
										System.out.println(coupon.printInfo()+"\n");
									}
									break;
								}
								else {
									System.out.println("The category you entered doesn't exist, try again");
								}
							}
							break;
						case 5:
							System.out.println("***RETURN COUPONS BY PRICE***");
							System.out.println("type in the maximum price you wish to view: ");
							while (true)
							{
								Integer price = userChoice();	
								if (price == null) {
									break;
								}
								double priceDouble = (double)price;
								if (priceDouble<0) {
									System.out.println("You didn't enter a number / valid number");
									continue;
								}
								List<Coupon> coupPriceArr = couponRepo.findByCompanyidAndPriceLessThan(comp.getCompanyId(), priceDouble); 
								for (Coupon coupon : coupPriceArr) {
									System.out.println(coupon.printInfo()+"\n");
								}
								
								System.out.println();
								break;
							}
							break;
						case 6:
							System.out.println("***COMPANY INFO***");
							System.out.println(comp.printInfo());
							break;
						case 9:
							System.out.println("Logging out. . . ");
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
		


		/***********************COMPANY ON COUPON********************/
			
		/*
		 * creates a new coupon after checking all conditions
		 */
		
		public void createCoupon (Company comp)
		{
			createCouponUser(comp);

			
		}
		
		@Transactional
		private Coupon createCouponUser(Company comp) {
			//String coupid; //V
			Integer compid;	//V
			CouponCategory cat = null;	//V
			String title;	//V
			String description; // V
			Date sdate;
			Date edate;
			int amount = 0;
			double price = 0;
			String img;
			compid = companyRepo.findByName(comp.getName()).get().getCompanyId();
			
			System.out.println("Choose coupon title");
			while (true)
			{	
				title = PrintingUtils.getStringFromUser();
				boolean isExist = isCouponExists(title);
				if (isExist==true) {
					String exMsg = "Coupon already exists, cannot create a coupon with the same name";
					System.out.println(exMsg);
					continue;
				}
				if (title.isEmpty()) {
					String exMsg = "Illegal coupon name, cannot be null, try again";
					System.out.println(exMsg);
					continue;
				}
				break;
			}
			System.out.println("Choose the coupon category");
			System.out.print("(");
			for (int i = 0; i < CouponCategory.values().length; i++) {
				System.out.print(CouponCategory.values()[i]+", ");
			}
			System.out.print(")");
			while (true)
			{
				String catChoice;
				catChoice = PrintingUtils.getStringFromUser();
				for (int i = 0; i < CouponCategory.values().length; i++) {
					if (catChoice.equals(CouponCategory.values()[i].toString())) {
						cat = CouponCategory.values()[i];
						break;
					}
				}
				if (cat == null) {
					System.out.println("The category you chose doesn't exist, try again");
				}
				else
					break;
			}
			
			System.out.println("Choose a description for you new coupon");
			while (true)
			{
				description = PrintingUtils.getStringFromUser();
				break;
			}

			while (true)
			{
				long millis=System.currentTimeMillis();  
				java.sql.Date date=new java.sql.Date(millis);
				sdate = date;
				break;
			}
			System.out.println("Set up expiry date:");
			while (true)
			{
				System.out.println("Exp. day:");
				try {
				int day = PrintingUtils.getIntFromUser();
				if (day<1 || day>31) {
					System.out.println("Wrong values (between 1-31). try again");
					throw new Exception();
				}
				System.out.println("Exp. month:");
				int month = PrintingUtils.getIntFromUser();
				if (month<1 || month>12) {
					System.out.println("Wrong values (between 1-12). try again");
					throw new Exception();
				}
				System.out.println("Exp. year:");
				int year = PrintingUtils.getIntFromUser();
				int curyear = Calendar.getInstance().get(Calendar.YEAR);
				Date endOfTheDate = new Date(year-1900, month-1, day);
		//		System.out.println(endOfTheDate.toString());
		//		System.out.println(sdate.toString());

					if (endOfTheDate.compareTo(sdate)>0) {
						edate = endOfTheDate;
						break;
					}
					else
						System.out.println("Wrong values (need to be more than current date). try again");

				
				/************!!!!!!!!!CONTINUE HERE!!!!!**************/
				
				}catch (Exception e) {
					System.out.println("enter a number");
				}
				
			}

			System.out.println("Choose a price for your coupon: ");
			while(true)
			{
				try {
				price = PrintingUtils.getDoubleFromUser();
				if (price<0) {
					throw new Exception();
				}
				}catch (IOException e) {
					System.out.println("enter a double");
				}
				catch (Exception e) {
					System.out.println("Price cannot be lower than 0. try again");
				}
				break;
			}
			System.out.println("Link an image to the coupon (enter path): ");
				String imgchk = PrintingUtils.getStringFromUser();
				File f = new File(imgchk);
				if(!f.exists() || f.isDirectory()) { 
				    System.out.println("Invalid path. no img to show.");
				    img = "no image";
				}
				else
					img = imgchk;
				System.out.println("How many coupons would you like to add?");
				while (true)
				{
					try {
					amount = PrintingUtils.getIntFromUser();
					if (amount < 0) {
						throw new Exception();
					}
					}catch (IOException e) {
						System.out.println("Amount must be more than 0");
					}catch (Exception e) {
						System.out.println("Enter a number!");
					}
					break;
					
				}
				Coupon newCoupon = new Coupon(compid, cat.ordinal(), cat, title, description, sdate, edate, amount, price, img);
				couponRepo.save(newCoupon);
				System.out.println("Coupon ID: "+couponRepo.findByTitle(title).get().getCouponid()+" Name: "+title+", was created succesfuly!");
				return newCoupon;
				

		}

		@Transactional
		private void updateCoupon(Coupon coup) {

			System.out.println("What would you like to change?\n"
					+ "*Coupon's category (0)\n"
					+ "*Coupon's title (1)\n"
					+ "*Coupon's description (2)\n"
					+ "*Coupon's expiry date (3)\n"
					+ "*Coupon's amount (4)\n"
					+ "*Coupon's price (5)\n"
					+ "Coupon's image (6)\n"
					+ "*Back to main menu (9)");
			

			
			try {
			int upchoice = PrintingUtils.getIntFromUser();
			switch (upchoice) {
				case 0:
					while (true)
					{
						String catChoice = PrintingUtils.getStringFromUser();
						for (int i = 0; i < CouponCategory.values().length; i++) {
							if (catChoice.equals(CouponCategory.values()[i].toString())) {
								coup.setCategory(CouponCategory.values()[i]);
								couponRepo.save(coup);
								System.out.println("Category succesfuly changed to "+catChoice);
								break;
							}
						}
						if (catChoice == null) {
							System.out.println("The category you chose doesn't exist, try again");
						}
						else
							break;
					}
					break;
				case 1:
					while (true)
					{	/// WORK HERE !!!! 
						String title = PrintingUtils.getStringFromUser();
						boolean isExist = isCouponExists(title);
						if (isExist==true) {
							String exMsg = "Coupon already exists with the same name, try again";
							System.out.println(exMsg);
							continue;
						}
						if (title.isEmpty()) {
							String exMsg = "Illegal coupon name, cannot be null, try again";
							System.out.println(exMsg);
							continue;
						}
						coup.setTitle(title);
						couponRepo.save(coup);
						System.out.println("Title succesfuly changed to "+title);
						break;
					}
					break;
				case 2:
					
						String description = PrintingUtils.getStringFromUser();
						coup.setDescription(description);
						couponRepo.save(coup);
						System.out.println("Description succesfuly changed to "+description);

					break;
					
				case 3:
					Date edate;
					while (true)
					{
						
						System.out.println("Exp. day:");
						try {
						int day = PrintingUtils.getIntFromUser();
						if (day<1 || day>31) {
							System.out.println("Wrong values (between 1-31). try again");
							throw new Exception();
						}
						System.out.println("Exp. month:");
						int month = PrintingUtils.getIntFromUser();
						System.out.println(month);
						if (month<1 || month>12) {
							System.out.println("Wrong values (between 1-12). try again");
							throw new Exception();
						}
						System.out.println("Exp. year:");
						int year = PrintingUtils.getIntFromUser();
						int curyear = Calendar.getInstance().get(Calendar.YEAR);
						if (year<curyear) {
							System.out.println("Wrong values (need to be equal or more than current year). try again");
							throw new Exception();
						}
						Date endOfTheDate = new Date(year-1900, month-1, day);
						/************!!!!!!!!!CONTINUE HERE!!!!!**************/
						edate = endOfTheDate;
						if (endOfTheDate.compareTo(edate)>0) {
							edate = endOfTheDate;
							coup.setEndDate(edate);
							couponRepo.save(coup);
							System.out.println("Expiry date succesfuly changed to "+edate);
							break;
						}
						else
							System.out.println("Wrong values (need to be more than current date). try again");
						}catch (Exception e) {
							System.out.println("enter a number");
						}
						
					}

					break;
				case 4:
					while (true)
					{
						try {
						int amount = PrintingUtils.getIntFromUser();
						if (amount < 0) {
							throw new Exception();
						}
						coup.setAmount(amount);
						couponRepo.save(coup);
						System.out.println("Amount succesfuly changed to "+amount);
						}catch (IOException e) {
							System.out.println("Amount must be more than 0");
						}catch (Exception e) {
							System.out.println("Enter a number!");
						}

						break;
						
					}
					break;
				case 5:
					while(true)
					{
						try {
						Double price = PrintingUtils.getDoubleFromUser();
						if (price<0) {
							throw new Exception();
						}
						coup.setPrice(price);
						couponRepo.save(coup);
						System.out.println("Price succesfuly changed to "+price);
						}catch (IOException e) {
							System.out.println("enter a double");
						}
						catch (Exception e) {
							System.out.println("Price cannot be lower than 0. try again");
						}
						break;
					}
					break;
				case 6:
					try {
					String imgchk = PrintingUtils.getStringFromUser();
					File f = new File(imgchk);
					if(!f.exists() || f.isDirectory()) { 
					    System.out.println("Invalid path. no img to show.");
					    coup.setImg("no image");
					}
					else
						coup.setImg(imgchk);
					}catch (Exception e) {
						System.out.println("You must enter a String. img path remained unchanged.");
						break;
					}
					couponRepo.save(coup);
					System.out.println("IMG path succesfuly changed to "+coup.getImg());			
					break;
				case 9:
					System.out.println("Returning to main menu. . . ");
					break;
					default:
						System.out.println("Invalid key, Returning to main menu. . . ");
			}
			
			}catch (Exception e) {
				String exMsg = "out of byte range, returning to main menu";
				System.out.println(exMsg);
				e.printStackTrace();
			}
			
		}
		
		public boolean isCouponExists(String coupName)
		{
			//not needed - List<Coupon> compCoups = couponRepo.findByCompanyid(compId);
			List<Coupon> allCoups = (List<Coupon>) couponRepo.findAll();
			if (!allCoups.isEmpty()) {	
				for (Coupon coup : allCoups) {							 
					if (coup.getTitle().equals(coupName)) {
						return true;
					}
				}
			}
			return false;
		}

		private void deleteCoupon(Coupon cp) {
			char choice = 'z';
			boolean chooseright = false;
			
			while(!chooseright)
			{
				System.out.println("Are you sure you want to delete '"+cp.getTitle()+"'? (y/n)");
				try {
					choice = PrintingUtils.getStringFromUser().charAt(0);
					Character.toLowerCase(choice);
				}catch (Exception e){
					System.out.println("you didn't type a char type, try again.");
					
				}
				if (choice == 'y' || choice == 'n') {
					chooseright = true;
				}
			}
			chooseright = false;
			while (!chooseright)
				{
				switch (choice) {
				case 'y':
	
					System.out.println("Coupon "+cp.getCouponid()+" deleted succesfuly");
					couponRepo.delete(cp);
//					
//					//deletes all purchase log of company coupons from customers
//					CouponDAO.deletePurchaseLogOfSpecCoupon(cp.getCoupon_id(), getComp().getId());	
//					ArrayList<Coupon> allCoups = CouponDAO.getCompanyCoupons(getComp().getId());		
//					for (Coupon coupon : allCoups) {			
//						//delete coupon
//						if (coupon.getCoupon_id().equals(cp.getCoupon_id())) {
//							CouponDAO.deleteCoupon(coupon.getCoupon_id());
//						}
//					}
					chooseright = true;
					break;
				
				case 'n':
					System.out.println("Returning to main menu. . . ");
					chooseright = true;
					break;
				default:
					System.out.println("Invalid key. try again . . ");
					break;
				}
			}
		}
}
					

			
			
			
	


//		public static int userChoice()
//		{
//			System.out.println("(Type '9' to go back to the main menu)");
//			int choice = PrintingUtils.getIntFromUser();
//			if (backButton(choice) == true)
//				return (Integer) null;
//			return choice;
//		}
//		
//		public static boolean backButton(int choice)
//		{
//			if (choice==9)
//				return true;
//			return false;
//		}
	



