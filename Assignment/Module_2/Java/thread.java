package t_08_08;

public class thread extends Thread 
{
	public void run()
	{
		System.out.println("Running.....");
	}
	
	public static void main(String[] args) {
		thread t1 = new thread();
		thread t2 = new thread();
		
		t1.run();
		t2.run();
	}
}
