package t_02_08;

import java.util.Scanner;

public class t04 {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter length of radius : ");
		int a = sc.nextInt();
		System.out.println("The area of circle of radius  " + a + " is " + 3.14*a*a);
	}
}
