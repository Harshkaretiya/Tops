package t_02_08;

import java.util.Scanner;

public class t02 {
		public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter number a");
		int a=sc.nextInt();
		System.out.println("ENter number b");
		int b=sc.nextInt();
		System.out.println("Addition is " + a+b);
		System.out.println("Subtraction is " + (a-b));
		System.out.println("multiplication is " + a*b);
		System.out.println("Division is " + (double)a/b);
		}
}
