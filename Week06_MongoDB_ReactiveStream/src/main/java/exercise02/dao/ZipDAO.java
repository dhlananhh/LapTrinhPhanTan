package exercise02.dao;


import exercise02.entities.Zip;

import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.types.ObjectId;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Field;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import com.mongodb.reactivestreams.client.AggregatePublisher;
import com.mongodb.reactivestreams.client.DistinctPublisher;
import com.mongodb.reactivestreams.client.FindPublisher;
import com.mongodb.reactivestreams.client.MongoCollection;
import com.mongodb.reactivestreams.client.MongoDatabase;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.BsonInt32;
import org.bson.Document;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import org.bson.codecs.configuration.CodecRegistry;




public class ZipDAO {
	private MongoCollection<Zip> zipsCollection;
	private MongoCollection<Document> zipsDocColl;
	
	
	public ZipDAO (MongoDatabase db) {
		PojoCodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
		CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));
		
		zipsCollection = db.getCollection("zips", Zip.class).withCodecRegistry(pojoCodecRegistry);
		zipsDocColl = db.getCollection("zips");
	}
	
	
    // Hiển thị danh sách các zipscode 	
	public List<Zip> getZipData() {
		CountDownLatch latch = new CountDownLatch(1);
		List<Zip> lstZips = new ArrayList<Zip>();
		
		FindPublisher<Zip> pub = zipsCollection.find();
		Subscriber<Zip> sub = new Subscriber<Zip>() {
			Subscription s;
			
			@Override
			public void onSubscribe(Subscription s) {
				this.s = s;
				s.request(1);
			}

			@Override
			public void onNext(Zip t) {
				lstZips.add(t);
				this.s.request(1);
			}

			@Override
			public void onError(Throwable t) {
				t.printStackTrace();
			}

			@Override
			public void onComplete() {
				latch.countDown();
				System.out.println("Successfully retrieved all zip data.");
			}
		};
		
		pub.subscribe(sub);
		
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return lstZips;
	}
	
	
	// 1. Hiển thị n documents từ document thứ k.
	public List<Zip> displayDocuments (int k, int n) {
		CountDownLatch latch = new CountDownLatch(1);
		// lstZips chứa danh sách các documents được truy vấn
		List<Zip> lstZips = new ArrayList<Zip>();
		
		// biến count dùng để đếm số lượng các documents đã nhận
		AtomicInteger count = new AtomicInteger(0);

		// skip(k) để bỏ qua k documents đầu tiên
		// limit(n) để giới hạn số documents được trả về.
		FindPublisher<Zip> pub = zipsCollection.find();
		
		Subscriber<Zip> sub = new Subscriber<Zip>() {
			Subscription s;

			@Override
			public void onSubscribe(Subscription s) {
				this.s = s;
				this.s.request(1);
			}

			@Override
			public void onNext(Zip t) {
				// kiểm tra xem đã đủ n documents từ document thứ k trở đi hay chưa
				if (count.getAndIncrement() >= k) {
					lstZips.add(t);
                
					// nếu đã đủ n documents thì dừng việc nhận thêm documents
					if (lstZips.size() >= n) {
						// dừng việc nhận thêm documents bằng cách cancel subscription
						this.s.cancel();
						latch.countDown();
					}
				}
				
				this.s.request(1);
			}

			@Override
			public void onError(Throwable t) {
				t.printStackTrace();
			}

			@Override
			public void onComplete() {
				latch.countDown();
				System.out.println("Successfully displayed " + n + " documents from the " + k + "th document.");
			}
		};

		pub.subscribe(sub);

		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return lstZips;
	}
	
	
	// 2. Chèn thêm 1 document mới
	public boolean insertOneNewZip (Zip zip) {
		AtomicBoolean result = new AtomicBoolean(false);
		CountDownLatch latch = new CountDownLatch(1);
		
		Publisher<InsertOneResult> pub = zipsCollection.insertOne(zip);
		Subscriber<InsertOneResult> sub = new Subscriber<InsertOneResult>() {

			@Override
			public void onSubscribe(Subscription s) {
				s.request(1);
			}

			@Override
			public void onNext(InsertOneResult t) {
				System.out.println("Zip inserted: " + t.getInsertedId());
				result.set(t.getInsertedId() != null ? true : false);
			}

			@Override
			public void onError(Throwable t) {
				t.printStackTrace();
			}

			@Override
			public void onComplete() {
				System.out.println("Inserted one document successfully.");
				latch.countDown();
			}
		};
		
		pub.subscribe(sub);
		
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return result.get();
	}
	
	
	// 3. Cập nhật thông tin của một document khi biết id
	public boolean updatePop (ObjectId id, double pop) {
		AtomicBoolean result = new AtomicBoolean(false);
		CountDownLatch latch = new CountDownLatch(1);
		
		Document filter = new Document("_id", id);
		Document update = new Document("$set", new Document("pop", pop));
		
		Publisher<UpdateResult> pub = zipsCollection.updateOne(filter, update);
		Subscriber<UpdateResult> sub = new Subscriber<UpdateResult>() {

			@Override
			public void onSubscribe(Subscription s) {
                s.request(1);				
			}

			@Override
			public void onNext(UpdateResult t) {
				result.set(t.getModifiedCount() > 0 ? true : false);
			}

			@Override
			public void onError(Throwable t) {
                t.printStackTrace();				
			}

			@Override
			public void onComplete() {
                latch.countDown();
                System.out.println("Updated successfully.");
			}
		};
		
		pub.subscribe(sub);
		
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return result.get();
	}
	
	
	// 4. Tìm các document có city là PALMER
	// Tìm các document khi biết tên City
	public List<Zip> findZipByCityName (String cityName) {
		CountDownLatch latch = new CountDownLatch(1);
		List<Zip> lstZips = new ArrayList<Zip>();

		FindPublisher<Zip> pub = zipsCollection.find(Filters.eq("city", cityName));
		Subscriber<Zip> sub = new Subscriber<Zip>() {
			Subscription s;

			@Override
			public void onSubscribe(Subscription s) {
				this.s = s;
				s.request(1);
			}

			@Override
			public void onNext(Zip t) {
				lstZips.add(t);
				this.s.request(1);
			}

			@Override
			public void onError(Throwable t) {
				t.printStackTrace();
			}

			@Override
			public void onComplete() {
				latch.countDown();
				System.out.println("Successfully find documents by city name.");
			}
		};

		pub.subscribe(sub);

		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return lstZips;
	}
	
	
	// 5. Tìm các document có pop > 100000
	public List<Zip> findZipByPop(int pop) {
		CountDownLatch latch = new CountDownLatch(1);
		List<Zip> lstZips = new ArrayList<Zip>();

		FindPublisher<Zip> pub = zipsCollection.find(Filters.gt("pop", pop));
		Subscriber<Zip> sub = new Subscriber<Zip>() {
			Subscription s;

			@Override
			public void onSubscribe(Subscription s) {
				this.s = s;
				s.request(1);
			}

			@Override
			public void onNext(Zip t) {
				lstZips.add(t);
				this.s.request(1);
			}

			@Override
			public void onError(Throwable t) {
				t.printStackTrace();
			}

			@Override
			public void onComplete() {
				latch.countDown();
				System.out.println("Successfully retrieved all zip data.");
			}
		};

		pub.subscribe(sub);

		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return lstZips;
	}
	
	
	// 6. Tìm dân số của thành phố FISHERS ISLAND
	public Zip findPopByCityName (String cityName) {
		CountDownLatch latch = new CountDownLatch(1);
		AtomicReference<Zip> result = new AtomicReference<Zip>();

		FindPublisher<Zip> pub = zipsCollection.find(Filters.eq("city", cityName));
		Subscriber<Zip> sub = new Subscriber<Zip>() {
			Subscription s;

			@Override
			public void onSubscribe(Subscription s) {
				this.s = s;
				s.request(1);
			}

			@Override
			public void onNext(Zip t) {
				result.set(t);
				this.s.request(1);
			}

			@Override
			public void onError(Throwable t) {
				t.printStackTrace();
			}

			@Override
			public void onComplete() {
				latch.countDown();
				System.out.println("Successfully find population by city name.");
			}
		};

		pub.subscribe(sub);

		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return result.get();
	}
	
	
	// 7. Tìm các thành phố có dân số từ 10 – 50
	public List<Zip> findZipByPopRange(int min, int max) {
		CountDownLatch latch = new CountDownLatch(1);
		List<Zip> lstZips = new ArrayList<Zip>();

		FindPublisher<Zip> pub = zipsCollection.find(Filters.and(Filters.gte("pop", min), Filters.lte("pop", max)));
		Subscriber<Zip> sub = new Subscriber<Zip>() {
			Subscription s;

			@Override
			public void onSubscribe(Subscription s) {
				this.s = s;
				s.request(1);
			}

			@Override
			public void onNext(Zip t) {
				lstZips.add(t);
				this.s.request(1);
			}

			@Override
			public void onError(Throwable t) {
				t.printStackTrace();
			}

			@Override
			public void onComplete() {
				latch.countDown();
				System.out.println("Successfully find documents with population from 10 to 50.");
			}
		};

		pub.subscribe(sub);

		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return lstZips;
	}
	
	
	// 8. Tìm tất cả các thành phố của bang MA có dân số trên 500
	public List<Zip> findZipByStateAndPop(String state, int pop) {
		CountDownLatch latch = new CountDownLatch(1);
		List<Zip> lstZips = new ArrayList<Zip>();

		FindPublisher<Zip> pub = zipsCollection.find(Filters.and(Filters.eq("state", state), Filters.gt("pop", pop)));
		Subscriber<Zip> sub = new Subscriber<Zip>() {
			Subscription s;

			@Override
			public void onSubscribe(Subscription s) {
				this.s = s;
				s.request(1);
			}

			@Override
			public void onNext(Zip t) {
				lstZips.add(t);
				this.s.request(1);
			}

			@Override
			public void onError(Throwable t) {
				t.printStackTrace();
			}

			@Override
			public void onComplete() {
				latch.countDown();
				System.out.println("Successfully find documents with state MA and population > 500.");
			}
		};

		pub.subscribe(sub);

		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return lstZips;
	}
	
	
	// 9. Tìm tất cả các bang (không trùng) trong collection
	
