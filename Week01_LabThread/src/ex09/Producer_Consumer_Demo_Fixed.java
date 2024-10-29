package ex09;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class MyQueue {
	int n;
	boolean valueSet = false;
	synchronized int get() {
		if (!valueSet) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("Got: " + n);
		
		// assume that our work takes time to execute
		try {
			Thread.sleep(300);
		} catch (Exception x) {
			x.printStackTrace();
		}
		
		valueSet = false;
		notify();
		return n;
	}
	
	
	synchronized void put (int n) {
		if (valueSet) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		this.n = n;
		valueSet = true;
		System.out.println("Put: " + n);
		
		// assume that our work takes time to execute
		try {
			Thread.sleep(500);
		} catch (Exception x) {
			x.printStackTrace();
		}
		
		valueSet = false;
		notify();
	}
}


class Producer implements Runnable {
	MyQueue q;
	public Producer (MyQueue q) {
		this.q = q;
	}
	
	@Override
	public void run() {
		int i = 0;
		while (true) {
			q.put(i++);
		}
	}
}


class Consumer implements Runnable {
	MyQueue q;
	public Consumer (MyQueue q) {
		this.q = q;
	}
	
	@Override
	public void run() {
		while (true) {
			q.get();
		}
	}
}


public class Producer_Consumer_Demo_Fixed {
	public static void main(String[] args) {
		System.out.println("Press Control-C to stop.");
		ExecutorService service = Executors.newFixedThreadPool(2);
		
		MyQueue q = new MyQueue();
		
		service.execute(new Producer(q));
		service.execute(new Consumer(q));
	}
}
