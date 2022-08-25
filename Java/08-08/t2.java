package t_08_08;

public class t2 {
	
	static void Div()
	{
//		if (b>0 || b<0) {
//			System.out.println((double)a/b);
//		}
//		else {
			try {
				double div = 10/0;
				System.out.println(div);
//				System.out.println(" 0 is invalid input");
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			
		}
	}
	public static void main(String[] args) {
	
		Div();
	}
}
