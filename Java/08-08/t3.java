package t_08_08;

public class t3 {
	
	
	public static void main(String[] args) {
		int a[] = new int[5];
		try {
			a[5] = 5;
		} catch (ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}
}
