package t_08_08;

import java.util.Iterator;
import java.util.Scanner;

public class check_integer 
{
	public static void main(String[] args) 
	{
			int a[] = new int[10];
			Scanner sc = new Scanner(System.in);
			for (int i = 0; i < 10; i++) 
			{			
					a[i] = sc.nextInt();
				
					try 
					{
									if (a[i]>10 && a[i]<40) 
									{
										
									}
									else
									throw new Error();
					}
					
					catch (Error e) 
					{
									System.out.println("Not valid");
					}
			}
			
			for(int i = 0; i < 10; i++)
			{
								if (a[i]>10 && a[i]<40) 
								{
									System.out.println(a[i]);
								}
			}
		
	}
}
