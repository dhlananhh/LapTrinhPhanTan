package app;


import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import dao.NewsDAO;
import dao.UserDAO;



public class Client {
	public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {
		String URL = "rmi://192.168.1.227:3002/";
		
		UserDAO userDAO = (UserDAO) Naming.lookup(URL + "userDAO");
		NewsDAO newsDAO = (NewsDAO) Naming.lookup(URL + "newsDAO");
		
		// tìm tương đối các tin tức có tags hoặc categories là "covid"
//		newsDAO.getNewsByTagsOrNewsCategories("tech").forEach(news -> {
//			System.out.println(news);
//		});
		
		// thống kê số lượng bình luận của từng người dùng
//		userDAO.getStatistics().forEach((user, count) -> {
//			System.out.println(user + " - " + count);
//		});
		
		// liệt kê tất cả tin tức
		newsDAO.listAllNews().forEach(news -> {
			System.out.println(news);
		});
	}

}
