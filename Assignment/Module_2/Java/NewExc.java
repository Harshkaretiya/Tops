package t_08_08;

import java.util.Scanner;

public class NewExc extends Exception
{
	public NewExc()
	{
		System.out.println("Sorry, insufficient balance, you need more 500 Rs.\r\n"
				+ "To perform this transaction.");
	}
	
	public static void main(String[] args) {
		NewExc n1 = new NewExc();
	}
}
