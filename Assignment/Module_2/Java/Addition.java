import java.util.Scanner;

public class Addition
{
	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter two number");
		int a = sc.nextInt();
		int b = sc.nextInt();
		System.out.println("Enter number according to operations : ");
		System.out.println("1 - addition \n2 - subtraction \n3 - multiplication \n4 - division");
		int num = sc.nextInt();
		switch (num) {
		case 1: {
			System.out.println("Addition is " + a+b);
			break;
		}
		case 2: {
			System.out.println("Subtraction is " + (a-b));
			break;
		}
		case 3: {
			System.out.println("Multiplication is " + a*b);
			break;
		}
		case 4: {
			System.out.println("Disivion is " + (double)a/b);
			break;
		}
		default:
			
		}

	}
}
