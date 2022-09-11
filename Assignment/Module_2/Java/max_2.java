import java.util.Scanner;

public class max_2 
{
	 public static void main(String[] args)
	{
		 Scanner sc = new Scanner(System.in);
		 System.out.println("Enter number a : ");
			int a = sc.nextInt();
			System.out.println("Enter number b : ");
			int b = sc.nextInt();
			System.out.println("Enter number c : ");
			int c = sc.nextInt();
			int max;
			max = (a>b&&a>c)?a:(b>a&&b>c)?b:c;;
			System.out.println("the max value is" + max);

	}
}
