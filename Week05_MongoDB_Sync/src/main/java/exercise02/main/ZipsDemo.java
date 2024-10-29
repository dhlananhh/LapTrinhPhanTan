package exercise02.main;

import org.bson.Document;

import java.sql.SQLException;
import java.util.List;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;

import exercise02.dao.AbstractDAO;
import exercise02.dao.ZipDAO;
import exercise02.utils.*;


public class ZipsDemo {
	public static void main (String[] args) {
//		DBConnection conn = new DBConnection();
		MongoClient mongoClient = AbstractDAO.getInstance().getClient();
		ZipDAO zipsDAO = new ZipDAO(mongoClient);
		
		System.out.println("Exercise 2");
		
		
		// 1. Hiển thị n documents từ document thứ k
		System.out.println("\n1. Display n documents from the kth document");
		zipsDAO.display(4);
		
		
		// 2. Chèn thêm 1 document mới vào collection
		Document doc = new Document("city", "ALPINE")
		.append("zip", "1234")
		.append("loc", new Document("y", 33.331165).append("x", 86.208934))
		.append("pop", 3062)
		.append("state", "AL");
		
		boolean result2 = zipsDAO.insert(doc);
		
		System.out.println("2. Insert a new document into the collection");
		System.out.println(result2);
		
		
		// 3. Cập nhật thông tin của một document khi biết id
		boolean result3 = zipsDAO.update("631223297fd029516d39eaee", new Document("city",
		      "ALPINEUpdate"));
		
		System.out.println("3. Update information of a document when knowing the id");
		System.out.println(result3);
		
		
		// 4. Tìm các document có city là PALMER
		System.out.println("4. Find documents with city PALMER");
		List<Document> documents = zipsDAO.findByCity("PALMER");
		for (Document record : documents) {
		    if (record != null) {
		        System.out.println(record);
		    }
		}
		
		
		// 5. Tìm các document có dân số > 100000
		// Chèn thêm document có dân số > 100000
		Document newDoc = new Document("city", "AUSTIN")
		.append("zip", "1234")
		.append("loc", new Document("y", 33.331165).append("x", 86.208934))
		.append("pop", 100000)
		.append("state", "AU");
		
		boolean result_newDoc = zipsDAO.insert(newDoc);
		
		System.out.println("Insert a new document with population > 100000 into the collection");
		System.out.println(result_newDoc);
		
		
		System.out.println("5. Find documents with population > 100000");
		AggregateIterable<Document> data = zipsDAO.findByPop(100000);
		for (Document record : data) {
			if (record != null) {
				System.out.println(record);
			} else {
				System.out.println("");
			}
		}
		
		
		// 6. Tìm dân số của thành phố FISHERS ISLAND
		System.out.println("6. Find the population of FISHERS ISLAND city");
		 zipsDAO.findByCityAndPop("FISHERS ISLAND");

		 
		 // 7. Tìm các thành phố có dân số từ 10-50
		 System.out.println("7. Find cities with populations between 10-50");
		 zipsDAO.findByPopRange(10, 50);
		 
		 
		 // 8. Tìm tất cả các thành phố của bang MA có dân số trên 500
		 System.out.println("8. Find all cities in MA state with populations over 500");
		 zipsDAO.findByPopRangeAndCity(500, "MA");
		 
		 
		 // 9. Tìm tất cả các bang (không trùng)
		 System.out.println("9. Find all states (no duplicates)");
		 zipsDAO.findByAllCity();
		 
		 // 10. Tìm tất cả các bang mà có chứa ít nhất 1 thành phố có dân số trên 100000
		 System.out.println("10. Find all states that contain at least 1 city with a population over 100000");
		 zipsDAO.findStateWithPopOver100000();
		 
		 // 11. Tính dân số trung bình của mỗi bang
		 System.out.println("11. Calculate the average population of each state");
		 zipsDAO.calculateAveragePop();
		 
		 // 12. Tìm những document của bang 'CT' và thành phố 'WATERBURY'
		 System.out.println("12. Find documents of state 'CT' and city 'WATERBURY'");
		 zipsDAO.findByStateAndCity("CT", "WATERBURY");
		 
		 // 13. Bang WA có bao nhiêu city (nếu trùng chỉ tính 1 lần)
		 System.out.println("13. How many cities does WA have (if duplicates only count once)");
		 zipsDAO.countCityOfState("WA");
		 
		 // 14. Tính số city của mỗi bang (nếu trùng chỉ tính 1 lần), kết quả giảm dần theo số city
		 System.out.println("14. Calculate the number of cities in each state (if duplicates only count once), the result decreases by the number of cities");
		 zipsDAO.countCityOfEachState();
		 
		 // 15. Tìm tất cả các bang có tổng dân số trên 10000000
		 System.out.println("15. Find all states with a total population over 10000000");
		 zipsDAO.findStateWithTotalPopOver10000000();
		 
		 // 16. Tìm các document có dân số lớn (nhỏ) nhất
		 System.out.println("16. Find documents with the largest (smallest) population");
		 zipsDAO.findDocWithLargestAndSmallestPop();
		 
		 // 17. Tìm bang có tổng dân số lớn nhất
		 System.out.println("17. Find the state with the largest total population");
		 zipsDAO.findStateWithLargestTotalPop();
		 
		 // 18. Xuất những document có dân số dưới dân số trung bình của mỗi city.
		 System.out.println("18. Export documents with populations below the average population of each city");
		 zipsDAO.findDocWithPopBelowAverage();
		 
		 // 19. Dựa vào tập kết quả câu trên, xóa các documents khi biết city
		 
		 // Chèn thêm 1 document mới vào collection, có city là HCMC
         Document docHCMC = new Document("city", "HCMC")
					.append("zip", "1234")
					.append("loc", new Document("y", 33.331165).append("x", 86.208934)).append("pop", 3062)
					.append("state", "AL");
         zipsDAO.insert(docHCMC);
         System.out.println("Insert a new document into the collection with city HCMC");
						
		 System.out.println("19. Based on the above results, delete documents when knowing the city");
		 zipsDAO.deleteDocByCity("HCMC");
		 System.out.println("Delete document with city HCMC");
	}
}
