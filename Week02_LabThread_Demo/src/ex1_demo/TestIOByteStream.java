package ex1_demo;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class TestIOByteStream {
	public static void main(String[] args) {

		File fileFrom = new File("docs/hello.txt");
		File fileTo = new File("data/hi.txt");

		BufferedInputStream in = null;
		BufferedOutputStream out = null;

		try {
			in = new BufferedInputStream(new FileInputStream(fileFrom));
			out = new BufferedOutputStream(new FileOutputStream(fileTo));

			int n = 5;
			byte[] b = new byte[n];
			
			while(in.available() > 0) {
				int x = in.read(b, 0, n);
				out.write(b, 0, x);
			}

		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				in.close();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}
}
