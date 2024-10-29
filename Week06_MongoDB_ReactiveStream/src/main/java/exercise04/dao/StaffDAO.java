package exercise04.dao;


import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertManyResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import com.mongodb.reactivestreams.client.MongoCollection;
import com.mongodb.reactivestreams.client.MongoDatabase;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import org.bson.Document;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import exercise04.entities.Staff;


public class StaffDAO {
	private MongoCollection<Staff> staffCollection;
	
	
	public StaffDAO (MongoDatabase db) {
		CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
		CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));
		
		staffCollection = db.getCollection("staffs", Staff.class).withCodecRegistry(pojoCodecRegistry);
	}
	
	
	// Thêm 1 NV mới
	public boolean insertOneNewStaff (Staff newStaff) {
		AtomicBoolean result = new AtomicBoolean(false);
		CountDownLatch latch = new CountDownLatch(1);
		
		Publisher<InsertOneResult> pub = staffCollection.insertOne(newStaff);
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
	
	
	// Thêm nhiều NV mới
	public boolean insertManyNewStaffs (List<Staff> lstNewStaffs) {
		AtomicBoolean result = new AtomicBoolean(false);
		CountDownLatch latch = new CountDownLatch(1);
		
		Publisher<InsertManyResult> pub = staffCollection.insertMany(lstNewStaffs);
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
	
	
	// Lấy thông tin NV khi biết mã
	public Staff getStaff (long id) {
		AtomicReference<Staff> result = new AtomicReference<Staff>(null);
		CountDownLatch latch = new CountDownLatch(1);
		
		Document doc = new Document("_id", id);
		
		Publisher<Staff> pub = staffCollection.find(doc).first();
		Subscriber<Staff> sub = new Subscriber<Staff>() {

			@Override
			public void onSubscribe(Subscription s) {
				s.request(1);
			}

			@Override
			public void onNext(Staff t) {
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
	
	
	// Update email khi biết mã
	/*
	MONGO QUERY:
		db.staffs.updateOne (
			{"_id": 12},
			{$set: {"email": "abc"}}
		)
	*/
	public boolean updateEmail (long id, String email) {
		AtomicBoolean result = new AtomicBoolean(false);
		CountDownLatch latch = new CountDownLatch(1);
		
		Document filter = new Document("_id", id);
		Document updateEmail = new Document("$set", new Document("email", email));
		
		Publisher<UpdateResult> pub = staffCollection.updateOne(filter, updateEmail);
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
	
	
	// Xóa NV khi biết mã
	/*
	MONGO QUERY:
		db.staffs.deleteOne (
			{"_id": 13}
		)
	*/
	public boolean deleteStaff (long id) {
		AtomicBoolean result = new AtomicBoolean(false);
		CountDownLatch latch = new CountDownLatch(1);
		
		Document doc = new Document("_id", id);
		Publisher<DeleteResult> pub = staffCollection.deleteOne(doc);
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
