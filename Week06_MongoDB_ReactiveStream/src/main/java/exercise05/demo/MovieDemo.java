package exercise05.demo;

import java.util.List;

import org.bson.Document;

import com.mongodb.reactivestreams.client.MongoDatabase;

import exercise05.utils.DBConnection;
import exercise05.dao.MovieDAO;

public class MovieDemo {
	public static void main(String[] args) {
		DBConnection conn = new DBConnection();
		MongoDatabase db = conn.getInstance().getDatabase();
		
		MovieDAO movieDAO = new MovieDAO(db);
		
		
		// 1. Liệt kê danh sách các đạo diễn có tham gia từ 30 bộ phim trở lên. 
		// Thông tin bao gồm: Tên đạo diễn (director) và số bộ phim.
//		System.out.println("1. List of directors over 30 movies");
//		List<Document> lstDirectors1 = movieDAO.findDirectorsOver30Movies();
//		lstDirectors1.forEach(d -> System.out.println(d));
		
		
		// 2. Tìm tất cả các đạo diễn có tham gia đạo diễn nhiều bộ phim nhất.
//		System.out.println("2. Find all directors with the most movies directed");
//		List<Document> lstDirectors2 = movieDAO.findDirectorsWithMostMovies();
//		lstDirectors2.forEach(d -> System.out.println(d));
		
		
		// 3. Liệt kê tựa phim (title) theo từng đạo diễn.
//		System.out.println("3. List of movies by director");
//		List<Document> lstMoviesByDirector = movieDAO.findMoviesByDirector();
//		lstMoviesByDirector.forEach(d -> System.out.println(d));
		
		
		// 4. Thống kê số bộ phim đã phát hành theo từng năm, sắp xếp giảm dần theo năm.
//		System.out.println("4. Number of movies released by year");
//		List<Document> lstMoviesByYear = movieDAO.countMoviesByYear();
//		lstMoviesByYear.forEach(d -> System.out.println(d));
		
		
		// 5. Tìm năm phát hành nhiều bộ phim nhất.
		System.out.println("5. Find the year with the most movies released");
		List<Document> lstYearWithMostMovies = movieDAO.findYearWithMostMovies();
		lstYearWithMostMovies.forEach(d -> System.out.println(d));
		
		
		// 6. Liệt kê danh sách các tựa phim (title) theo từng quốc gia.
		System.out.println("6. List of movies by country");
		List<Document> lstMoviesByCountry = movieDAO.findMoviesByCountry();
		lstMoviesByCountry.forEach(d -> System.out.println(d));
		
		
		// 7. Đếm số bộ phim theo từng quốc gia, sắp xếp giảm dần theo số bộ phim.
		System.out.println("7. Count of movies by country");
		List<Document> lstCountMoviesByCountry = movieDAO.countMoviesByCountry();
		lstCountMoviesByCountry.forEach(d -> System.out.println(d));
		
		
		// 8. Tìm những tựa phim (title) phát hành trong tháng 03 năm 2016.
		System.out.println("8. Find movies released in March 2016");
		List<Document> lstMoviesInMarch2016 = movieDAO.findMoviesInMarch2016();
		lstMoviesInMarch2016.forEach(d -> System.out.println(d));
		
		
		// 9. Liệt kê những tựa phim (title) do diễn viên “Frank Powell” hoặc “Charles Wellesley” đóng.
		System.out.println("9. Find movies with actors Frank Powell or Charles Wellesley");
		List<Document> lstMoviesByActors = movieDAO.findMoviesByCast();
		lstMoviesByActors.forEach(d -> System.out.println(d));
		
		
		// 10. Tìm những quốc gia phát hành nhiều bộ phim nhất.
		System.out.println("10. Find countries with the most movies released");
		List<Document> lstCountriesWithMostMovies = movieDAO.findCountriesWithMostMovies();
		lstCountriesWithMostMovies.forEach(d -> System.out.println(d));
	}
}
