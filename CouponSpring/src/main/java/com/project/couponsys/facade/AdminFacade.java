package com.project.couponsys.facade;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import com.project.couponsys.PrintingUtils;
import com.project.couponsys.entities.Company;
import com.project.couponsys.entities.Coupon;
import com.project.couponsys.entities.Customer;
import com.project.couponsys.entities.Purchase;
import com.project.couponsys.repository.CompanyRepo;
import com.project.couponsys.repository.CouponRepo;
import com.project.couponsys.repository.CustomerRepo;
import com.project.couponsys.repository.PurchaseRepo;

@Primary
@Component
public class AdminFacade extends LoginFacade{
	
	@Autowired
	CompanyRepo companyRepo;
	@Autowired
	CustomerRepo customerRepo;
	@Autowired
	PurchaseRepo purchaseRepo;
	@Autowired
	CouponRepo couponRepo;

	/*****************************Constructor for AdminFacade****************************/
	
	public AdminFacade() {
	}

	/*****************************MAIN MENU ADMIN****************************
	*returns the constructed object in case of successful login			 		  */
	public void mainMenu() {

		boolean end = false;
//		try {
//		createCompany();
//		}catch (Exception e) {
//			e.printStackTrace();
//		}
		System.out.println("Welcome Admin! What would you like to do?\n***************");
			while(!end)
			{
			System.out.println("*Create a new company (0)\n"
					+ "*Update company details (1)\n"
					+ "*Delete company (2)\n"
					+ "*Return all companies (3)\n"
					+ "*Return a specific company (4)\n"
					+ "*Create a new customer (5)\n"
					+ "*Update customer details (6)\n"
					+ "*Delete customer (7)\n"
					+ "*Return all customers (8)\n"
					+ "*Return a specific customer (9)\n"
					+ "*Log Out (10)\n");
			
						try {
						int mainMenuChoice = PrintingUtils.getIntFromUser();
						switch (mainMenuChoice) {
							case 0:
								System.out.println("***CREATE NEW COMPANY***");
								createCompany();
								break;
								
							case 1:
								System.out.println("***UPDATE A COMPANY***");
										System.out.print("type in the ID of the company you wish to change: ");
										Integer idCompUp = PrintingUtils.getIntFromUser();
										while (!companyRepo.findById(idCompUp).isPresent()) {
											System.out.println("Couldn't find a company with that id, try again");
											idCompUp = userChoice();
											if (idCompUp == null) {
												break;
											}
										}
										updateCompany(companyRepo.findById(idCompUp).get());	
								break;
							case 2:
								System.out.println("***DELETE A COMPANY***");
								System.out.print("type in the ID of the company you wish to delete: ");
								Integer idCompRemove = PrintingUtils.getIntFromUser();
								while (!companyRepo.findById(idCompRemove).isPresent()) {
									System.out.println("Couldn't find a company with that id, try again");
									idCompRemove = userChoice();
									if (idCompRemove == null) {
										break;
									}
								}
								removeCompany(companyRepo.findById(idCompRemove).get());
								break;
								
							case 3:
								System.out.println("***ALL COMPANIES***");
								List<Company> allComps = (List<Company>) companyRepo.findAll();
								for (Company company : allComps) {
									System.out.println(company.printInfo());
								}
								
								System.out.println();
								break;
							case 4:
								System.out.println("***SPECIFIC COMPANY***");
								System.out.println("type in the ID of the company you wish to view: ");
								Integer idCompOpen = PrintingUtils.getIntFromUser();
								String compInfo = "";
								if(companyRepo.findById(idCompOpen).isPresent())
									compInfo = companyRepo.findById(idCompOpen).get().printInfo();
								if (compInfo.isEmpty()) {
									System.out.println("Company doesn't exist. no info to show");
								}
								System.out.println(compInfo);
								
								break;
							case 5:
								System.out.println("***NEW CUSTOMER***");
								createCustomer();
								break;
								
							case 6: 
								System.out.println("***UPDATE CUSTOMER***");
								System.out.print("type in the ID of the customer you wish to change: ");
								Integer idCustUp = PrintingUtils.getIntFromUser();
								while (!customerRepo.findById(idCustUp).isPresent()) {
									System.out.println("Couldn't find a customer with this id, try again");
									idCustUp = userChoice();
									if (idCustUp == null) {
										break;
									}
								}
								updateCustomer(customerRepo.findById(idCustUp).get());
								break;
								
							case 7:
								System.out.println("***DELETE A CUSTOMER***");
								System.out.print("type in the ID of the customer you wish to delete: ");
								Integer idCustRemove = PrintingUtils.getIntFromUser();
								while (!customerRepo.findById(idCustRemove).isPresent()) {
									System.out.println("Couldn't find a customer with this id, try again");
									idCustRemove = userChoice();
									if (idCustRemove == null) {
										break;
									}
								}
								removeCustomer(customerRepo.findById(idCustRemove).get());
								break;
								
							case 8:
								System.out.println("***ALL CUSTOMERS***");
								List<Customer> allCusts = (List<Customer>) customerRepo.findAll();
								for (Customer customer : allCusts) {
									System.out.println(customer.printInfo());
								}
								break;
								
							case 9:
								System.out.println("***SPECIFIC CUSTOMER***");
								System.out.println("type in the ID of the customer you wish to view: ");
								Integer idCustOpen = PrintingUtils.getIntFromUser();
								String custInfo = "";
								if (customerRepo.findById(idCustOpen).isPresent())
									custInfo = customerRepo.findById(idCustOpen).get().printInfo();
								if (custInfo.isEmpty()) {
									custInfo ="Couldn't find a customer with the given ID, no info to show";
								}
								System.out.println(custInfo);
								break;
							case 10:
								end=true;
								System.out.println("Exiting main menu. . .\n");
								break;
							default:
								break;
						}
					}catch (Exception e) {
						String exMsg = "You entered the wrong type of variable (only number in range is acceptable)";
						System.out.println(exMsg+"\n");
						e.printStackTrace();
					}
				}
				
			}
			
	

