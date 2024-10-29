package clientUDP;


import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;
import javax.imageio.ImageIO;
import javax.swing.*;


public class UDPClient {

    public static void main(String[] args) throws Exception {
        // Cổng server
        int port = 2520;

        // Tạo socket client
        DatagramSocket clientSocket = new DatagramSocket();

        // Gửi yêu cầu đến server
        byte[] sendBuffer = new byte[1024];
        DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, InetAddress.getByName("localhost"), port);
        clientSocket.send(sendPacket);

        // Buffer để nhận dữ liệu
        byte[] receiveBuffer = new byte[1024];

        // Nhận datagram từ server
        DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
        clientSocket.receive(receivePacket);

        // Lấy dữ liệu ảnh từ datagram
        byte[] imageData = receivePacket.getData();

        // Chuyển đổi dữ liệu ảnh thành buffered image
        BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageData));

        // Hiển thị ảnh lên JFrame
        JFrame frame = new JFrame();
        frame.add(new JLabel(new ImageIcon(image)));
        frame.pack();
        frame.setVisible(true);

        // Đóng socket
        clientSocket.close();
    }
}
