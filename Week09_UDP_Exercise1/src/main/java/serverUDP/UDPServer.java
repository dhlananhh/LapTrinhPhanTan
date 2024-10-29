package serverUDP;


import java.io.*;
import java.net.*;


public class UDPServer {

    public static void main(String[] args) throws Exception {
        // Cổng server lắng nghe
        int port = 2520;

        // Tạo socket server
        DatagramSocket serverSocket = new DatagramSocket(port);

        // Buffer để nhận dữ liệu
        byte[] receiveBuffer = new byte[1024];

        // Vòng lặp để nhận yêu cầu từ client
        while (true) {
            // Nhận datagram từ client
            DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
            serverSocket.receive(receivePacket);

            // Lấy địa chỉ và cổng của client
            InetAddress clientAddress = receivePacket.getAddress();
            int clientPort = receivePacket.getPort();

            // Xử lý yêu cầu của client
            handleClientRequest(clientAddress, clientPort);
        }
    }

    private static void handleClientRequest(InetAddress clientAddress, int clientPort) throws Exception {
        // Tên file ảnh
        String fileName = "img/image.jpg";

        // Đọc file ảnh
        byte[] imageData = readFile(fileName);

        // Tạo datagram để gửi file ảnh
        DatagramPacket sendPacket = new DatagramPacket(imageData, imageData.length, clientAddress, clientPort);

        // Gửi file ảnh đến client
        DatagramSocket serverSocket = new DatagramSocket();
        serverSocket.send(sendPacket);

        // Đóng socket
        serverSocket.close();
    }

    private static byte[] readFile (String fileName) throws IOException {
        File file = new File(fileName);
        FileInputStream fis = new FileInputStream(file);
        byte[] data = new byte[(int) file.length()];
        fis.read(data);
        fis.close();
        return data;
    }
}
