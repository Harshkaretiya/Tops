import java.util.Scanner;

public class Triangle 
{
	public static void main(String[] args) 
	{
		Scanner sc = new Scanner(System.in);
		System.out.println("enter value of base = ");
		int  a = sc.nextInt();
		System.out.println("enter value of height = ");
		int  b = sc.nextInt();
		int c = (a*b)/2;
		System.out.println(c);
	}
}
