package exercise01.dao;


import org.bson.Document;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertManyResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import com.mongodb.reactivestreams.client.FindPublisher;
import com.mongodb.reactivestreams.client.MongoCollection;
import com.mongodb.reactivestreams.client.MongoDatabase;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import exercise01.entities.Customer;

public class CustomerDAO {
	private MongoCollection<Customer> customerCollection;
	
	
	// Constructor
	public CustomerDAO (MongoDatabase db) {
		CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
		CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));
		
		customerCollection = db.getCollection("customers", Customer.class).withCodecRegistry(pojoCodecRegistry);
	}
	
	// Tìm KH khi biết mã
	// KQ trả về là Obj Customer
	public Customer getCustomer (String id) {
		AtomicReference<Customer> result = new AtomicReference<Customer>();
		
		CountDownLatch latch = new CountDownLatch(1);
		
		Publisher<Customer> pub = customerCollection.find(Filters.eq("_id", id)).first();
		Subscriber<Customer> sub = new Subscriber<Customer>() {

			@Override
			public void onSubscribe(Subscription s) {
				s.request(1);
			}

			@Override
			public void onNext(Customer t) {
				result.set(t);
			}

			@Override
			public void onError(Throwable t) {
				
			}

			@Override
			public void onComplete() {
				latch.countDown();
				System.out.println("Successfully found customer");
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
	
	// Lấy DS KH
	// KQ trả về ArrayList chứa các TT KH
	public List<Customer> getAllCustomers() {
		// Tạo AtomicBoolean để đánh dấu kết quả lấy DS KH
		// Nếu true là thành công, còn false là thất bại
		AtomicBoolean result = new AtomicBoolean(false);
		
		// Tạo CountDownLatch để chờ đồng bộ hoàn thành việc thêm KH
		CountDownLatch latch = new CountDownLatch(1);
		
		// Khởi tạo list để chứa các TT KH
		List<Customer> lstCustomer = new ArrayList<Customer>();
		
		// Tạo FindPublisher để tương tác với coll Customer
		FindPublisher<Customer> pub = customerCollection.find();
		
		// Tạo Subscriber để xử lý dữ liệu từ FindPublisher
		Subscriber<Customer> sub = new Subscriber<Customer>() {
			Subscription s;
			
			@Override
			public void onSubscribe(Subscription s) {
				this.s = s;
				s.request(1);
			}

			@Override
			public void onNext(Customer customer) {
				lstCustomer.add(customer);
				this.s.request(1);
			}

			@Override
			public void onError(Throwable throwable) {
				throwable.printStackTrace();
			}

			@Override
			public void onComplete() {
				latch.countDown();
				System.out.println("Completed");
			}
		};
		
		pub.subscribe(sub);
		
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return lstCustomer;
	}
	
	
	// thêm mới 1 KH
	public boolean insertCustomer (Customer customer) {
		// Tạo AtomicBoolean để đánh dấu kết quả thêm sản phẩm
		// Nếu true là thành công, còn false là thất bại
		AtomicBoolean result = new AtomicBoolean(false);
		
		// Tạo CountDownLatch để chờ đồng bộ hoàn thành việc thêm KH
		CountDownLatch latch = new CountDownLatch(1);
		
		// Tạo Publisher để tương tác với nguồn DL coll Customer
		Publisher<InsertOneResult> pub = customerCollection.insertOne(customer);
		
		// Tạo một Subscriber<InsertOneResult> để xử lý kết quả thêm sản phẩm từ Publisher
		Subscriber<InsertOneResult> sub = new Subscriber<InsertOneResult>() {

			// Request 1 record từ coll Customer bằng cách gọi s.request(1).
			@Override
			public void onSubscribe(Subscription s) {
				s.request(1);
			}
			
			// In ra thông báo đã thêm KH thành công cùng với ID của KH đc thêm
			// và cập nhật g.trị của result thành true nếu getInsertedId() trả về một giá trị khác null, ngược lại là false
			@Override
			public void onNext(InsertOneResult t) {
				System.out.println("Customer inserted: " + t.getInsertedId());
				t.getInsertedId();
				
				result.set(t.getInsertedId() != null ? true : false);
			}

			// in ra thông báo lỗi
			@Override
			public void onError(Throwable t) {
				t.printStackTrace();
			}

			// Khi việc thêm KH thành công và in ra thông báo
			// Giải phóng CountDownLatch bằng cách gọi latch.countDown()
			@Override
			public void onComplete() {
				System.out.println("Completed");
				latch.countDown();
			}
		};
		
		// Đký Subscriber với Publisher
		// Bắt đầu quá trình thêm KH
		pub.subscribe(sub);
		
		// KQ trả về result
		return result.get();
	}
	
	
	// thêm mới nhiều KH
	public boolean insertManyNewCustomers (List<Customer> customers) {
		AtomicBoolean result = new AtomicBoolean(false);
		CountDownLatch latch = new CountDownLatch(1);
		
		Publisher<InsertManyResult> pub = customerCollection.insertMany(customers);
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
				latch.countDown();
				System.out.println("Successfully inserted new customers");
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
	
	
	// Update Street Address của KH khi biết id
	/*
	MONGO QUERY:
		db.customers.updateOne (
			{"_id": "RENA1303"},
			{$set: {"address.street": "abcxyz"}}
		)
	*/
	public boolean updateAddress (String id, String street) {
		AtomicBoolean result = new AtomicBoolean(false);
		CountDownLatch latch = new CountDownLatch(1);
		
		Document filter = new Document("_id", id);
		Document update = new Document("$set", new Document("address.street", street));
		
		Publisher<UpdateResult> pub = customerCollection.updateOne(filter, update);
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
	
	
	// Xóa 1 KH khi biết id
	/*
	MONGO QUERY:
		db.customers.deleteOne({"_id": "RENA1303"})
	*/
	public boolean deleteCustomer (String id) {
		AtomicBoolean result = new AtomicBoolean(false);
		CountDownLatch latch = new CountDownLatch(1);
		
		Document doc = new Document("_id", id);
		Publisher<DeleteResult> pub = customerCollection.deleteOne(doc);
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

}
