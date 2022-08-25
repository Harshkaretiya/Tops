package t_02_08;

import java.util.Scanner;

public class t07 {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the value of temperature in celcious : ");
		double c = sc.nextDouble();
		System.out.println("The temperature in Fahrenheit is " + ((double)9/5*c+32));
	}
}
