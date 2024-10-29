package dao;


import entities.User;
import java.util.Map;


public interface UserDAO {
	public boolean addUser (User user);
	public Map<User, Integer> getStatistics();
}
