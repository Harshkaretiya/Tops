import java.util.Scanner;

public class Prime 
{
	public static void main(String[] args) 
	{
		Scanner sc = new Scanner(System.in);
		int a = sc.nextInt(),i;
		for (i = 2; i < a/2; i++) {
			if (a%i==0) {
				System.out.println("not a prime number");
				break;
			}
		}
		if (a%i!=0) {
			System.out.println("prime number");
		}

	}
}
