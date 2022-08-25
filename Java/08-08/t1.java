package t_08_08;

public class t1
{
	static void validate(int age)
	{
		if (age>18) {
			System.out.println("You are eligible");
		}
		else {
			try {
				throw new MyException();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		validate(15);
	}
}