/*	
	db.zipcodes.aggregate (
    	{$group: {_id: "$state"}}
	)
*/
	
	public List<Document> findDistinctStates() {
		CountDownLatch latch = new CountDownLatch(1);
		List<Document> lstZips = new ArrayList<Document>();
		
		AggregatePublisher<Document> aggregate = zipsDocColl.aggregate(Arrays.asList(Aggregates.group("$state")));
		Subscriber<Document> sub = new Subscriber<Document>() {
			Subscription s;

			@Override
			public void onSubscribe(Subscription s) {
				this.s = s;
				s.request(1);
			}

			@Override
			public void onNext(Document t) {
				lstZips.add(t);
				this.s.request(1);
			}

			@Override
			public void onError(Throwable t) {
				t.printStackTrace();
			}

			@Override
			public void onComplete() {
				latch.countDown();
				System.out.println("Successfully find all states (no duplicates).");
			}
		};
		
		aggregate.subscribe(sub);
		
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return lstZips;
	}
	
	
	// 10. Tìm tất cả các bang mà có chứa ít nhất 1 thành phố có dân số trên 100000
	public List<Document> findStatesWithPopOver(int pop) {
		CountDownLatch latch = new CountDownLatch(1);
		List<Document> lstZips = new ArrayList<Document>();

		AggregatePublisher<Document> pub = zipsDocColl
				.aggregate(Arrays.asList(Aggregates.match(Filters.gt("pop", pop)), Aggregates.group("$state")));
		
		Subscriber<Document> sub = new Subscriber<Document>() {
			Subscription s;

			@Override
			public void onSubscribe(Subscription s) {
				this.s = s;
				s.request(1);
			}

			@Override
			public void onNext(Document t) {
				lstZips.add(t);
				this.s.request(1);
			}

			@Override
			public void onError(Throwable t) {
				t.printStackTrace();
			}

			@Override
			public void onComplete() {
				latch.countDown();
				System.out.println("Successfully find all states that have at least 1 city with population > 100000.");
			}
		};

		pub.subscribe(sub);

		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return lstZips;
	}
	
	
	// 11. Tính dân số trung bình của mỗi bang
