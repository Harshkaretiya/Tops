import java.util.Scanner;

public class Add 
{
	public static void main(String[] args) 
	{
		System.out.println("enter two number =");
		Scanner sc = new Scanner(System.in);
		int a = sc.nextInt();
		int b = sc.nextInt();
		System.out.println("addition = "+(a+b)+"\nsubtraction = "+(a-b)+"\nmultiplication = "+(a*b)+"\ndivision = "+(a/b));
	}
}
