package chapter01;

public class CharPrinter implements Runnable {
	private char ch;
	private int num;
	
	
	public CharPrinter(char ch, int num) {
		this.ch = ch;
		this.num = num;
	}


	@Override
	public void run() {
		for (int i = 0; i < num; i++) {
			System.out.println(ch + " ");
		}
	}
	
	
	
}
