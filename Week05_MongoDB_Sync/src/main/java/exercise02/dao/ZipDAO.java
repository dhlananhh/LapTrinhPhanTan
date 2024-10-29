package exercise02.dao;


import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.mongodb.MongoException;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.result.InsertOneResult;


public class ZipDAO extends AbstractDAO {
	private MongoCollection<Document> zipsCollection;

  
	public ZipDAO(MongoClient client) {
		super(client);
		zipsCollection = db.getCollection("zips");
	}

	
	// 1. Hiển thị n documents từ document thứ k
	public void display(int n) {
		MongoCursor<Document> it = zipsCollection.find().iterator();
		int i = 0;
		while (it.hasNext()) {
			Document doc = it.next();
			System.out.println(doc);
			i++;
			
			if (i == n)
				break;
		}
	}
	

	// 2. Chèn thêm 1 document mới vào collection
	public boolean insert(Document doc) {
		try {
			InsertOneResult result = zipsCollection.insertOne(doc);
			return result.getInsertedId() != null ? true : false;
		} catch (MongoException e) {
			e.printStackTrace();
			return false;
		}
	}

	
	// 3. Cập nhật thông tin của một document khi biết id
	public boolean update(String id, Document doc) {
		try {
			Document filter = new Document("_id", new ObjectId(id));
			Document update = new Document("$set", doc);
			zipsCollection.updateOne(filter, update);
			return true;
	    } catch (MongoException e) {
	    	e.printStackTrace();
	    	return false;
	    }
	}

	
	// 4. Tìm các document có city là PALMER
	public List<Document> findByCity() {
		Document filter = new Document("city", "PALMER");
		return zipsCollection.find(filter).into(Arrays.asList());
	}
	
	public List<Document> findByCity(String city) {
	    Document filter = new Document("city", city);
	    return zipsCollection.find(filter).into(new ArrayList<>());
	}


	// 5. Tìm các document có dân số > 100000
	public AggregateIterable<Document> findByPop(int pop) {
		Document filter = new Document("pop", new Document("$gt", pop));
		AggregateIterable<Document> data = zipsCollection.aggregate(Arrays.asList(Aggregates.match(filter)));
		
		return data;
	}

	
	// 6. Tìm dân số của thành phố FISHERS ISLAND
	public void findByCityAndPop(String city) {
	    Document filter = new Document("city", city);
	    Document projection = new Document("_id", 0).append("pop", 1);
	    MongoCursor<Document> it = zipsCollection.find(filter).projection(projection).iterator();
	    
	    while (it.hasNext()) {
	    	Document doc = it.next();
	    	System.out.println(doc);
	    }
	}

