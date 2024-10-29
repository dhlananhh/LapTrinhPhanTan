package ex3;


import java.util.concurrent.CountDownLatch;
import javax.management.RuntimeErrorException;


class Worker implements Runnable {
	private final CountDownLatch entryBarrier;
	private final CountDownLatch exitBarrier;
	
	
	public Worker (CountDownLatch entryBarrier, CountDownLatch exitBarrier) {
		this.entryBarrier = entryBarrier;
		this.exitBarrier = exitBarrier;
	}

	
	@Override
	public void run() {
		try {
			// Wait until main thread is ready (entry barrier)
			entryBarrier.await();
			
			// Do something
			work();
			
			// Let main thread proceed
			exitBarrier.countDown();
		} catch (Error e) {
			throw new RuntimeErrorException(e);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	
	private void work() {
		System.out.printf("Worker {%s} started \n", Thread.currentThread().getName());
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.printf("Worker {%s} finished \n", Thread.currentThread().getName());
	}
	
	
	private static final int PARTIES = 4;
	public static void main(String[] args) throws InterruptedException {
		CountDownLatch entryBarrier = new CountDownLatch(1);
		
		// Create barrier synchronizers
		CountDownLatch exitBarrier = new CountDownLatch(PARTIES);
		for (int p = 0; p < PARTIES; p++) {
			// Create & start threads with barriers
			Runnable task = new Worker(entryBarrier, exitBarrier);
			new Thread(task).start();
		}
		
		// Don't start yet
		System.out.println("All threads waiting to start ");
		Thread.sleep(1000);
		
		// Let all worker threads proceed
		entryBarrier.countDown();
		System.out.println("Waiting for results ");
		
		// Wait for all t finish (exit barrier)
		exitBarrier.await();
		System.out.println("All threads finished ");
	}
}
