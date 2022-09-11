import java.util.Scanner;

public class Temperture 
{
	public static void main(String[] args) 
	{
		System.out.println("temprture value = ");//9/5  c + 32
		Scanner sc = new Scanner(System.in);
		int a = sc.nextInt();
		double b = (double)9/5*a+32;
		System.out.println("degree"+b);
		
	}
}
