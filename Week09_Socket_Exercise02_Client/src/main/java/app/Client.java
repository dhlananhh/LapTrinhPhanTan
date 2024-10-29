package app;

import java.awt.FlowLayout;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;

public class Client {

    private static final String HOST = "localhost"; // Địa chỉ server
    private static final int PORT = 8080; // Cổng kết nối

    private JTextField idField;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField emailField;
    private JTextField phoneField;
    private JCheckBox activeCheckBox;
    private JButton submitButton;
    private JTextArea responseArea;

    public static void main(String[] args) {
        Client client = new Client();
        client.createGUI();
        client.connectToServer();
    }

    private void createGUI() {
        JFrame frame = new JFrame("Client quản lý nhân viên");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 350);
        frame.setLayout(new FlowLayout());

        // Tạo các thành phần giao diện
        idField = new JTextField(10);
        firstNameField = new JTextField(15);
        lastNameField = new JTextField(15);
        emailField = new JTextField(20);
        phoneField = new JTextField(15);
        activeCheckBox = new JCheckBox("Hoạt động");
        submitButton = new JButton("Gửi");
        responseArea = new JTextArea(10, 40);

        // Thêm thành phần vào giao diện
        frame.add(new JLabel("Mã nhân viên: "));
        frame.add(idField);
        frame.add(new JLabel("Họ: "));
        frame.add(firstNameField);
        frame.add(new JLabel("Tên: "));
        frame.add(lastNameField);
        frame.add(new JLabel("Email: "));
        frame.add(emailField);
        frame.add(new JLabel("Số điện thoại: "));
        frame.add(phoneField);
        frame.add(activeCheckBox);
        frame.add(submitButton);
        frame.add(new JScrollPane(responseArea));

        // Thêm sự kiện cho nút submit
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submitStaffData();
            }
        });

        frame.setVisible(true);
    }

    private void connectToServer() {
        try (Socket socket = new Socket(HOST, PORT)) {
            // Gửi request tới server
            DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());

            // Xử lý kết quả trả về từ server
            String response = inputStream.readUTF();
            responseArea.append(response + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void submitStaffData() {
        try (Socket socket = new Socket(HOST, PORT)) {
            // Tạo chuỗi request chứa thông tin nhân viên
            String request = idField.getText() + "|" + firstNameField.getText() + "|" + lastNameField.getText() + "|" + emailField.getText() + "|" + phoneField.getText() + "|" + (activeCheckBox.isSelected() ? "1" : "0");

            // Gửi request tới server
            DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());

            outputStream.writeUTF(request);
            System.out.println("Gửi request tới server: " + request);

            // Xử lý kết quả trả về từ server
            String response = inputStream.readUTF();
            responseArea.append(response + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
