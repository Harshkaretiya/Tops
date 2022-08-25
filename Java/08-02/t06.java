package t_02_08;

import java.util.Scanner;

public class t06 {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the principle amount  :  ");
		double p = sc.nextDouble();
		System.out.println("Enter the interest rate in % : ");
		double r = sc.nextDouble();
		System.out.println("Enter the time period in years : ");
		double t = sc.nextDouble();
	
		System.out.println("The simple interest is " + (double)(p*r*t/100));
		}
}