	// 7. Tìm các thành phố có dân số từ 10-50
	public void findByPopRange(int from, int to) {
		Document fromPop = new Document("pop", new Document("$gt", from));
	    Document toPop = new Document("pop", new Document("$lt", to));
	    Document filter = new Document("$and", Arrays.asList(fromPop, toPop));
	    Document projection = new Document("_id", 0).append("city", 1).append("pop", 1);
	    MongoCursor<Document> it = zipsCollection.find(filter).projection(projection).iterator();
	    
	    while (it.hasNext()) {
	    	Document doc = it.next();
	    	System.out.println(doc);
	    }
	}

	
	// 8. Tìm tất cả các thành phố của bang MA có dân số trên 500
	public void findByPopRangeAndCity(int pop, String city) {
	    Document popRange = new Document("pop", new Document("$gt", pop));
	    Document cityFilter = new Document("state", city);
	    Document filter = new Document("$and", Arrays.asList(popRange, cityFilter));
	    Document projection = new Document("_id", 0).append("state", 1).append("pop", 1);
	    MongoCursor<Document> it = zipsCollection.find(filter).projection(projection).iterator();
	    
	    while (it.hasNext()) {
	    	Document doc = it.next();
	    	System.out.println(doc);
	    }
	}

	
	// 9. Tìm tất cả các bang (không trùng)
	public void findByAllCity() {
		Document popRange = new Document(
			"$group", new Document("_id",
			"$state").append("count", new Document("$sum", 1))
		);
		
		AggregateIterable<Document> docs = zipsCollection.aggregate(Arrays.asList(popRange));
		MongoCursor<Document> it = docs.iterator();
		
	    while (it.hasNext()) {
	    	Document doc = it.next();
	    	System.out.println(doc);
	    }
	}
	
	
	// 10. Tìm tất cả các bang mà có chứa ít nhất 1 thành phố có dân số trên 100000
	public void findStateWithPopOver100000() {
		Document subMatch = new Document("$match", new Document("pop", new Document("$gt", 100000)));
		Document subGroup = new Document("$group", new Document("_id", "$state"));

		AggregateIterable<Document> docs = zipsCollection.aggregate(Arrays.asList(subMatch, subGroup));

		MongoCursor<Document> it = docs.iterator();
	    while (it.hasNext()) {
	    	Document doc = it.next();
	    	System.out.println(doc);
	    }
  }

	
	// 11.Tính dân số trung bình của mỗi bang
	public void calculateAveragePop() {
		Document group = new Document(
			"$group", new Document("_id", "$state")
			.append("avg", new Document("$avg", new Document("$sum", "$pop")))
		);
		
		AggregateIterable<Document> docs = zipsCollection.aggregate(Arrays.asList(group));
		MongoCursor<Document> it = docs.iterator();
		
		while (it.hasNext()) {
			Document doc = it.next();
			System.out.println(doc);
		}
	}

	
	// 12.Tìm những document của bang 'CT' và thành phố 'WATERBURY'
	public void findByStateAndCity(String state, String city) {
		Document filter = new Document("state", state).append("city", city);
		MongoCursor<Document> it = zipsCollection.find(filter).iterator();
    
		while (it.hasNext()) {
			Document doc = it.next();
			System.out.println(doc);
		}
	}

	
	// 13. Bang WA có bao nhiêu city (nếu trùng chỉ tính 1 lần)
	// db.zips.distinct('city', { state: 'WA' }).length;
	public void countCityOfState(String state) {
		Document filter = new Document("state", state);
		List<String> cities = zipsCollection.distinct("city", filter, String.class).into(new ArrayList<>());
		System.out.println(cities.size());
	}
	
	
	// 14. Tính số city của mỗi bang (nếu trùng chỉ tính 1 lần), kết quả giảm dần theo số city
	public void countCityOfEachState() {
		Document group = new Document("$group", new Document("_id", "$state").append("count", new Document("$sum", 1)));
		Document sort = new Document("$sort", new Document("count", -1));
		AggregateIterable<Document> docs = zipsCollection.aggregate(Arrays.asList(group, sort));
		
		MongoCursor<Document> it = docs.iterator();
		while (it.hasNext()) {
			Document doc = it.next();
			System.out.println(doc);
		}
	}

	// 15. Tìm tất cả các bang có tổng dân số trên 1000000
	public void findStateWithTotalPopOver10000000() {
		Document group = new Document("$group", new Document("_id", "$state").append("totalPop", new Document("$sum", "$pop")));
		Document match = new Document("$match", new Document("totalPop", new Document("$gt", 10000000)));
		AggregateIterable<Document> docs = zipsCollection.aggregate(Arrays.asList(group, match));
    
		MongoCursor<Document> it = docs.iterator();
		while (it.hasNext()) {
			Document doc = it.next();
			System.out.println(doc);
		}
	}
	
