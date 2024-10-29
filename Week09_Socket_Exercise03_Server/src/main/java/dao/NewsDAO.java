package dao;


import java.util.List;
import entities.News;


public interface NewsDAO {
	public List<News> getNewsByTagsOrNewsCategories (String value);
}
