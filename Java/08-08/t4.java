package t_08_08;

import java.util.Iterator;
import java.util.Scanner;

public class t4 {
	public static void main(String[] args) {
		int a[] = new int[10];
		Scanner sc = new Scanner(System.in);
		for (int i = 0; i < 10; i++) {			
			a[i] = sc.nextInt();
			
			if (a[i]>10 && a[i]<40) {
				System.out.println(a[i]);
			}
			else {
				try {
				throw new Error("Not valid number");
				}
				catch (Error e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
			
		}
		
	}
}
