package ex4;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicDemo {
	public static void main (String[] args) throws InterruptedException {
		ProcessingAtomicThread pt = new ProcessingAtomicThread();
		
		Thread t1 = new Thread(pt, "t1");
		t1.start();
		
		Thread t2 = new Thread(pt, "t2");
		t2.start();
		
		t1.join();
		t2.join();
		
		System.out.println("Processing Atomic count = " + pt.getCount());
	}
}
	
	
class ProcessingAtomicThread implements Runnable {
	private AtomicInteger count = new AtomicInteger(0);
	
	@Override
	public void run() {
		for (int i = 0; i < 5; i++) {
			processSomething(i);
			int j = count.incrementAndGet();
			System.out.println("Thread name: " + Thread.currentThread().getName() + " - after count: " + j);
		}
	}
	
	public int getCount() {
		return this.count.get();
	}
	
	private void processSomething (int i) {
		// processing some job
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}