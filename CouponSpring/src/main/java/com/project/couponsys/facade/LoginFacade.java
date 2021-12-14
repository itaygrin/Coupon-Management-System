package com.project.couponsys.facade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.project.couponsys.PrintingUtils;
import com.project.couponsys.entities.CouponCategory;
import com.project.couponsys.entities.Customer;
import com.project.couponsys.repository.CompanyRepo;
import com.project.couponsys.repository.CustomerRepo;

@Component
@Qualifier("loginFacade")
public class LoginFacade {
	
	@Autowired
	AdminFacade adminFacade;
	@Autowired
	CompanyFacade companyFacade;
	@Autowired
	CustomerFacade customerFacade;
	@Autowired
	CompanyRepo companyRepo;
	@Autowired
	CustomerRepo customerRepo;
	
	protected String eMail = "";
	protected String password = "";
	
	public String [] loginDetails()
	{
		
		System.out.print("Email: ");
		String email;
		while (true)
		{
		email = PrintingUtils.getStringFromUser();
			if (!email.contains("@")) {
				System.out.println("Sorry, invalid email address. must contain '@'");
			}
			else
				break;
		}
		email = email.toLowerCase();
		System.out.print("Password: ");
		String pwd = PrintingUtils.getStringFromUser();
		String [] strArr = {email, pwd};
		password = pwd;
		eMail = email;
		return strArr;
	}
	
	public boolean companyLog(char backOrTryAgain)
	{
		while(backOrTryAgain != 'X')
		{
			System.out.println("Press 'X' if you wish to go back");
			char choice = PrintingUtils.getStringFromUser().charAt(0);
			if (choice == 'X' || choice == 'x')
				break;
			String [] strArr = loginDetails(); //[0] = email, [1] = password
			if (companyRepo.findByEmailAndPassword(strArr[0], strArr[1]).isPresent())
				return true;
			
			System.out.println("Either the password or the email are incorrect, try again");
		}
		return false;
	}
	
	public boolean customerLog(char backOrTryAgain)
	{
		while(backOrTryAgain != 'X')
		{
			System.out.println("Press 'X' if you wish to go back");
			char choice = PrintingUtils.getStringFromUser().charAt(0);
			if (choice == 'X' || choice == 'x')
				break;
			String [] strArr = loginDetails(); //[0] = email, [1] = password
			if (customerRepo.findByEmailAndPassword(strArr[0], strArr[1]).isPresent())
				return true;
			
			System.out.println("Either the password or the email are incorrect, try again");
		}
		return false;
	}
	
	
	public boolean AdminLog (char backortryagain)
	{
		while(backortryagain != 'X')
		{	System.out.println("Press 'X' if you wish to go back, any key to continue");
			char choice = PrintingUtils.getStringFromUser().charAt(0);
			if (choice == 'X' || choice == 'x')
				break;
			String [] strArr = loginDetails();
				
			if(strArr[0].equals("admin@admin.com") && strArr[1].equals("admin")) {
				return true;
			}
			System.out.println("Either the password or the email are incorrect, try again");
		}
		return false;

	}


	public void login ()

	{
		boolean isConnected = false;
		while(!isConnected){
			System.out.println("Hey, welcome to the Itay's Coupon management program!\n"
					+ "First, you must login.\n"
					+ "login as: *Admin (0)\n"
					+ "          *Company (1)\n"
					+ "          *Customer (2)");
			char clienttype = PrintingUtils.getStringFromUser().charAt(0);
			char backortryagain = 'z';
			switch (clienttype) {
			case '0':
				//AdminFacade adminFacade = new AdminFacade();
				isConnected = AdminLog(backortryagain);
				if(isConnected)
					adminFacade.mainMenu();
				break;
			case '1':
				isConnected = companyLog(backortryagain);
				if (isConnected)	//incase of success - connect with the given company.
					companyFacade.mainMenu(companyRepo.findByEmailAndPassword(eMail, password).get());
				break;
			case '2':
				isConnected = customerLog(backortryagain);
				if (isConnected)	//incase of success - connect with the given company.
					customerFacade.mainMenu(customerRepo.findByEmailAndPassword(eMail, password).get());
				break;
			default:
				System.out.println("INVALID CHOICE");
				break;
			}
			isConnected = false;
			
			
		}
		
	}/*
	public static ArrayList<File> filesInFoler (String path)
	{
		File f = new File(path);
		ArrayList<File> files = new ArrayList<File>(Arrays.asList(f.listFiles()));
		return files;
	}
	*/
	public static String strUserChoice()
	{
		System.out.println("(Type 0' to go back to the main menu)");
		String choice = PrintingUtils.getStringFromUser();
		if (backButton(choice) == true)
			return null;
		return choice;
	}
	
	public static boolean backButton(String choice)
	{
		if (choice.toLowerCase().equals("0"))
			return true;
		return false;
	}
	
	public static Integer userChoice()
	{
		System.out.println("(Type '0' to go back to the main menu)");
		try {
		int choice = PrintingUtils.getIntFromUser();
		if (backButton(choice) == true)
			return null;
		return choice;
		}catch (Exception e) {
			return 0;
		}
	}
	
	public static boolean backButton(int choice)
	{
		if (choice==0)
			return true;
		return false;
	}
	
	public static CouponCategory isCatExists(String catstring)
	{
		for (int i = 0; i < CouponCategory.values().length; i++) {
			if (catstring.toUpperCase().equals(CouponCategory.values()[i].toString())) {
				return CouponCategory.values()[i];
			}
		}
		return null;
	}

	
	
	

}

