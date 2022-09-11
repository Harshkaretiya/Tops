package t_08_08;

import java.util.Scanner;

public class balance {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int Balance = 2000;
		try {
			Balance  = Balance - 2500;
			if (Balance<0) {
				throw new NewExc();
			}
			
		} catch (NewExc e) {
			// TODO: handle exception
//			e.printStackTrace();
		}
	}
}