/*	
db.zipcodes.aggregate ([
    {$group: {_id: {state: "$state", city: "$city"}, pop: {$sum: "$pop"}}},
    {$group: {_id: "$_id.state", totalPop: {$sum: "$pop"}}}
])
*/	
	
	public List<Document> avgPopByState() {
		CountDownLatch latch = new CountDownLatch(1);
		List<Document> lstZips = new ArrayList<Document>();
	
		AggregatePublisher<Document> aggregate = zipsDocColl.aggregate(
				Arrays.asList(Aggregates.group(new Document("state", "$state"), Accumulators.sum("pop", "$pop")),
						Aggregates.group("$_id.state", Accumulators.sum("totalPop", "$pop"))));
		
		Subscriber<Document> sub = new Subscriber<Document>() {
            Subscription s;
            
			@Override
			public void onSubscribe(Subscription s) {
                this.s = s;
                s.request(1);
			}

			@Override
			public void onNext(Document t) {
				lstZips.add(t);
				this.s.request(1);
			}

			@Override
			public void onError(Throwable t) {
                t.printStackTrace();				
			}

			@Override
			public void onComplete() {
				latch.countDown();
				System.out.println("Successfully calculate average population by state.");
			}
		};
		
		aggregate.subscribe(sub);
		
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	
		return lstZips;
	}
	
	
	// 12. Tìm những document của bang 'CT' và thành phố 'WATERBURY'
	public List<Zip> findZipByStateAndCity(String state, String city) {
		CountDownLatch latch = new CountDownLatch(1);
		List<Zip> lstZips = new ArrayList<Zip>();

		FindPublisher<Zip> pub = zipsCollection.find(Filters.and(Filters.eq("state", state), Filters.eq("city", city)));
		Subscriber<Zip> sub = new Subscriber<Zip>() {
			Subscription s;

			@Override
			public void onSubscribe(Subscription s) {
				this.s = s;
				s.request(1);
			}

			@Override
			public void onNext(Zip t) {
				lstZips.add(t);
				this.s.request(1);
			}

			@Override
			public void onError(Throwable t) {
				t.printStackTrace();
			}

			@Override
			public void onComplete() {
				latch.countDown();
				System.out.println("Successfully find documents by state and city.");
			}
		};

		pub.subscribe(sub);

		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return lstZips;
	}
	
	
	// 13. Bang WA có bao nhiêu city (nếu trùng chỉ tính 1 lần)
	
