package t_08_08;

public class MyException extends Exception 
{
	public MyException() 
	{
		System.out.println("Your age is not eligible");
		
	}
	
	public static void main(String[] args) {
		
		MyException m1 =new MyException();
		
	}
}
