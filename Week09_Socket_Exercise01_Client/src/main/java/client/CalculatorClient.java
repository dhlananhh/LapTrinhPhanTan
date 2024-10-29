package client;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import com.formdev.flatlaf.FlatLightLaf;


public class CalculatorClient extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private static final int SERVER_PORT = 8000;
	
	private JLabel headingLabel;
	private JButton buttonGiai, buttonXoa, buttonThoat;
	private JButton blueButton, redButton, greenButton;
	private JRadioButton radCong, radTru, radNhan, radChia;
	private JLabel labelA, labelB, labelKQ;
	private JTextField input1, input2, output;
	private JPanel panelA, panelB, panelC, panelD;
	
	
	public CalculatorClient() {
		setTitle("CalculatorClient");
		setSize(600, 500);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		
		createAndDisplayGUI();
	}
	
	
	public void createAndDisplayGUI() {
		// ** content panel **
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new BorderLayout());
		
		// ** north panel **
		JPanel northPanel = new JPanel();
		northPanel.add(Box.createRigidArea(new Dimension(0, 50)));
		headingLabel = new JLabel("Calculator Client");
		northPanel.add(headingLabel);
		contentPanel.add(northPanel, BorderLayout.NORTH);
		headingLabel.setFont(new Font("Arial", Font.BOLD, 25));
		headingLabel.setForeground(Color.BLUE);
		
		// ** west panel **
		JPanel westPanel = new JPanel();
		westPanel.setLayout(new GridLayout(6, 1, 0, 10));
		westPanel.setBackground(Color.LIGHT_GRAY);
		westPanel.setPreferredSize(new Dimension(100, 0));	
		westPanel.setBorder(BorderFactory.createTitledBorder("Chọn tác vụ"));
		contentPanel.add(westPanel, BorderLayout.WEST);
		
		// -- tạo các button --
		buttonGiai = new JButton("Giải");
		buttonGiai.add(Box.createRigidArea(new Dimension(10, 20)));
		buttonXoa = new JButton("Xóa");
		buttonXoa.add(Box.createRigidArea(new Dimension(10, 20)));
		buttonThoat = new JButton("Thoát");
		buttonThoat.add(Box.createRigidArea(new Dimension(10, 20)));
		
		// -- add các button vào west panel
		westPanel.add(buttonGiai);
		westPanel.add(buttonXoa);
		westPanel.add(buttonThoat);
		
		// ** center panel **
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(null);
		contentPanel.add(centerPanel, BorderLayout.CENTER);
		centerPanel.setBorder(BorderFactory.createTitledBorder("Tính toán: "));
		
		// -- thêm các label vào center panel --
		labelA = new JLabel("Nhập a: ");
		centerPanel.add(labelA);
		int x = 50, y = 30, width = 100, height = 30;
		labelA.setBounds(x, y, width, height);
		
		input1 = new JTextField();
		centerPanel.add(input1);
		int a = x + 50, b = 30, w = 300, h = 30;
		input1.setBounds(a, b, w, h);
		
		labelB = new JLabel("Nhập b: ");
		centerPanel.add(labelB);
		y += 50;
		labelB.setBounds(x, y, width, height);
		
		input2 = new JTextField();
		centerPanel.add(input2);
		b += 50;
		input2.setBounds(a, b, w, h);
		
		labelKQ = new JLabel("Kết quả: ");
		centerPanel.add(labelKQ);
		y += 200;
		labelKQ.setBounds(x, y, width, height);
		
		output = new JTextField();
		centerPanel.add(output);
		b += 200;
		output.setBounds(a, b, w, h);
		output.setEditable(false);

		// -- tạo các radio button và add vào center panel --
		radCong = new JRadioButton("Cộng", true);
		centerPanel.add(radCong);
		radCong.setBounds(120, 160, 100, 30);
		
        radTru = new JRadioButton("Trừ");
        centerPanel.add(radTru);
        radTru.setBounds(220, 160, 100, 30);
        
        radNhan = new JRadioButton("Nhân");
        centerPanel.add(radNhan);
        radNhan.setBounds(120, 200, 100, 30);
        
        radChia = new JRadioButton("Chia");
        centerPanel.add(radChia);
        radChia.setBounds(220, 200, 100, 30);
        
        // -- tạo border cho panel phép toán --
        JPanel subPanel = new JPanel();
        centerPanel.add(subPanel);
        subPanel.setBorder(BorderFactory.createTitledBorder("Phép toán: "));
        subPanel.setBounds(100, 140, 300, 120);
        
        // -- thêm radio button vào ButtonGroup để tránh bị chọn nhầm --
        ButtonGroup groupButton = new ButtonGroup();
        groupButton.add(radCong);
        groupButton.add(radTru);
        groupButton.add(radNhan);
        groupButton.add(radChia);
		
		//** south panel **
		JPanel southPanel = new JPanel();
		southPanel.setPreferredSize(new Dimension(600, 70));
		southPanel.setBackground(Color.PINK);
		contentPanel.add(southPanel, BorderLayout.SOUTH);
		
		//-- create 3 small panels --
		blueButton = new JButton();
		blueButton.setBackground(Color.BLUE);
		blueButton.add(Box.createRigidArea(new Dimension(5, 25)));
		
		
		redButton = new JButton();
		redButton.setBackground(Color.RED);
		redButton.add(Box.createRigidArea(new Dimension(5, 25)));
		
		
		greenButton = new JButton();
		greenButton.setBackground(Color.GREEN);
		greenButton.add(Box.createRigidArea(new Dimension(5, 25)));
		
		
		southPanel.add(blueButton);
		southPanel.add(Box.createRigidArea(new Dimension(10, 0)));
		southPanel.add(redButton);
		southPanel.add(Box.createRigidArea(new Dimension(10, 0)));
		southPanel.add(greenButton);
		
		// ** subscribe ActionListener **
		buttonGiai.addActionListener(this);
		buttonXoa.addActionListener(this);
		buttonThoat.addActionListener(this);
		blueButton.addActionListener(this);
		redButton.addActionListener(this);
		greenButton.addActionListener(this);
		
		// ** create container **
		Container container = getContentPane();
		container.add(contentPanel);
	}
	
	
	@Override
	public void actionPerformed (ActionEvent e) {
		Object o = e.getSource();
		double a = 0, b = 0, result = 0;
		
		if (o.equals(buttonThoat)) {
			System.exit(0);
		} else if (o.equals(blueButton)) {
			headingLabel.setForeground(Color.BLUE);
		} else if (o.equals(redButton)) {
			headingLabel.setForeground(Color.RED);
		} else if (o.equals(greenButton)) {
			headingLabel.setForeground(Color.GREEN);
		} else if (o.equals(buttonXoa)) {
			input1.setText("");
			input2.setText("");
			output.setText("");
			radCong.setSelected(true);
			input1.requestFocus();
		} else if (o.equals(buttonGiai)) {
			if (!isInt(input1)) {
				focus(input1);
			} else if (!isInt(input2)) {
				focus(input2);
			} else {
				a = Double.parseDouble(input1.getText());
				b = Double.parseDouble(input2.getText());
				String operator = "";
				
				if (radCong.isSelected()) {
//					result = a + b;
					operator = "+";
				} else if (radTru.isSelected()) {
//					result = a - b;
					operator = "-";
				} else if (radNhan.isSelected()) {
//					result = a * b;
					operator = "*";
				} else if (radChia.isSelected()) {
					if (b != 0)
//						result = a / b;
                        operator = "/";
                    else {
                    	focus(input2);
                        JOptionPane.showMessageDialog(null, "Lỗi chia cho 0 !!!");
                        input2.requestFocus();
                    }
				}
				output.setText(String.valueOf(result));
				
				// Build expression string
                String expression = a + " " + operator + " " + b;

                // Send expression to server and receive result
                try (
                        Socket socket = new Socket("localhost", SERVER_PORT);
                        OutputStream outputStream = socket.getOutputStream();
                        PrintWriter writer = new PrintWriter(outputStream, true);
                        InputStream inputStream = socket.getInputStream();
                        Scanner scanner = new Scanner(inputStream)
                ) {
                    // Send expression
                    writer.println(expression);

                    // Receive result from server
                    String resultString = scanner.nextLine();
                    result = Double.parseDouble(resultString);

                    // Update output field
                    output.setText(resultString);
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error: Connection failed");
                }
			}
		}	
	}
	
	
	private boolean isInt (JTextField text) {
		boolean result = true;
		try {
			Integer.parseInt(text.getText());
		} catch (NumberFormatException ex) {
			result = false;
		}
		return result;
	}
	
	
	private void focus (JTextField text) {
		JOptionPane.showMessageDialog(null, "Lỗi nhập liệu !!!");
		text.selectAll();
		text.requestFocus();
	}
	
	
	public static void main(String[] args) {
		FlatLightLaf.setup();
		new CalculatorClient().setVisible(true);
	}
}
