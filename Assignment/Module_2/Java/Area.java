import java.util.Scanner;

public class Area
{
	public static void main(String[] args) 
	{
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the number according to operations");
		System.out.println("1 - Area of Triangle\n2 - Area of rectangle\n3 - Area of circle");
		int num = sc.nextInt();
		if (num==1) {
			System.out.println("Enter Height and base length");
			int h = sc.nextInt();
			int b = sc.nextInt();
			System.out.println("Area of triangle is " + 0.5*h*b);
		}
		else if (num ==2) {
			System.out.println("Enter length and width");
			int l = sc.nextInt();
			int w = sc.nextInt();
			System.out.println("Area of rectangle is " + l*w);
		}
		else if (num == 3) {
			System.out.println("Enter radius");
			int r = sc.nextInt();
			System.out.println("Area of circle is " + 3.14*r*r);
		}
		else {
			System.out.println("you have entered wrong value");
		}

	}
}
