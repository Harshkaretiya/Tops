package t_02_08;

import java.util.Scanner;

public class t10 {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the number");
		double a = sc.nextDouble();
		if (a>0) {
			System.out.println("number is positive");
		}
		else if (a<0) {
			System.out.println("number is negative");
		}
		else {
			System.out.println("number is zero");
		}
	}
}
