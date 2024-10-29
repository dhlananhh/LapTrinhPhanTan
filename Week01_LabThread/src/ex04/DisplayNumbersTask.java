package ex04;


public class DisplayNumbersTask implements Runnable {
	@Override
	public void run() {
		for (int i=1; i<=10; i++)
			System.out.println(Thread.currentThread().getName() + ": " + i);
	}

	
	public static void main(String[] args) {
		// tạo 3 obj task
		DisplayNumbersTask task1 = new DisplayNumbersTask();
		DisplayNumbersTask task2 = new DisplayNumbersTask();
		DisplayNumbersTask task3 = new DisplayNumbersTask();
		
		// tạo 3 thread và connect với task tương ứng
		Thread thread1 = new Thread(task1, "Thread 1");
		Thread thread2 = new Thread(task2, "Thread 2");
		Thread thread3 = new Thread(task3, "Thread 3");
		
		// khởi động thread
		thread1.start();
		thread2.start();
		thread3.start();
	}
}