/*	
	db.zipcodes.aggregate ([
	    {$match: {state: "WA"}},
	    {$group: {_id: "$city"}},
	    {$group: {_id: null, numOfCity: {$sum: 1}}},
	    {$project: {_id: 0, soTP: 1}}
	])
*/
	
	public List<Document> countCityInState(String state) {
        CountDownLatch latch = new CountDownLatch(1);
        List<Document> lstZips = new ArrayList<Document>();

        AggregatePublisher<Document> pub = zipsDocColl.aggregate(
                Arrays.asList(Aggregates.match(Filters.eq("state", state)), Aggregates.group("$city"),
                        Aggregates.group(null, Accumulators.sum("numOfCity", 1)),
                        Aggregates.project(new Document("_id", 0).append("numOfCity", 1))));
        
        Subscriber<Document> sub = new Subscriber<Document>() {
            Subscription s;

            @Override
            public void onSubscribe(Subscription s) {
                this.s = s;
                s.request(1);
            }

            @Override
            public void onNext(Document t) {
                lstZips.add(t);
                this.s.request(1);
            }

            @Override
            public void onError(Throwable t) {
                t.printStackTrace();
            }

            @Override
            public void onComplete() {
                latch.countDown();
                System.out.println("Successfully count city in state.");
            }
        };

        pub.subscribe(sub);

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return lstZips;
    }
	
	
	// 14. Tính số city của mỗi bang (nếu trùng chỉ tính 1 lần), kết quả giảm dần theo số city
	
