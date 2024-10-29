package server;


import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;


public class Server {
	private static final int PORT = 4005;
	
	
	public static void main(String[] args) {
		try (ServerSocket serverSocket = new ServerSocket(PORT)) {
			System.out.println("Server is listening on port " + PORT);

			while (true) {
				Socket socket = serverSocket.accept();

				System.out.println("New client connected");

				new Thread(() -> handleClient(socket)).start();
			}
		} catch (IOException ex) {
			System.out.println("Server exception: " + ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	
	private static void handleClient (Socket clientSocket) {
		try (InputStream input = clientSocket.getInputStream();
            OutputStream output = clientSocket.getOutputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            PrintWriter writer = new PrintWriter(output, true)) {
            
			String requestPath = reader.readLine();
			File file = new File(requestPath);
			
			if (file.exists() && file.isDirectory()) {
				List<File> files = listFiles(file);
				String response = buildResponse(files);
				writer.write(response);
				writer.flush();
			} else {
				writer.write("Invalid path");
				writer.flush();
			}
			
			clientSocket.close();
        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
	}
	
	
	private static List<File> listFiles (File directory) {
		List<File> files = new ArrayList<File>();
		File[] subFiles = directory.listFiles();

		if (subFiles != null) {
			for (File subFile : subFiles) {
				if (subFile.isDirectory()) {
					files.addAll(listFiles(subFile));
				} else {
					files.add(subFile);
				}
			}
		}

		return files;
	}
	
	
	private static String buildResponse (List<File> files) {
		StringBuilder response = new StringBuilder();

		for (File file : files) {
			response.append(file.getName()).append("\n");
		}

		return response.toString();
	}
}
