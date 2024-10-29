package client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import com.formdev.flatlaf.FlatLightLaf;

public class ClientGUI {
	private static final String SERVER_ADDRESS = "localhost";
	private static final int PORT = 4005;
	
	
	private JLabel lblRequestURL, lblServerPort;
	private JTextField txtRequestURL, txtServerPort;
	private JTree resultTree;
	
	
	public void createAndShowGUI() {
		JFrame frame = new JFrame("Remote File Manager Client");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 600);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setLayout(new BorderLayout());
		
		
		JPanel requestPanel = new JPanel();
		
		lblServerPort = new JLabel("Server: ");
        requestPanel.add(lblServerPort);
        
        txtServerPort = new JTextField(20);
        requestPanel.add(txtServerPort);
		
		lblRequestURL = new JLabel("Path: ");
		requestPanel.add(lblRequestURL);
		
        txtRequestURL = new JTextField(20);
        requestPanel.add(txtRequestURL);
        

        JButton btnRequest = new JButton("Send request");
        btnRequest.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				sendRequest();
			}
		});
        requestPanel.add(btnRequest);
        
        frame.getContentPane().add(requestPanel, BorderLayout.NORTH);
        
        resultTree = new JTree();
        JScrollPane scrollPane = new JScrollPane(resultTree);
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
        
        frame.setVisible(true);
	}
	
	
	private void sendRequest() {
		String requestPath = txtRequestURL.getText();
		
		try (Socket socket = new Socket(SERVER_ADDRESS, PORT);
            InputStream input = socket.getInputStream();
            OutputStream output = socket.getOutputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            PrintWriter writer = new PrintWriter(output, true)) {
            
			output.write((requestPath + "\n").getBytes());
            output.flush();
            
            byte[] buffer = new byte[1024];
            int bytesRead = input.read(buffer);
            
			if (bytesRead == -1) {
				System.out.println("Server closed connection");
				return;
			} else if (bytesRead > 0) {
				System.out.println("Server response: " + "\n" + new String(buffer, 0, bytesRead));
				displayResponse(new String(buffer, 0, bytesRead));
			}
        } catch (IOException ex) {
            System.out.println("Client exception: " + ex.getMessage());
            ex.printStackTrace();
        }
	}
	
	
	private void displayResponse (String response) {
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Results");

        String[] lines = response.split("\n");
        for (String line : lines) {
            DefaultMutableTreeNode node = new DefaultMutableTreeNode(line);
            root.add(node);
        }

        resultTree.setModel(new DefaultTreeModel(root));
	}
	
	
	public static void main(String[] args) {
		FlatLightLaf.setup();
		new ClientGUI().createAndShowGUI();
	}
}
