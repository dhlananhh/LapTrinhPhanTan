package ex05;

public class PrimeCheckTask implements Runnable {
	private int x;
	
	
	// constructor
	public PrimeCheckTask (int x) {
		this.x = x;
	}
	
	
	// hàm kt xem x có phải là số nguyên tố hay không
	private boolean isPrime (int num) {
		if (num <= 1)
			return false;
		
		for (int i=2; i<=Math.sqrt(num); i++) {
			if (num % i == 0)
				return false;
		}
		
		return true;
	}
	
	
	@Override
	public void run() {
		if (isPrime(x))
			System.out.println(x + " is a prime number.");
		else
			System.out.println(x + " is not a prime number.");
	}

	
	public static void main(String[] args) {
		// tạo 3 số nguyên dương ngẫu nhiên
		int number1 = 13;
		int number2 = 20;
        int number3 = 7;
        
        
        // tạo 3 threads và connect với number tương ứng
        Thread thread1 = new Thread(new PrimeCheckTask(number1));
        Thread thread2 = new Thread(new PrimeCheckTask(number2));
        Thread thread3 = new Thread(new PrimeCheckTask(number3));
        
        // khởi động thread
		thread1.start();
		thread2.start();
		thread3.start();
	}
}
