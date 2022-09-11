package t_08_08;

public class catch_box
{
	public static void main(String[] args) {
		try {
			int[] arr = {1,2,3,4/0}; 
//			int[] arr = {1,2,3,4};
			 System.out.println(arr[5]);
		} 
		
		catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("out of box");
		}
		catch (ArithmeticException e) {
			System.out.println("divide by zero");
		}
	}
}