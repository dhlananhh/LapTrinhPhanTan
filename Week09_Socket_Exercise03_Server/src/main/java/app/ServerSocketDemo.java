package app;

import java.io.*;
import java.net.*;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import entities.Address;
import entities.News;
import entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import services.EntityManagerFactoryUtil;
import services.NewsService;
import services.UserService;

public class ServerSocketDemo {
	private static final int SERVER_PORT = 8080;
//	private static final int MAX_CLIENTS = 10; // Adjust based on server capacity
	
	
	public static void main(String[] args) throws IOException {
//		ExecutorService executor = Executors.newFixedThreadPool(MAX_CLIENTS);
		
		try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {
			System.out.println("Server started on port " + SERVER_PORT + " at " + new Date());
			System.out.println("Server is running...");
			System.out.println("================================");

			while (true) {
				// Accept new client connection
				Socket clientSocket = serverSocket.accept();
				System.out.println("New client connected: " + clientSocket.getRemoteSocketAddress());
				System.out.println("Number of active clients: " + Thread.activeCount());
				System.out.println("================================");

				// Handle client in a separate thread
				// Run class ClientHandler in a separate thread
				Thread t = new Thread(new ClientHandler(clientSocket));
				t.start();
			}
		}
	}
}
	
	
class ClientHandler implements Runnable {
	private Socket clientSocket;
	private EntityManagerFactoryUtil managerFactoryUtil;
	private EntityManager entityManager;
	private NewsService newsService;
	private UserService userService;
	
	
	public ClientHandler (Socket clientSocket) {
		this.clientSocket = clientSocket;
		this.managerFactoryUtil = new EntityManagerFactoryUtil();
		this.entityManager = managerFactoryUtil.getEntityManager();
		this.newsService = new NewsService(entityManager);
		this.userService = new UserService(entityManager);
	}


	@Override
	public void run() {
		try {
			ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
			ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
			
			int choice = 0;
			
			while (true) {
				choice = in.readInt();
				
				switch (choice) {
					case 1:
						// create and add a new user (also insert address object into user object)
						User user = (User) in.readObject();
						Address address = (Address) in.readObject();
						user.setAddress(address);
						userService.addUser(user);
						boolean isAdded = userService.addUser(user);
						out.writeBoolean(isAdded);
						out.flush();
						break;
						
					case 2:
						// get news
						String value = in.readUTF();
						System.out.println("Client requested to get news: " + value);
						List<News> news = newsService.getNewsByTagsOrNewsCategories(value);
						out.writeObject(news);
						out.flush();
						break;
						
					case 3:
						// get user statistics
						Map<User, Integer> map = userService.getStatistics();
						out.writeObject(map);
						out.flush();
						break;

				default:
					System.out.println("Invalid choice");
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.managerFactoryUtil.closeEntityManager();
			this.managerFactoryUtil.closeEntityManagerFactory();
		}
	}
}
