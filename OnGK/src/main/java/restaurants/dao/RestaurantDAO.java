package restaurants.dao;

import restaurants.entities.Restaurant;
import restaurants.entities.Address;

import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.model.TextSearchOptions;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertManyResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import com.mongodb.reactivestreams.client.AggregatePublisher;
import com.mongodb.reactivestreams.client.FindPublisher;
import com.mongodb.reactivestreams.client.MongoCollection;
import com.mongodb.reactivestreams.client.MongoDatabase;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;


public class RestaurantDAO {
	private MongoCollection<Restaurant> restaurantCollection;
	
	
	public RestaurantDAO (MongoDatabase db) {
		PojoCodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
		CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));
		
		restaurantCollection = db.getCollection("restaurants", Restaurant.class).withCodecRegistry(pojoCodecRegistry);
	}
	
	
	// Thêm 1 nhà hàng mới
	public boolean insertOneNewRestaurant (Restaurant restaurant) {
		AtomicBoolean result = new AtomicBoolean(false);
		CountDownLatch latch = new CountDownLatch(1);
		
		Publisher<InsertOneResult> pub = restaurantCollection.insertOne(restaurant);
		Subscriber<InsertOneResult> sub = new Subscriber<InsertOneResult>() {

			@Override
			public void onSubscribe(Subscription s) {
				s.request(1);
			}

			@Override
			public void onNext(InsertOneResult t) {
				result.set(t.getInsertedId() != null ? true : false);
			}

			@Override
			public void onError(Throwable t) {
				t.printStackTrace();
			}

			@Override
			public void onComplete() {
				System.out.println("Success");
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
	
	
	// Thêm nhiều nhà hàng mới
	public boolean insertManyNewRestaurants (List<Restaurant> listNewRestaurants) {
		AtomicBoolean result = new AtomicBoolean(false);
		CountDownLatch latch = new CountDownLatch(1);
		
		Publisher<InsertManyResult> pub = restaurantCollection.insertMany(listNewRestaurants);
		Subscriber<InsertManyResult> sub = new Subscriber<InsertManyResult>() {
			
			@Override
			public void onSubscribe(Subscription s) {
				s.request(1);
			}

			@Override
			public void onNext(InsertManyResult t) {
				result.set(t.getInsertedIds() != null ? true : false);
			}

			@Override
			public void onError(Throwable t) {
				t.printStackTrace();
			}

			@Override
			public void onComplete() {
				System.out.println("Success");
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
	
	
	
	/*
	Dùng text search để tìm kiếm các nhà hàng dựa theo 
	borough (khu vực mà nhà hàng tọa lạc), cuisine (loại ẩm thực mà nhà hàng phục vụ)
		+ getRestaurants (borough: String, cuisine: String): List<getRestaurant>
	*/
	
	/*
	MONGO QUERY:
		db.restaurants.find (
			{"borough": "Brooklyn", "cuisine": "Bakery"}
		)
	*/
	
	
	public Restaurant getOneRestaurant (String restaurantId) {
		AtomicReference<Restaurant> result = new AtomicReference<Restaurant>();
		CountDownLatch latch = new CountDownLatch(1);
		
//		Document filter = new Document("restaurant_id", restaurantId);
		
//		Publisher<Restaurant> pub = restaurantCollection.find(filter).first();
		Publisher<Restaurant> pub = restaurantCollection.find(Filters.eq("restaurant_id", restaurantId)).first();
		Subscriber<Restaurant> sub = new Subscriber<Restaurant>() {

			@Override
			public void onSubscribe(Subscription s) {
				s.request(1);
			}

			@Override
			public void onNext(Restaurant t) {
				result.set(t);
			}

			@Override
			public void onError(Throwable t) {
				t.printStackTrace();
			}

			@Override
			public void onComplete() {
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
	
	
	public List<Restaurant> getRestaurants (String borough, String cuisine) {
		List<Restaurant> lstRestaurants = new ArrayList<Restaurant>();
		CountDownLatch latch = new CountDownLatch(1);
		
		Document filter = new Document()
			.append("borough", borough)
			.append("cuisine", cuisine);
		
		FindPublisher<Restaurant> pub = restaurantCollection.find(filter);
		Subscriber<Restaurant> sub = new Subscriber<Restaurant>() {
			Subscription s;
			
			@Override
			public void onSubscribe(Subscription s) {
				this.s = s;
				s.request(1);
			}

			@Override
			public void onNext(Restaurant t) {
				lstRestaurants.add(t);
				this.s.request(1);
			}

			@Override
			public void onError(Throwable t) {
				t.printStackTrace();
			}

			@Override
			public void onComplete() {
				System.out.println("Success");
				latch.countDown();
			}
		};
		
		pub.subscribe(sub);
		
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return lstRestaurants;
	}
	
	
	public List<Restaurant> getRestaurantsByBoroughAndCuisine (String textSearch) {
		Bson keys = Indexes.compoundIndex(Indexes.ascending("borough"), Indexes.ascending("cuisine"));
		restaurantCollection.createIndex(keys);
		
		List<Restaurant> lstRestaurants = new ArrayList<Restaurant>();
		CountDownLatch latch = new CountDownLatch(1);
		
		Publisher<Restaurant> pub = restaurantCollection.find(Filters.text(textSearch, new TextSearchOptions().caseSensitive(false)));
		Subscriber<Restaurant> sub = new Subscriber<Restaurant>() {
			Subscription s;

			@Override
			public void onSubscribe(Subscription s) {
				this.s = s;
				s.request(1);
			}

			@Override
			public void onNext(Restaurant t) {
				lstRestaurants.add(t);
				this.s.request(1);
			}

			@Override
			public void onError(Throwable t) {
				t.printStackTrace();
			}

			@Override
			public void onComplete() {
				System.out.println("Success");
				latch.countDown();
			}
		};
		
		pub.subscribe(sub);
		
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return lstRestaurants;
	}
	
	
	public List<Restaurant> getRestaurantsByBoroughAndCuisine (String borough, String cuisine) {
        List<Restaurant> lstRestaurants = new ArrayList<>();
        CountDownLatch latch = new CountDownLatch(1);

        // Tạo index cho trường borough và cuisine
        restaurantCollection.createIndex(new Document("borough", 1).append("cuisine", 1))
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(1);
                    }

                    @Override
                    public void onNext(String indexName) {
                        // Index được tạo thành công
                    }

                    @Override
                    public void onError(Throwable t) {
                        t.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        // Hoàn thành tạo index
                        // Tiếp tục thực hiện tìm kiếm
                    	performSearch(borough, cuisine, lstRestaurants, latch);
                    }
                });

        

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return lstRestaurants;
    }
	
	
	// Thực hiện tìm kiếm
    private void performSearch(String borough, String cuisine, List<Restaurant> lstRestaurants, CountDownLatch latch) {
        Document filter = new Document()
                .append("borough", borough)
                .append("cuisine", cuisine);

        FindPublisher<Restaurant> pub = restaurantCollection.find(filter);
        Subscriber<Restaurant> sub = new Subscriber<Restaurant>() {
            Subscription s;

            @Override
            public void onSubscribe(Subscription s) {
                this.s = s;
                s.request(1);
            }

            @Override
            public void onNext(Restaurant t) {
                lstRestaurants.add(t);
                this.s.request(1);
            }

            @Override
            public void onError(Throwable t) {
                t.printStackTrace();
            }

            @Override
            public void onComplete() {
                System.out.println("Success");
                latch.countDown();
            }
        };

        pub.subscribe(sub);
    }
	
	
	public boolean deleteRestaurant (String restaurantId) {
		AtomicBoolean result = new AtomicBoolean(false);
		CountDownLatch latch = new CountDownLatch(1);
		
		Publisher<DeleteResult> pub = restaurantCollection.deleteOne(new Document("restaurant_id", restaurantId));
		Subscriber<DeleteResult> sub = new Subscriber<DeleteResult>() {

			@Override
			public void onSubscribe(Subscription s) {
				s.request(1);
			}

			@Override
			public void onNext(DeleteResult t) {
				result.set(t.getDeletedCount() > 0 ? true : false);
			}

			@Override
			public void onError(Throwable t) {
				t.printStackTrace();
			}

			@Override
			public void onComplete() {
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
	
	
	/*
	Cập nhật thông tin building của Address 
	khi biết restaurantId và name của restaurant  
		+ updateTheBuildingOfAddress (restaurantId: String, name: String)
	*/
	
	/*
	MONGO QUERY:
		db.restaurants.updateOne (
			{"restaurant_id": "40356018", "name": "Riviera Caterer"},
			{$set: {"address.building": "1234"}}
		)
	*/
	
	public boolean updateTheBuildingOfAddress (String restaurantId, String name, String buildingUpdated) {
		AtomicBoolean result = new AtomicBoolean(false);
		CountDownLatch latch = new CountDownLatch(1);
		
		Document filter = new Document()
			.append("restaurant_id", restaurantId)
			.append("name", name);
		Document update = new Document("$set", new Document("address.building", buildingUpdated));
		
		Publisher<UpdateResult> pub = restaurantCollection.updateOne(filter, update);
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
	
	
	public boolean updateAddress (String restaurantId, String name, Address addressUpdated) {
		AtomicBoolean result = new AtomicBoolean(false);
		CountDownLatch latch = new CountDownLatch(1);
		
		Document filter = new Document().append("restaurant_id", restaurantId).append("name", name);
		Document update = new Document("$set", new Document("address", addressUpdated));
		
		Publisher<UpdateResult> pub = restaurantCollection.updateOne(filter, update);
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
	
	
	/*
	Tính điểm trung bình (score) của từng nhà hàng,
	sắp xếp giảm dần theo điểm trung bình.
	
	Lấy 5 nhà hàng có điểm trung bình cao nhất.

	Output: [{_id: '40393488', avgScore: 38.6}]
	
		+ getAvgScoreOfRestaurantByGrade()
	*/
	
	/*
	db.restaurants.aggregate([
	    {$unwind:"$grades"},
	    {
	        $group: {
	            _id: "$restaurant_id",
	            avgScore: { $avg: "$grades.score" }
	        }
	    },
	    {
	        $sort: { avgScore: -1 }
	    },
	    {
	        $limit: 5
	    }
	])	
	*/
	
	

	public List<Restaurant> getAvgScoreOfRestaurantByGrade() {
		List<Restaurant> lstRes = new ArrayList<Restaurant>();
		CountDownLatch latch = new CountDownLatch(1);
		
		AggregatePublisher<Restaurant> pub = restaurantCollection.aggregate(Arrays.asList(
			Aggregates.unwind("$grades"),
			Aggregates.group(null, Accumulators.avg("avgScore", "$grades.score")),
			Aggregates.sort(new Document("avgScore", -1)),
			Aggregates.limit(5)
		));
		
		Subscriber<Restaurant> sub = new Subscriber<Restaurant>() {
			Subscription s;
			
			@Override
			public void onSubscribe(Subscription s) {
				this.s = s;
				this.s.request(1);
			}

			@Override
			public void onNext(Restaurant t) {
				lstRes.add(t);
				this.s.request(1);
			}

			@Override
			public void onError(Throwable t) {
				t.printStackTrace();
			}

			@Override
			public void onComplete() {
                latch.countDown();				
			}
		};
		
		pub.subscribe(sub);
		
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return lstRes;
	}
}
