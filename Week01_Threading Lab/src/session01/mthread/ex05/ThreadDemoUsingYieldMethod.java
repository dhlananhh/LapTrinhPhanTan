package session01.mthread.ex05;

public class ThreadDemoUsingYieldMethod implements Runnable {
	private Thread t;
	
	
	public ThreadDemoUsingYieldMethod (String str) {
		t = new Thread(this, str);
		t.start();
	}
	
	
	@Override
	public void run() {
		for (int i = 0; i < 5; i++) {
			// yields control to another thread every 5 iterations
			if ((i % 5) == 0) {
				System.out.println(Thread.currentThread().getName() + "yielding control...");
				
				/* 	causes the currently executing thread object to temporarily
				pause and allow other threads to execute  */
				Thread.yield();
			}
		}
		
		System.out.println(Thread.currentThread().getName() + "has finished executing.");
	}

	
	public static void main(String[] args) {
		new ThreadDemoUsingYieldMethod("Thread 1 ");
		new ThreadDemoUsingYieldMethod("Thread 2 ");
		new ThreadDemoUsingYieldMethod("Thread 3 ");
	}
}
