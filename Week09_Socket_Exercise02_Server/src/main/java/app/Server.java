package app;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.net.ServerSocket;
import java.net.Socket;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import dao.StaffDAO;
import entity.Staff;

import jakarta.persistence.EntityManager;
import services.EntityManagerFactoryUtil;
import services.StaffService;


public class Server {
	public static void main(String[] args) {
		try (ServerSocket serverSocket = new ServerSocket(3002)) {
			// Create a thread pool to handle multiple clients
			ExecutorService executorService = Executors.newCachedThreadPool();
			
			// Start accepting client connections
			System.out.println("Server is listening on port 3002");
			
			while (true) {
				// Accept a client connection
				Socket clientSocket = serverSocket.accept();
				System.out.println("Client connected: " + clientSocket.getInetAddress().getHostAddress());
				
				// Create a new thread for the client and submit it to the thread pool
				ClientHandler clientHandler = new ClientHandler(clientSocket);
				executorService.execute(clientHandler);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	private static class ClientHandler implements Runnable {
		private Socket clientSocket;
		private ObjectInputStream in;
		private ObjectOutputStream out;
		private EntityManagerFactoryUtil managerFactoryUtil;
		private EntityManager entityManager;
		

		public ClientHandler(Socket clientSocket) {
			this.clientSocket = clientSocket;
			this.managerFactoryUtil = new EntityManagerFactoryUtil();
			this.entityManager = managerFactoryUtil.getEntityManager();
		}

		@Override
		public void run() {
			try {
				// Create input and output streams for data transmission
				in = new ObjectInputStream(clientSocket.getInputStream());
				out = new ObjectOutputStream(clientSocket.getOutputStream());
				
				// Read data from the client
				while (true) {
					String clientData = in.readUTF();
					System.out.println("Received data from client: " + clientData);
					
					String[] items = clientData.split("&", 2);
					if (items.length == 0) {
						System.out.println("Missing request from client");
						return;
					}
					
					switch (items[0]) {
					
						case "addStaff":
							// Add a new staff
							StaffDAO staffDAO = new StaffService(entityManager);
							Staff staff = (Staff) in.readObject();
							boolean result = staffDAO.addStaff(staff);
							out.writeBoolean(result);
							out.flush();
							break;
						
						case "updateStaff":
							// Update a staff
							staffDAO = new StaffService(entityManager);
							staff = (Staff) in.readObject();
							result = staffDAO.updateStaff(staff);
							out.writeBoolean(result);
							out.flush();
							break;
							
						case "deleteStaff":
							// Delete a staff
							staffDAO = new StaffService(entityManager);
							long id = in.readLong();
							result = staffDAO.deleteStaff(id);
							out.writeBoolean(result);
							out.flush();
							break;
							
						case "findStaffById":
							// Find a staff by ID
							staffDAO = new StaffService(entityManager);
							id = in.readLong();
							staff = staffDAO.getStaffById(id);
							out.writeObject(staff);
							out.flush();
							break;
							
						default:
							System.out.println("Invalid request from client");
							break;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					in.close();
					out.close();
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
				managerFactoryUtil.closeEntityManager();
				managerFactoryUtil.closeEntityManagerFactory();
			}
		}
	}
}
