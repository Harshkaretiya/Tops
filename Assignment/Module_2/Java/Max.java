import java.util.Scanner;

public class Max 
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
		if (a>b && a>c) {
			System.out.println("A is biggest number");
		}
		else{
					if (b>a && b>c) {
					System.out.println("B is Biggest number");
					}
					else {
					System.out.println("C is Biggest number");
					}
		}

	}
}