	/***********************ADMIN ON COMPANY********************/
		
	/*
	 * creates a new company after checking all conditions
	 */
	public void createCompany ()
	{
		createCompanyUser();	
	}
	/*
	 * assistant method for createCompany - constructs a new company
	 */
	@Transactional
	public Company createCompanyUser () {
		//Integer companyid;
		String comPassword;
		String comEmail;
		String compName;
		//companyid = "cmp_"+CompanyCounter;
		
		System.out.println("Choose the company name");
		while (true)
		{
		 compName = PrintingUtils.getStringFromUser();
			boolean isExist = isCompanyExists(compName);
			if (isExist==true) {
				String exMsg = "Company already exists, cannot create a company with the same name";
				System.out.println(exMsg);
				continue;
			}
			if (compName.isEmpty()) {
				String exMsg = "Illegal company name, cannot be null, try again";
				System.out.println(exMsg);
				continue;
			}
			break;
		}
		System.out.println("Choose the company email");
		while (true)
		{
			comEmail = PrintingUtils.getStringFromUser();
			if (isEmailExistsComp(comEmail) == true) {
				System.out.println("Sorry, email already taken. try again");
			}
			if (emailValidation(comEmail)== false) {
				System.out.println("Sorry, email format must contain one '@'");
			}
			
			if (isEmailExistsComp(comEmail) == false && emailValidation(comEmail)== true) {
			break;	
			}

		}
		System.out.println("Choose the company password");
		while (true)
		{
		 comPassword = PrintingUtils.getStringFromUser();
		if (comPassword.isEmpty()) {
			String exMsg = "Password must contain characters, cannot be null, try again";
			System.out.println(exMsg);
		}
		else 
			break;
		}
		Company newCompany = new Company();
		newCompany.setEmail(comEmail);
		newCompany.setName(compName);
		newCompany.setPassword(comPassword);
		try {
		companyRepo.save(newCompany);
		}catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Company ID: "+companyRepo.findByName(compName).get().getCompanyId()+" Name: "+compName+", was created succesfuly!");
		return newCompany;
		
	}

	/*
	 * return true if company exists.
	 */
	public boolean isCompanyExists(String compName)
	{
		try {
		Optional<Company> data = companyRepo.findByName(compName);
		return data.isPresent();
		}catch (Exception e) {
		return false;
		}
	}
	public boolean isEmailExistsComp(String emailtocheck)  
	{
		try {
		List<Company> allComps = (List<Company>) companyRepo.findAll();
		for (Company company : allComps) {
			if (company.getEmail().equals(emailtocheck))
				return true;
		}
		return false;
		
		}catch (Exception e) {
			return false;
		}
	}

