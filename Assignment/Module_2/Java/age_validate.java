package t_08_08;

public class age_validate
{
	static void validate(int age)
	{
		
			//throw new ArithmeticException("Not Valid");
			try 
			{
				
				
				if(age>18)
				{
					System.out.println("Eligible to vote");
				}
				else
				{
					throw new Error();
				}
			}
			catch (Error e) 
			{
				// TODO Auto-generated catch block
				System.out.println("error");
			}
			
		
		System.out.println("Executed");
	}
	public static void main(String[] args) 
	{
		validate(15);
	}
}