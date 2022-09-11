import java.util.Scanner;

public class Positive_Negative 
{
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the number = ");
		double a = sc.nextDouble();
		if (a>0) {
			System.out.println("number "+a+ " is positive");
		}
		else if (a<0) {
			System.out.println("number "+a+" is negative");
		}
		else {
			System.out.println("number "+a+" is zero");
		}

	}
}
