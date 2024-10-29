package ex1_demo;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class CopyFileDemo extends JFrame{
	
	private JTextField tfFrom;
	private JTextField tfTo;
	private JButton btnCopy;
	private JProgressBar progressBar;

	public CopyFileDemo() {
		setTitle("Copy File");
		setSize(400, 200);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		Box b, b1, b2, b3 ,b4;
		add(b = Box.createVerticalBox());
		
		b.add(b1 = Box.createHorizontalBox()); b.add(Box.createVerticalStrut(10));
		b.add(b2 = Box.createHorizontalBox()); b.add(Box.createVerticalStrut(10));
		b.add(b3 = Box.createHorizontalBox()); b.add(Box.createVerticalStrut(10));
		b.add(b4 = Box.createHorizontalBox()); b.add(Box.createVerticalStrut(10));
		
		JLabel lblFrom, lblTo;
		b1.add(lblFrom = new JLabel("From: ")); b1.add(tfFrom = new JTextField());
		b2.add(lblTo = new JLabel("To: ")); b2.add(tfTo = new JTextField());
		
		b3.add(btnCopy = new JButton("Copy...."));
		b4.add(progressBar = new JProgressBar());
		progressBar.setStringPainted(true);
		
		b.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		lblTo.setPreferredSize(lblFrom.getPreferredSize());
		
		tfFrom.setText("docs/hello.txt");
		tfTo.setText("data/hi.txt");
		
		btnCopy.addActionListener((e) -> {
			
			File fileFrom = new File(tfFrom.getText());
			File fileTo = new File(tfTo.getText());
			
			CopyTask task = new CopyTask(fileFrom, fileTo);
			task.execute();
			task.addPropertyChangeListener((evt) -> {
				if("progress".equals(evt.getPropertyName()))
				{
					progressBar.setValue((int) evt.getNewValue());
				}
			});
		});
		
	}
	
	public static void main(String[] args) throws InvocationTargetException, InterruptedException {
		SwingUtilities.invokeLater(() -> new CopyFileDemo().setVisible(true));
	}

}
