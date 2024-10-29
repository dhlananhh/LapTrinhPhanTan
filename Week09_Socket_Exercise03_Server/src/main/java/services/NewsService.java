package services;


import java.util.List;

import dao.NewsDAO;
import entities.News;
import jakarta.persistence.EntityManager;


public class NewsService implements NewsDAO {
	private EntityManager entityManager;

	
	public NewsService (EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	

	/*	
	
	*/
	@Override
	public List<News> getNewsByTagsOrNewsCategories (String value) {
		return entityManager
				.createQuery("SELECT n FROM News n WHERE n.news_tags LIKE :value OR n.news_categories LIKE :value",
						News.class)
				.setParameter("value", "%" + value + "%").getResultList();
	}

}