/*	
	db.zipcodes.aggregate ([
	    {$group: {_id: {state: "$state", city: "$city"}}},
	    {$group: {_id: "$_id.state", numOfCity: {$sum: 1}}},
	    {$project: {_id: 0, state: "$_id", soTP: 1}},
	    {$sort: {numOfCity: -1}}
	])
	
*/	
	
	public List<Document> countCityInStateDesc() {
        CountDownLatch latch = new CountDownLatch(1);
        List<Document> lstZips = new ArrayList<Document>();

        AggregatePublisher<Document> pub = zipsDocColl.aggregate(
                Arrays.asList(Aggregates.group(new Document("state", "$state").append("city", "$city")),
                        Aggregates.group("$_id.state", Accumulators.sum("numOfCity", 1)),
                        Aggregates.project(new Document("_id", 0).append("state", "$_id").append("numOfCity", 1)),
                                Aggregates.sort(new Document("numOfCity", -1))));

        Subscriber<Document> sub = new Subscriber<Document>() {
            Subscription s;

            @Override
            public void onSubscribe(Subscription s) {
                this.s = s;
                s.request(1);
            }

            @Override
            public void onNext(Document t) {
                lstZips.add(t);
                this.s.request(1);
            }

            @Override
            public void onError(Throwable t) {
                t.printStackTrace();
            }

            @Override
            public void onComplete() {
                latch.countDown();
                System.out.println("Successfully count cities by state (no duplicates), sort by city count descending.");
            }
        };

        pub.subscribe(sub);

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return lstZips;
    }
	
	
	// 15. Tìm tất cả các bang có tổng dân số trên 10000000
	
/*
	db.zipcodes.aggregate ([
	    {$group: {_id: "$state", totalPop: {$sum: "$pop"}}},
	    {$match: {totalPop: {$gte: 10*1000*1000}}}
	])
*/
	
	public List<Document> findStatesWithTotalPopOver(int pop) {
		CountDownLatch latch = new CountDownLatch(1);
		List<Document> lstZips = new ArrayList<Document>();
	
		AggregatePublisher<Document> pub = zipsDocColl
				.aggregate(Arrays.asList(Aggregates.group("$state", Accumulators.sum("totalPop", "$pop")),
						Aggregates.match(Filters.gte("totalPop", pop))));
	
		Subscriber<Document> sub = new Subscriber<Document>() {
			Subscription s;
	
			@Override
			public void onSubscribe(Subscription s) {
				this.s = s;
				s.request(1);
			}
	
			@Override
			public void onNext(Document t) {
				lstZips.add(t);
				this.s.request(1);
			}
	
			@Override
			public void onError(Throwable t) {
				t.printStackTrace();
			}
	
			@Override
			public void onComplete() {
				latch.countDown();
				System.out.println("Successfully find states with total population over 10000000.");
			}
		};
	
		pub.subscribe(sub);
	
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	
		return lstZips;
	}
	
	
	// 16. Tìm các document có dân số lớn (nhỏ) nhất

	// Lớn nhất
