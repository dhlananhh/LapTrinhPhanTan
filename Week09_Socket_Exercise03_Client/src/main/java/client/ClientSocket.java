package client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.Map;

import entities.User;
import entities.Address;

public class ClientSocket {
	@SuppressWarnings("unchecked")
	public static void main (String[] args) {
		try (Scanner scanner = new Scanner(System.in)) {
			try (Socket socket = new Socket("192.168.1.227", 8080)) {
				ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
				ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
				
				int choice = 0;
				System.out.println("Enter your choice: \n" +
					"1. Add a new user \n" +
					"2. Get news by tags or news categories \n" +
					"3. Get user statistics");
				
				while (true) {
					choice = scanner.nextInt();
					out.writeInt(choice);
					out.flush();
					
					switch (choice) {
					case 1:
						// create and add a new user (also insert address object into user object)
//						User user = new User();
//						Address address = new Address();
//						System.out.println("Enter user id: ");
//						user.setUserId(scanner.nextLong());
//						System.out.println("Enter user name: ");
//						user.setUserName(scanner.next());
//						System.out.println("Enter user email: ");
//						user.setUserEmail(scanner.next());
//						System.out.println("Enter user password: ");
//						user.setUserPassword(scanner.next());
//						System.out.println("Enter research areas (comma separated): ");
//						user.setResearchAreas(scanner.hasNextLine());
//						System.out.println("Enter address id: ");
//						address.setAddressId(scanner.nextLong());
						
						break;
						
					case 2: 
						// Get news by tags or news categories
						System.out.println("Enter tags or news categories (comma separated): ");
						String value = scanner.next();
						out.writeUTF(value);
						out.flush();
						break;
						
					case 3:
						// Get user statistics
						Map<User, Integer> map = (Map<User, Integer>) in.readObject();
						map.entrySet().forEach(entry -> {
							System.out.println("User: " + entry.getKey() + ", Count: " + entry.getValue());
						});
						break;
						
					default:
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
