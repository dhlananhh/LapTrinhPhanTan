package exercise02.demo;

import java.util.ArrayList;
import java.util.List;

import org.bson.BsonInt32;
import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.reactivestreams.client.MongoDatabase;

import exercise02.dao.ZipDAO;
import exercise02.entities.Location;
import exercise02.entities.Zip;
import exercise02.utils.DBConnection;

public class ZipDemo {
	public static void main (String[] args) throws InterruptedException {
		DBConnection conn = new DBConnection();
		MongoDatabase db = conn.getInstance().getDatabase();
		
		ZipDAO zipDAO = new ZipDAO(db);
		
		
		// Hiển thị danh sách các zipscode
//		List<Zip> lstZips = zipDAO.getZipData();
		
//		System.out.println("1. Show Zip list");
//		lstZips.forEach(z -> System.out.println(z));
		
		
		// 1. Hiển thị n documents từ document thứ k.
		// Ví dụ: Hiển thị 5 documents từ document thứ 10
		System.out.println("1. Display n documents from the kth document.");
		List<Zip> lstZips2 = zipDAO.displayDocuments(10, 5);
		lstZips2.forEach(z -> System.out.println(z));
		
		
		// 2. Chèn thêm 1 document mới
/*	
		Zip zip = new Zip();
		
		zip.setId(ObjectId.get());
		zip.setCity("Vermont");
		zip.setState("VT");
		zip.setPop(647464);
		
		Location loc = new Location(45.0, 73.4);
		zip.setLoc(loc);
		
		System.out.println("2. Insert new document");
		System.out.println(zipDAO.insertOneNewZip(zip));
*/
		
		
		// 3. Cập nhật document khi biết id
//		System.out.println("3. Update document by id");
//		System.out.println(zipDAO.updatePop(zip.getId(), 9999.0));
		
		
		// 4. Tìm các document có city là PALMER
		System.out.println("4. Find documents by city name");
		List<Zip> lstZips3 = zipDAO.findZipByCityName("PALMER");
		lstZips3.forEach(z -> System.out.println(z));
		
		
		// 5. Tìm các document có dân số >100000
		System.out.println("5. Find documents with population > 100000");
		List<Zip> lstZips4 = zipDAO.findZipByPop(100000);
		lstZips4.forEach(z -> System.out.println(z));

		
		// 6. Tìm dân số của thành phố FISHERS ISLAND
		System.out.println("6. Find population by city name");
		System.out.println(zipDAO.findPopByCityName("FISHERS ISLAND"));
		
		
		// 7. Tìm các thành phố có dân số từ 10 – 50
		System.out.println("7. Find documents with population from 10 to 50");
		List<Zip> lstZips5 = zipDAO.findZipByPopRange(10, 50);
		lstZips5.forEach(z -> System.out.println(z));
		
		
		// 8. Tìm tất cả các thành phố của bang MA có dân số trên 500
		List<Zip> lstZips6 = zipDAO.findZipByStateAndPop("MA", 500);
		System.out.println("8. Find documents with state MA and population > 500");
		lstZips6.forEach(z -> System.out.println(z));
		
		
		// 9. Tìm tất cả các bang (không trùng)
		System.out.println("9. Find all states (no duplicates)");
		List<Document> lstZips7 = zipDAO.findDistinctStates();
		lstZips7.forEach(z -> System.out.println(z));
		
		
		// 10. Tìm tất cả các bang mà có chứa ít nhất 1 thành phố có dân số trên 100000
		System.out.println("10. Find all states that have at least 1 city with population > 100000");
		List<Document> lstZips8 = zipDAO.findStatesWithPopOver(100000);
		lstZips8.forEach(z -> System.out.println(z));
		
		
		// 11. Tính dân số trung bình của mỗi bang
		System.out.println("11. Calculate average population by state");
		List<Document> lstZips11 = zipDAO.avgPopByState();
		lstZips11.forEach(z -> System.out.println(z));
		
		
		// 12. Tìm những document của bang 'CT' và thành phố 'WATERBURY'
		System.out.println("12. Find documents by CT state and WATERBURY city");
		List<Zip> lstZips12 = zipDAO.findZipByStateAndCity("CT", "WATERBURY");
		lstZips12.forEach(z -> System.out.println(z));
		
		
		// 13. Bang WA có bao nhiêu city (nếu trùng chỉ tính 1 lần)
		System.out.println("13. Count cities in WA state (no duplicates)");
		List<Document> lstZips13 = zipDAO.countCityInState("WA");
		lstZips13.forEach(z -> System.out.println(z));
		
		
		// 14. Tính số city của mỗi bang (nếu trùng chỉ tính 1 lần), kết quả giảm dần theo số city
		System.out.println("14. Count cities by state (no duplicates), sort by city count descending");
		List<Document> lstZips14 = zipDAO.countCityInStateDesc();
		lstZips14.forEach(z -> System.out.println(z));
		
		
		// 15. Tìm tất cả các bang có tổng dân số trên 10000000
		System.out.println("15. Find all states with total population over 10000000");
		List<Document> lstZips15 = zipDAO.findStatesWithTotalPopOver(10000000);
		lstZips15.forEach(z -> System.out.println(z));
		
		
		// 17. Tìm bang có tổng dân số lớn nhất
		System.out.println("17. Find state with the largest total population");
        System.out.println(zipDAO.findStateWithLargestPop());		
	}
}
