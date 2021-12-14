package com.project.couponsys;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PrintingUtils {
	public static void println(String line)
	{
		System.out.println(line);
	}
	
	public static void pause()
	{
		
		System.out.print("Press Enter key to continue ... ");
		getStringFromUser();
	}
	
	public static int getIntFromUser()
	{
		return Integer.parseInt(getStringFromUser());
		
	}
	public static double getDoubleFromUser()
	{
		return Double.parseDouble(getStringFromUser());
		
	}
	public static String getStringFromUser()
	{
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			return br.readLine();
		} catch (IOException e) {
			return null;
		}
		/*Scanner scanner = new Scanner(System.in);
		while(scanner.hasNextLine()){
		    String str=scanner.next();
		    scanner.close();
		    return str;
		}
		scanner.close();
		return null;*/
	}

}
