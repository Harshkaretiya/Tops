package t_02_08;

import java.util.Scanner;

public class t11 {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the year number");
		int a = sc.nextInt();
		if (a%4==0) {
			System.out.println("its a leap year");
		}
		else {
			System.out.println("its not a leap year");
		}
	}
}
