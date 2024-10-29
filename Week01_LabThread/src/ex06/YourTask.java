package ex06;

import java.util.Iterator;

public class YourTask implements Runnable {
	
	@Override
	public void run() {
		try {
			Thread t = new Thread(new AnotherTask("Another task", 10));
			t.start();
			for (int i = 0; i < 8; i++) {
				System.out.println("Your Task #" + i);
				if (i == 5)
					t.join();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
