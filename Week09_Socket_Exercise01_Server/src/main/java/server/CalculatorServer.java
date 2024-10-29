package server;


import java.io.*;
import java.net.*;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class CalculatorServer {

    private static final int SERVER_PORT = 8000;
    private static final int MAX_CLIENTS = 10; // Adjust based on server capacity

    
    public static void main(String[] args) throws IOException {
        ExecutorService executor = Executors.newFixedThreadPool(MAX_CLIENTS);

        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {
            System.out.println("Calculator Server started on port " + SERVER_PORT + " at " + new Date());
            System.out.println("Server is running...");
            System.out.println("================================");

            while (true) {
                // Accept new client connection
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket.getRemoteSocketAddress());
                System.out.println("Number of active clients: " + Thread.activeCount());
                System.out.println("================================");

                // Handle client in a separate thread
                executor.execute(new ClientHandler(clientSocket));
            }
        }
    }
}

class ClientHandler implements Runnable {

    private final Socket clientSocket;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try (
                InputStream inputStream = clientSocket.getInputStream();
                OutputStream outputStream = clientSocket.getOutputStream();
                Scanner scanner = new Scanner(inputStream);
                PrintWriter writer = new PrintWriter(outputStream, true)
        ) {
            // Receive expression from client
            String expression = scanner.nextLine();

            // Calculate result
            double result = calculate(expression);

            // Send result back to client
            writer.println(result);

        } catch (IOException e) {
            System.err.println("Error communicating with client: " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private double calculate(String expression) {
        // Implement your logic to parse the expression and perform the calculation
        // You can use String.split() and switch-case for basic arithmetic operations
        // Remember to handle division by zero and other potential errors

        // Example implementation (replace with your actual logic)
        String[] parts = expression.split(" ");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid expression format");
        }

        double a = Double.parseDouble(parts[0]);
        double b = Double.parseDouble(parts[2]);
        String operator = parts[1];

        switch (operator) {
            case "+":
                return a + b;
            case "-":
                return a - b;
            case "*":
                return a * b;
            case "/":
                if (b == 0) {
                    throw new ArithmeticException("Division by zero");
                }
                return a / b;
            default:
                throw new IllegalArgumentException("Unsupported operator: " + operator);
        }
    }
}
