package chapter01;

public class ThreadDemo {
	public static void main(String[] args) {
		Runnable task1 = new CharPrinter('A', 20);
		Runnable task2 = new CharPrinter('B', 20);
		Thread thread1 = new Thread(task1 );
		Thread thread2 = new Thread(task2 );
		
		thread1.start();
		thread2.start();
	}
}
