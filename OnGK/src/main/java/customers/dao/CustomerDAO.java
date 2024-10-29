package customers.dao;


import customers.entities.Customer;



import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import com.mongodb.reactivestreams.client.FindPublisher;
import com.mongodb.reactivestreams.client.MongoCollection;
import com.mongodb.reactivestreams.client.MongoDatabase;


public class CustomerDAO {
	private MongoCollection<Customer> customerCollection;
	
	
	public CustomerDAO (MongoDatabase db) {
		PojoCodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
		CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));
		
		customerCollection = db.getCollection("customers", Customer.class).withCodecRegistry(pojoCodecRegistry);
	}
	
	
	// Dùng text search để tìm kiếm tất cả thông tin Customers theo user_name và package
	public List<Customer> getCustomersByUserNameAndPackage (String kwUserName, String kwPackages) {
		List<Customer> lstCustomers = new ArrayList<Customer>();
		CountDownLatch latch = new CountDownLatch(1);
		
		Document textSearch = new Document()
			.append("user_name", kwUserName)
			.append("package", kwPackages);
		
		FindPublisher<Customer> pub = customerCollection.find(textSearch);
		Subscriber<Customer> sub = new Subscriber<Customer>() {
			Subscription s;
			
			@Override
			public void onSubscribe(Subscription s) {
				this.s = s;
				this.s.request(1);
			}

			@Override
			public void onNext(Customer t) {
				lstCustomers.add(t);
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
		
		return lstCustomers;
	}
}
