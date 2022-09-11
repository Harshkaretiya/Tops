import java.util.Scanner;

public class Interest 
{
	public static void main(String[] args) 
	{
		Scanner sc = new Scanner(System.in);
		System.out.println("deposit value = ");
		double a = sc.nextDouble();
		System.out.println("rate = ");
		double b = sc.nextDouble();
		System.out.println("time = ");
		double c = sc.nextDouble();
		double d  = (a*b*c)/100 ;
		System.out.println("Interst = "+d);
		
	}
}