/*
 	db.zipcodes.aggregate ([ 
 		{$match: 
 			{pop: {$gt: 0}}
 		}, 
 		{$sort: {pop: -1}},
 		{$limit: 1} 
 	])
 */

	public List<Zip> findMaxPop() {
		CountDownLatch latch = new CountDownLatch(1);
		List<Zip> lstZips = new ArrayList<Zip>();
	
		AggregatePublisher<Zip> pub = zipsCollection.aggregate(Arrays.asList(Aggregates.match(Filters.gt("pop", 0)),
				Aggregates.sort(new Document("pop", -1)), Aggregates.limit(1)));
	
		Subscriber<Zip> sub = new Subscriber<Zip>() {
			Subscription s;
	
			@Override
			public void onSubscribe(Subscription s) {
				this.s = s;
				s.request(1);
			}
	
			@Override
			public void onNext(Zip t) {
				lstZips.add(t);
				this.s.request(1);
			}
	
			@Override
			public void onError(Throwable t) {
				t.printStackTrace();
			}
	
			@Override
			public void onComplete() {
				latch.countDown();
				System.out.println("Successfully find max population.");
			}
		};
	
		pub.subscribe(sub);
	
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	
		return lstZips;
	}

// Nhỏ nhất
/*	
	db.zipcodes.aggregate ([
	    {$match: {pop: {$gt: 0}}},
	    {$sort: {pop: 1}},
	    {$limit: 1}
	])
*/	
	
	public List<Zip> findMinPop() {
		CountDownLatch latch = new CountDownLatch(1);
		List<Zip> lstZips = new ArrayList<Zip>();
	
		AggregatePublisher<Zip> pub = zipsCollection.aggregate(Arrays.asList(Aggregates.match(Filters.gt("pop", 0)),
				Aggregates.sort(new Document("pop", 1)), Aggregates.limit(1)));
	
		Subscriber<Zip> sub = new Subscriber<Zip>() {
			Subscription s;
	
			@Override
			public void onSubscribe(Subscription s) {
				this.s = s;
				s.request(1);
			}
	
			@Override
			public void onNext(Zip t) {
				lstZips.add(t);
				this.s.request(1);
			}
	
			@Override
			public void onError(Throwable t) {
				t.printStackTrace();
			}
	
			@Override
			public void onComplete() {
				latch.countDown();
				System.out.println("Successfully find min population.");
			}
		};
	
		pub.subscribe(sub);
	
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	
		return lstZips;
	}


	// 17. Tìm bang có tổng dân số lớn nhất
	
/*	
	db.zipcodes.aggregate ([
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
	
	
public Document findStateWithLargestPop() {
        CountDownLatch latch = new CountDownLatch(1);
//        List<Document> lstZips = new ArrayList<Document>();
        AtomicReference<Document> result = new AtomicReference<Document>();
    
        AggregatePublisher<Document> pub = zipsDocColl.aggregate(Arrays.asList(Aggregates.group("$state", Accumulators.sum("pop", "$pop")),
                Aggregates.group(null, Accumulators.max("max", "$pop"), Accumulators.push("listState", "$$ROOT")),
                Aggregates.unwind("$listState"),
                Aggregates.replaceWith(new Document("$mergeObjects", Arrays.asList("$listState", new Document("max", "$max")))),
                Aggregates.addFields((List<Field<?>>) new Document("compare", new Document("$cmp", Arrays.asList("$pop", "$max")))),
                Aggregates.match(Filters.eq("compare", 0)),
                Aggregates.unset(Arrays.asList("compare", "max"))));
    
        Subscriber<Document> sub = new Subscriber<Document>() {
            Subscription s;
    
            @Override
            public void onSubscribe(Subscription s) {
                this.s = s;
                s.request(1);
            }
    
            @Override
            public void onNext(Document t) {
                this.s.request(1);
            }
    
            @Override
            public void onError(Throwable t) {
                t.printStackTrace();
            }
    
            @Override
            public void onComplete() {
                latch.countDown();
                System.out.println("Successfully find state with max population.");
            }
        };
    
        pub.subscribe(sub);
    
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    
        return result.get();
    }

// 18. Tìm bang có tổng dân số nhỏ nhất

// 18. Tìm bang có tổng dân số nhỏ nhất

// 18. Tìm bang có tổng dân số nhỏ nhất
	
	
	
	
	
	
	
	
	
	
	
}
