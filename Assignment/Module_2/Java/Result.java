import java.util.Scanner;

public class Result
{
	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter marks of maths out of 100");
		int a = sc.nextInt();
		System.out.println("Enter marks of english out of 100");
		int b = sc.nextInt();
		System.out.println("Enter marks of science out of 100");
		int c = sc.nextInt();
		System.out.println("Enter marks of hindi out of 100");
		int d = sc.nextInt();
		System.out.println("Enter marks of gujarati out of 100");
		int e = sc.nextInt();
		int sum=(a+b+c+d+e);
		int p=sum/5;
		System.out.println("The total marks is " + sum);
		System.out.println("The percentage is " + sum/5 +"%");
		
		if (p>75) {
			System.out.println("Distinction");
		}
		else if (p>60 && p<=75) {
			System.out.println("First class");
		}
		else if (p>50 && p<=60) {
			System.out.println("Second class");
		}
		else if (p>35 && p<=50) {
			System.out.println("Pass class");
		}
		else {
			System.out.println("Fail");
		}

	}
}