	// 16. Tìm các document có dân số lớn (nhỏ) nhất
	/*
	db.zips.aggregate( [
	   { $group:
	      {
	        _id: { state: "$state", city: "$city" },
	        pop: { $sum: "$pop" }
	      }
	   },
	   { $sort: { pop: 1 } },
	   { $group:
	      {
	        _id : "$_id.state",
	        biggestCity:  { $last: "$_id.city" },
	        biggestPop:   { $last: "$pop" },
	        smallestCity: { $first: "$_id.city" },
	        smallestPop:  { $first: "$pop" }
	      }
	   },
	
	  // the following $project is optional, and
	  // modifies the output format.
	
	  { $project:
	    { _id: 0,
	      state: "$_id",
	      biggestCity:  { name: "$biggestCity",  pop: "$biggestPop" },
	      smallestCity: { name: "$smallestCity", pop: "$smallestPop" }
	    }
	  }
	] )
	*/
	public void findDocWithLargestAndSmallestPop() {
		Document group = new Document("$group",
				new Document("_id", new Document("state", "$state").append("city", "$city")).append("pop",
						new Document("$sum", "$pop")));
		Document sort = new Document("$sort", new Document("pop", 1));
		Document group2 = new Document("$group",
				new Document("_id", "$_id.state").append("biggestCity", new Document("$last", "$_id.city"))
						.append("biggestPop", new Document("$last", "$pop"))
						.append("smallestCity", new Document("$first", "$_id.city"))
						.append("smallestPop", new Document("$first", "$pop")));
		Document project = new Document("$project",
				new Document("_id", 0).append("state", "$_id")
						.append("biggestCity", new Document("name", "$biggestCity").append("pop", "$biggestPop"))
						.append("smallestCity", new Document("name", "$smallestCity").append("pop", "$smallestPop")));

		AggregateIterable<Document> docs = zipsCollection.aggregate(Arrays.asList(group, sort, group2, project));
		MongoCursor<Document> it = docs.iterator();

		while (it.hasNext()) {
			Document doc = it.next();
			System.out.println(doc);
		}
	}

  
  // 17. Tìm bang có tổng dân số lớn nhất
  /*
	db.zips.aggregate ([
	  {$group:
	      {
	          _id: "$state",
	          pop: {$sum: "$pop"}
	      }
	  },
	  {$group:
	      {
	          _id: null,
	          max: {$max: "$pop"},
	          listState: {$push: '$$ROOT'}
	      }
	  },
	  {$unwind: '$listState'},
	  {$replaceWith: {$mergeObjects: ['$listState', {max: '$max'}]}},
	  {$addFields: {compare: {$cmp: ["$pop", "$max"]}}},
	  {$match: {compare: 0}},
	  {$unset: ["compare", "max"]}
	])
  */
  public void findStateWithLargestTotalPop() {
		Document group = new Document("$group",
				new Document("_id", "$state").append("pop", new Document("$sum", "$pop")));
		Document group2 = new Document("$group", new Document("_id", null).append("max", new Document("$max", "$pop"))
				.append("listState", new Document("$push", "$$ROOT")));
		Document unwind = new Document("$unwind", "$listState");
		Document replaceWith = new Document("$replaceWith",
				new Document("$mergeObjects", Arrays.asList("$listState", new Document("max", "$max"))));
		Document addFields = new Document("$addFields",
				new Document("compare", new Document("$cmp", Arrays.asList("$pop", "$max"))));
		Document match = new Document("$match", new Document("compare", 0));
		Document unset = new Document("$unset", Arrays.asList("compare", "max"));
		AggregateIterable<Document> docs = zipsCollection
				.aggregate(Arrays.asList(group, group2, unwind, replaceWith, addFields, match, unset));
		MongoCursor<Document> it = docs.iterator();
		while (it.hasNext()) {
			Document doc = it.next();
			System.out.println(doc);
		}
  }
  
  // 18. Xuất những document có dân số dưới dân số trung bình của mỗi city
  /*
	db.zips.aggregate([
	  {
	    $group: {
	      _id: null,
	      listCity: {$push: { city: "$city", pop: "$pop" }},
	      avgCityPop: {$avg: "$pop"}
	    }
	  },
	  {
	    $unwind: '$listCity'
	  },
	  {
	    $addFields: {
	      belowAvgPop: { $lt: ["$listCity.pop", "$avgCityPop"] }
	    }
	  },
	  {
	    $match: { belowAvgPop: true }
	  },
	  {
	    $project: { _id: 1, city: "$listCity.city", pop: "$listCity.pop", avgCityPop: 1 }
	  }
	])
  */
  	public void findDocWithPopBelowAverage() {
		Document group = new Document("$group",
				new Document("_id", null)
						.append("listCity", new Document("$push", new Document("city", "$city").append("pop", "$pop")))
						.append("avgCityPop", new Document("$avg", "$pop")));
		Document unwind = new Document("$unwind", "$listCity");
		Document addFields = new Document("$addFields",
				new Document("belowAvgPop", new Document("$lt", Arrays.asList("$listCity.pop", "$avgCityPop"))));
		Document match = new Document("$match", new Document("belowAvgPop", true));
		Document project = new Document("$project", new Document("_id", 1).append("city", "$listCity.city")
				.append("pop", "$listCity.pop").append("avgCityPop", 1));
		AggregateIterable<Document> docs = zipsCollection
				.aggregate(Arrays.asList(group, unwind, addFields, match, project));
		MongoCursor<Document> it = docs.iterator();
		while (it.hasNext()) {
			Document doc = it.next();
			System.out.println(doc);
		}
  	}


  	// 19.Dựa vào tập kết quả câu trên, xóa các documents khi biết city
  public void deleteByCity(String city) {
    Document filter = new Document("city", city);
    zipsCollection.deleteMany(filter);
  }
  
  
  // Based on the above results, delete documents when knowing the city
	public void deleteDocByCity (String city) {
		Document filter = new Document("city", city);
		zipsCollection.deleteMany(filter);
	}
}
