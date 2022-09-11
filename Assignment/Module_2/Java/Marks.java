import java.util.Scanner;

public class Marks 
{
	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter marks of maths out of 100 = ");
		int a = sc.nextInt();
		System.out.println("Enter marks of english out of 100 = ");
		int b = sc.nextInt();
		System.out.println("Enter marks of science out of 100 = ");
		int c = sc.nextInt();
		System.out.println("Enter marks of hindi out of 100 = ");
		int d = sc.nextInt();
		System.out.println("Enter marks of gujarati out of 100 = ");
		int e = sc.nextInt();
		int sum=(a+b+c+d+e);
		System.out.println("The total marks is = " + sum);
		System.out.println("The percentage is = " +(double)sum/5);

	}
}