	@Transactional
	public void removeCompany(Company comp) {
		char choice = 'z';
		boolean chooseright = false;
		
		while(!chooseright)
		{
			System.out.println("Are you sure you want to delete '"+comp.getName()+"'? (y/n)");
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
		
		
		switch (choice) {
		case 'y':

			//deletes company	
			//deletes all purchase log of company coupons from customers
			System.out.println("ID: "+comp.getCompanyId()+", Name"+comp.getName()+ " got deleted succesfuly!");
			for (Coupon cp : couponRepo.findByCompanyid(comp.getCompanyId())) {
				for (Purchase pur : purchaseRepo.findByCouponid(cp.getCouponid())) {
					purchaseRepo.delete(pur);
				}
				couponRepo.delete(cp);
			}
			companyRepo.delete(comp);
			break;
		
		case 'n':
			System.out.println("Returning to main menu. . . ");
		default:
			System.out.println("Invalid key. Returning to main menu. . . ");
			break;
		}

		
		
	}
	@Transactional
	public void updateCompany(Company comp)
	{
		System.out.println("What would you like to change?\n"
				+ "*email address (0)\n"
				+ "*Password (1)\n"
				+ "*Back to main menu (9)");
		
		try {
		int upchoice = PrintingUtils.getIntFromUser();
		switch (upchoice) {
			case 0:
				while(true)
				{
				System.out.println("Select new email:");
				String email = PrintingUtils.getStringFromUser();
				if (emailValidation(email)==true && isEmailExistsComp(email)== false) {
					comp.setEmail(email);
					companyRepo.save(comp);
					System.out.println("Email succesfuly changed to "+email);
					break;
				}
				else
					System.out.println("invalid email, try again");
				}
				break;
				
			case 1:
				while(true)
				{
				System.out.println("Select password: ");
				String newpass = PrintingUtils.getStringFromUser();
				if (!newpass.isEmpty()) {
					comp.setPassword(newpass);
					companyRepo.save(comp);
					System.out.println("Password succesfuly changed to "+newpass);
					break;
				}
				else
					System.out.println("Password must contain characters, try again");
				}
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
	/************************ADMIN ON CUSTOMER***********************/
	
	public void createCustomer ()
	{
		createCustomerUser();
	}
	
	@Transactional
	public Customer createCustomerUser ()
	{
		
		//String custid;
		String custPassword;
		String custEmail;
		String custFName;
		String custLname;
		//custid = "cst_"+CustomerCounter;
		
		System.out.println("Enter customer's First name");
		while (true)
		{
			custFName = PrintingUtils.getStringFromUser();
			
			if (custFName.isEmpty()) {
				String exMsg = "Illegal first name, cannot be null, try again";
				System.out.println(exMsg);
				continue;
			}
			
			break;
		}
		System.out.println("Enter customer last name");
		while (true)
		{
			custLname = PrintingUtils.getStringFromUser();
			
			if (custLname.isEmpty()) {
				String exMsg = "Illegal last name, cannot be null, try again";
				System.out.println(exMsg);
				continue;
			}
			
			break;
		}
		System.out.println("Enter customer email");	
		while (true)
		{
			custEmail = PrintingUtils.getStringFromUser();
			boolean isExist = isEmailExistsCust(custEmail);		//will check if email is taken
			if (isExist==true) {
				String exMsg = "Email already exists, cannot create a customer with the same email";
				System.out.println(exMsg);
				continue;
			}
			
			if (emailValidation(custEmail)==false) {
			String exMsg = "Illegal email address, must contain only one '@', try again";
			System.out.println(exMsg);
			continue;
			}
		else
			break;
		}
		System.out.println("Enter customer password");	
		while (true)
		{
		 custPassword = PrintingUtils.getStringFromUser();
		if (custPassword.isEmpty()) {
			String exMsg = "Password must contain characters, cannot be null, try again";
			System.out.println(exMsg);
		}
		else 
			break;
		}
		
		Customer newCustomer = new Customer();
		custFName = custFName.toLowerCase();
		custLname = custLname.toLowerCase();
		custEmail = custEmail.toLowerCase();
		newCustomer.setFname(custFName);
		newCustomer.setLname(custLname);
		newCustomer.setEmail(custEmail);
		newCustomer.setPassword(custPassword);
		try {
		customerRepo.save(newCustomer);
		System.out.println("Customer "+newCustomer.getCustomerId()+" created succesfuly!");
		}catch (Exception e) {
			e.printStackTrace();
		}
		return newCustomer;

	}
	@Transactional
	public void updateCustomer(Customer cust) 
	{
		System.out.println("What would you like to change?\n"
				+ "*email address (0)\n"
				+ "*Password (1)\n"
				+ "First name (2)\n"
				+ "Last name (3)\n"
				+ "*Back to main menu (9)");
		
		int upchoice = PrintingUtils.getIntFromUser();
		switch (upchoice) {
		case 0:
			while(true)
			{
			System.out.println("Select new email:");
			String email = PrintingUtils.getStringFromUser();
			if (emailValidation(email)==true && isEmailExistsCust(email)== false) {
				cust.setEmail(email);
				System.out.println("Email has succesfuly changed to "+email);
				customerRepo.save(cust);
				break;
			}
			else
				System.out.println("invalid email, try again");
			}
			break;
			
		case 1:
			while(true)
			{
			System.out.println("Select password: ");
			String newpass = PrintingUtils.getStringFromUser();
			if (!newpass.isEmpty()) {
				cust.setPassword(newpass);
				System.out.println("Password has succesfuly changed to "+newpass);
				customerRepo.save(cust);
				break;
			}
			else
				System.out.println("Password must contain characters, try again");
			}
			break;

		case 2:
			while(true)
			{
				System.out.println("Select new first name: ");
				String newfname = PrintingUtils.getStringFromUser();
				if (!newfname.isEmpty()) {
					cust.setFname(newfname);
					System.out.println("Customer name has succesfuly changed to "+newfname);
					customerRepo.save(cust);
					break;
				}
			}
			break;
		case 3:
			while (true)
			{

				System.out.println("Select new last name: ");
				String newlname = PrintingUtils.getStringFromUser();
				if (!newlname.isEmpty()) {
					cust.setFname(newlname);
					System.out.println("Customer last name has succesfuly changed to "+newlname);
					customerRepo.save(cust);
					break;
				}
			}
			break;
		case 9:
			System.out.println("Returning to main menu. . . ");
			break;
		default:
			System.out.println("Invalid key, returning to main menu. . . ");
			break;
		}
		
	}
	
	@Transactional
	public void removeCustomer (Customer cust)
	{
		char choice = 'z';
		
		while(true)
		{
			System.out.println("Are you sure you want to delete '"+cust.getFname()+" "+cust.getLname()+"'? (y/n)");
			try {
				choice = PrintingUtils.getStringFromUser().charAt(0);
				Character.toLowerCase(choice);
			}catch (Exception e){
				System.out.println("you didn't type a char type, try again.");
			}
			break;
		}
		
		switch (choice) {
		case 'y':
			System.out.println("Customer "+cust.getCustomerId()+" got deleted succesfuly!");
			purchaseRepo.delete(purchaseRepo.findByCustomerid(cust.getCustomerId()).get(0));
			customerRepo.delete(cust);
			break;

		case 'n':
			System.out.println("Returning to main menu. . . ");
		default:
			System.out.println("Invalid key. Returning to main menu. . . ");
			break;
		}
		
		
		
	}

	
	public boolean isEmailExistsCust(String emailtocheck)
	{
		try {
		List<Customer> allCusts = (List<Customer>) customerRepo.findAll();
		for (Customer customer : allCusts) {
			if(customer.getEmail().equals(emailtocheck))
				return true;
		}
		return false;
		}
		catch (Exception e) {
			return false;
		}

	}

	
	/************************ADDITIONAL METHOD*******************************/
	

	public static boolean emailValidation (String emailtocheck)
	{
		int inmark = 0;
		for (int i = 0; i < emailtocheck.length(); i++) {	//checks num of @ in the email
			char c = emailtocheck.charAt(i);		
			if (c=='@') {
				inmark++;
			}
		}
		if (inmark!=1) {			////return true if valid
			return false;
		}
		return true;
	}

	
}

















