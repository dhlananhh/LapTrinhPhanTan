package ex06;

public class TestJoinThread {
	public static void main(String[] args) {
		new Thread(new YourTask()).start();
	}
}
