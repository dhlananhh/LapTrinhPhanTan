package ex1_demo;

import java.awt.HeadlessException;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

public class CopyTask extends SwingWorker<Integer, Integer>{
	private File fileFrom;
	private File fileTo;
	/**
	 * @param fileFrom
	 * @param fileTo
	 */
	public CopyTask(File fileFrom, File fileTo) {
		this.fileFrom = fileFrom;
		this.fileTo = fileTo;
	}
	
	@Override
	protected Integer doInBackground() throws Exception {
		
		BufferedInputStream in = null;
		BufferedOutputStream out = null;
		byte[] buffs = null;

		try {
			in = new BufferedInputStream(new FileInputStream(fileFrom));
			out = new BufferedOutputStream(new FileOutputStream(fileTo));

			buffs = Files.readAllBytes(Paths.get(fileFrom.getAbsolutePath()));
			int n = buffs.length /100;
			byte[] b = new byte[n];
			int p = 0;
			
			while(in.available() > 0) {
				int x = in.read(b, 0, n);
				out.write(b, 0, x);
				p++;
				setProgress(Math.min(100, p));
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
		
		return buffs.length;
	}
	@Override
	protected void process(List<Integer> chunks) {
		super.process(chunks);
	}
	@Override
	protected void done() {
		try {
			JOptionPane.showMessageDialog(null, "Finished! Bytes in total: " + get());
		} catch (HeadlessException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}
	
	
}
