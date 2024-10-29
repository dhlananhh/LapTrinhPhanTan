package exercise05.dao;


import exercise05.entities.Movies;

import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.types.ObjectId;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Field;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
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


public class MovieDAO {
	private MongoCollection<Movies> moviesCollection;
	private MongoCollection<Document> moviesDocColl;
	
	
	public MovieDAO (MongoDatabase db) {
		PojoCodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
		CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));
		
		moviesCollection = db.getCollection("movies", Movies.class).withCodecRegistry(pojoCodecRegistry);
		moviesDocColl = db.getCollection("movies");
	}
	
	
	// 1. Liệt kê danh sách các đạo diễn có tham gia từ 30 bộ phim trở lên. 
	// Thông tin bao gồm: Tên đạo diễn (director) và số bộ phim.
	// Output dạng: [ { director: 'Takashi Miike', 'number of movies': 34 }, …]
/*
	db.movies.aggregate([
	    {
	        $unwind: "$directors"
	    },
	    {
	        $group: {
	            _id: "$directors",
	            count: { $sum: 1 }
	        }
	    },
	    {
	        $match: {
	            count: { $gte: 30 }
	        }
	    },
	    {
	        $project: {
	            _id: 0,
	            director: "$_id",
	            count: 1
	        }
	    }
	])
*/
	
	public List<Document> findDirectorsOver30Movies() {
		List<Document> lstDirectors = new ArrayList<Document>();
		CountDownLatch latch = new CountDownLatch(1);
		
		// Using AggregatePublisher to list all directors over 30 movies. The information includes director's name and number of movies.
		AggregatePublisher<Document> aggregatePublisher = moviesDocColl.aggregate(Arrays.asList(
				Aggregates.unwind("$directors"), Aggregates.group("$directors", Accumulators.sum("count", 1)),
				Aggregates.match(Filters.gte("count", 30)),
				Aggregates.project(Projections.fields(Projections.excludeId(), Projections.computed("director", "$_id"),
						Projections.include("count")))));
		
		
		Subscriber<Document> sub = new Subscriber<Document>() {
            Subscription s;
            
			@Override
			public void onSubscribe(Subscription s) {
                this.s = s;
                s.request(1);
			}

			@Override
			public void onNext(Document t) {
				lstDirectors.add(t);
				this.s.request(1);
			}

			@Override
			public void onError(Throwable t) {
                t.printStackTrace();				
			}

			@Override
			public void onComplete() {
				latch.countDown();
				System.out.println("Successfully list of directors over 30 movies.");
			}
		};
		
		aggregatePublisher.subscribe(sub);
		
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return lstDirectors;
	}
	
	
	// 2. Tìm tất cả các đạo diễn có tham gia đạo diễn nhiều bộ phim nhất
	// Output là: [ { director: 'Woody Allen' } ]
/*	
	db.movies.aggregate([
	    {
	        $unwind: "$directors"
	    },
	    {
	        $group: {
	            _id: "$directors",
	            count: { $sum: 1 }
	        }
	    },
	    {
	        $sort: { count: -1 }
	    },
	    {
	        $limit: 1
	    },
	    {
	        $project: {
	            _id: 0,
	            director: "$_id",
	            // count: 1
	        }
	    }
	])
*/	
	
	public List<Document> findDirectorsWithMostMovies() {
		List<Document> lstDirectors = new ArrayList<Document>();
		CountDownLatch latch = new CountDownLatch(1);
	
		// Using AggregatePublisher to list all directors with the most movies.
		// The information includes director's name.
		AggregatePublisher<Document> aggregatePublisher = moviesDocColl.aggregate(Arrays.asList(
				Aggregates.unwind("$directors"), Aggregates.group("$directors", Accumulators.sum("count", 1)),
				Aggregates.sort(new Document("count", -1)), Aggregates.limit(1),
				Aggregates.project(Projections.fields(Projections.excludeId(), Projections.computed("director", "$_id")))));
	
		Subscriber<Document> sub = new Subscriber<Document>() {
			Subscription s;
	
			@Override
			public void onSubscribe(Subscription s) {
				this.s = s;
				s.request(1);
			}
	
			@Override
			public void onNext(Document t) {
				lstDirectors.add(t);
				this.s.request(1);
			}
	
			@Override
			public void onError(Throwable t) {
				t.printStackTrace();
			}
	
			@Override
			public void onComplete() {
				latch.countDown();
				System.out.println("Successfully list of directors with the most movies directed.");
			}
		};
	
		aggregatePublisher.subscribe(sub);
	
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	
		return lstDirectors;
	}
	
	
	// 3. Liệt kê tựa phim (title) theo từng đạo diễn.
	// Thông tin bao gồm: tên đạo diễn (director) và danh sách tựa phim.
	// Output dạng: [ { movies: [ 'Lost in America', 'Defending Your Life', 'Mother' ], director: 'Albert Brooks'}, …]
	
/*	
	db.movies.aggregate([
	    {
	        $unwind: "$directors"
	    },
	    {
	        $group: {
	            _id: "$directors",
	            movies: { $push: "$title" }
	        }
	    },
	    {
	        $project: {
	            _id: 0,
	            director: "$_id",
	            movies: 1
	        }
	    }
	])
*/
	
	public List<Document> findMoviesByDirector() {
		List<Document> lstMoviesByDirector = new ArrayList<Document>();
		CountDownLatch latch = new CountDownLatch(1);
	
		// Using AggregatePublisher to list all movies by director.
		// The information includes director's name and list of movies.
		AggregatePublisher<Document> aggregatePublisher = moviesDocColl.aggregate(Arrays.asList(
				Aggregates.unwind("$directors"), Aggregates.group("$directors", Accumulators.push("movies", "$title")),
				Aggregates.project(Projections.fields(Projections.excludeId(), Projections.computed("director", "$_id"),
						Projections.include("movies")))));
	
		Subscriber<Document> sub = new Subscriber<Document>() {
			Subscription s;
	
			@Override
			public void onSubscribe(Subscription s) {
				this.s = s;
				s.request(1);
			}
	
			@Override
			public void onNext(Document t) {
				lstMoviesByDirector.add(t);
				this.s.request(1);
			}
	
			@Override
			public void onError(Throwable t) {
				t.printStackTrace();
			}
	
			@Override
			public void onComplete() {
				latch.countDown();
				System.out.println("Successfully list of movies by director.");
			}
		};
	
		aggregatePublisher.subscribe(sub);
	
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	
		return lstMoviesByDirector;
	}
	
	
	// 4. Thống kê số bộ phim đã phát hành theo từng năm, sắp xếp giảm dần theo năm.
	// Năm dựa vào field released
	// Output dạng:[ { 'number of movies': 10, year: 2016 }, …]
	
/*	
	db.movies.aggregate([
	    {
	        $group: {
	            _id: "$year",
	            count: { $sum: 1 }
	        }
	    },
	    {
	        $sort: { _id: -1 }
	    }
	])	
*/
	
	public List<Document> countMoviesByYear() {
        List<Document> lstMoviesByYear = new ArrayList<Document>();
        CountDownLatch latch = new CountDownLatch(1);
    
        // Using AggregatePublisher to count movies by year.
        // The information includes number of movies and year.
        AggregatePublisher<Document> aggregatePublisher = moviesDocColl.aggregate(Arrays.asList(
                Aggregates.group("$year", Accumulators.sum("count", 1)), Aggregates.sort(new Document("_id", -1))));
    
        Subscriber<Document> sub = new Subscriber<Document>() {
            Subscription s;
    
            @Override
            public void onSubscribe(Subscription s) {
                this.s = s;
                s.request(1);
            }
    
            @Override
            public void onNext(Document t) {
                lstMoviesByYear.add(t);
                this.s.request(1);
            }
    
            @Override
            public void onError(Throwable t) {
                t.printStackTrace();
            }
    
            @Override
            public void onComplete() {
                latch.countDown();
                System.out.println("Successfully count movies by year.");
            }
        };
    
        aggregatePublisher.subscribe(sub);
    
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    
        return lstMoviesByYear;
    }
	
	
	// 5. Tìm năm phát hành nhiều bộ phim nhất.
	// Năm dựa vào field released
	// Output là: [ { year: 2014 } ]
	
/*	
	db.movies.aggregate([
	    {
	        $group: {
	            _id: "$year",
	            count: { $sum: 1 }
	        }
	    },
	    {
	        $sort: { count: -1 }
	    },
	    {
	        $limit: 1
	    }
	])	
*/
	
	public List<Document> findYearWithMostMovies() {
		List<Document> lstYear = new ArrayList<Document>();
		CountDownLatch latch = new CountDownLatch(1);
	
		// Using AggregatePublisher to find year with the most movies.
		AggregatePublisher<Document> aggregatePublisher = moviesDocColl
				.aggregate(Arrays.asList(Aggregates.group("$year", Accumulators.sum("count", 1)),
						Aggregates.sort(new Document("count", -1)), Aggregates.limit(1)));
	
		Subscriber<Document> sub = new Subscriber<Document>() {
			Subscription s;
	
			@Override
			public void onSubscribe(Subscription s) {
				this.s = s;
				s.request(1);
			}
	
			@Override
			public void onNext(Document t) {
				lstYear.add(t);
				this.s.request(1);
			}
	
			@Override
			public void onError(Throwable t) {
				t.printStackTrace();
			}
	
			@Override
			public void onComplete() {
				latch.countDown();
				System.out.println("Successfully find year with the most movies.");
			}
		};
	
		aggregatePublisher.subscribe(sub);
	
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	
		return lstYear;
	}
	
	
	// 6. Liệt kê danh sách các tựa phim (title) theo từng quốc gia.
	// Thông tin bao gồm: tên quốc gia và danh sách tựa phim.
	
/*	
db.movies.aggregate([
    {
        $unwind: "$countries"
    },
    {
        $group: {
            _id: "$countries",
            movies: { $push: "$title" }
        }
    },
    {
        $project: {
            _id: 0,
            country: "$_id",
            movies: 1
        }
    }
])
*/
	
	public List<Document> findMoviesByCountry() {
		List<Document> lstMoviesByCountry = new ArrayList<Document>();
		CountDownLatch latch = new CountDownLatch(1);
	
		// Using AggregatePublisher to list all movies by country.
		// The information includes country's name and list of movies.
		AggregatePublisher<Document> aggregatePublisher = moviesDocColl.aggregate(Arrays.asList(
			Aggregates.unwind("$countries"), Aggregates.group("$countries", Accumulators.push("movies", "$title")),
			Aggregates.project(Projections.fields(Projections.excludeId(), Projections.computed("country", "$_id"),
					Projections.include("movies")))));
	
		Subscriber<Document> sub = new Subscriber<Document>() {
			Subscription s;
	
			@Override
			public void onSubscribe(Subscription s) {
				this.s = s;
				s.request(1);
			}
	
			@Override
			public void onNext(Document t) {
				lstMoviesByCountry.add(t);
				this.s.request(1);
			}
	
			@Override
			public void onError(Throwable t) {
				t.printStackTrace();
			}
	
			@Override
			public void onComplete() {
				latch.countDown();
				System.out.println("Successfully list of movies by country.");
			}
		};
	
		aggregatePublisher.subscribe(sub);
	
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	
		return lstMoviesByCountry;
	}
	
	
	// 7. Đếm số bộ phim theo từng quốc gia, sắp xếp giảm dần theo số bộ phim.
	// Thông tin bao gồm: Tên quốc gia và số bộ phim.
	// Tên quốc gia dựa vào thuộc tính countries
	// Output dạng: [ { country: 'USA', 'number of movies': 11855 },… ]
	
/*	
	db.movies.aggregate([
	    {
	        $unwind: "$countries"
	    },
	    {
	        $group: {
	            _id: "$countries",
	            count: { $sum: 1 }
	        }
	    },
	    {
	        $sort: { count: -1 }
	    }
	])	
*/
	
	public List<Document> countMoviesByCountry() {
        List<Document> lstMoviesByCountry = new ArrayList<Document>();
        CountDownLatch latch = new CountDownLatch(1);
    
        // Using AggregatePublisher to count movies by country.
        // The information includes country's name and number of movies.
        AggregatePublisher<Document> aggregatePublisher = moviesDocColl.aggregate(Arrays.asList(
                Aggregates.unwind("$countries"), Aggregates.group("$countries", Accumulators.sum("count", 1)),
                Aggregates.sort(new Document("count", -1))));
    
        Subscriber<Document> sub = new Subscriber<Document>() {
            Subscription s;
    
            @Override
            public void onSubscribe(Subscription s) {
                this.s = s;
                s.request(1);
            }
    
            @Override
            public void onNext(Document t) {
                lstMoviesByCountry.add(t);
                this.s.request(1);
            }
    
            @Override
            public void onError(Throwable t) {
                t.printStackTrace();
            }
    
            @Override
            public void onComplete() {
                latch.countDown();
                System.out.println("Successfully count movies by country.");
            }
        };
    
        aggregatePublisher.subscribe(sub);
    
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    
        return lstMoviesByCountry;
    }
	
	
	// 8. Tìm những tựa phim (title) phát hành trong tháng 03 năm 2016.
	// Output là: [ { title: 'Knight of Cups' }, { title: 'Sand Castles' }, { title: 'The Treasure' }]
	
/*	
	db.movies.aggregate([
	    {
	        $match: {
	            released: {
	                $gte: ISODate("2016-03-01"),
	                $lt: ISODate("2016-04-01")
	            }
	        }
	    },
	    {
	        $project: {
	            _id: 0,
	            title: 1
	        }
	    }
	])	
*/
	
	public List<Document> findMoviesInMarch2016() {
        List<Document> lstMoviesInMarch2016 = new ArrayList<Document>();
        CountDownLatch latch = new CountDownLatch(1);
    
        // Using AggregatePublisher to find movies in March 2016.
        AggregatePublisher<Document> aggregatePublisher = moviesDocColl.aggregate(Arrays.asList(
                Aggregates.match(Filters.and(Filters.gte("released", "2016-03-01"), Filters.lt("released", "2016-04-01"))),
                        Aggregates.project(Projections.fields(Projections.excludeId(), Projections.include("title")))));
    
        Subscriber<Document> sub = new Subscriber<Document>() {
            Subscription s;
    
            @Override
            public void onSubscribe(Subscription s) {
                this.s = s;
                s.request(1);
            }
    
            @Override
            public void onNext(Document t) {
                lstMoviesInMarch2016.add(t);
                this.s.request(1);
            }
    
            @Override
            public void onError(Throwable t) {
                t.printStackTrace();
            }
    
            @Override
            public void onComplete() {
                latch.countDown();
                System.out.println("Successfully find movies in March 2016.");
            }
        };
    
        aggregatePublisher.subscribe(sub);
    
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    
        return lstMoviesInMarch2016;
    }
	
	
	// 9. Liệt kê những tựa phim (title) do diễn viên “Frank Powell” hoặc “Charles Wellesley” đóng.
	// Diễn viên dựa vào thuộc tính cast
	// Output là : [ { title: 'A Corner in Wheat' }, { title: 'The Poor Little Rich Girl' }]
	
/*	
	db.movies.aggregate([
	    {
	        $match: {
	            cast: {
	                $in: ["Frank Powell", "Charles Wellesley"]
	            }
	        }
	    },
	    {
	        $project: {
	            _id: 0,
	            title: 1
	        }
	    }
	])
*/
	
	public List<Document> findMoviesByCast() {
		List<Document> lstMoviesByCast = new ArrayList<Document>();
		CountDownLatch latch = new CountDownLatch(1);
	
		// Using AggregatePublisher to find movies by cast.
		AggregatePublisher<Document> aggregatePublisher = moviesDocColl
				.aggregate(Arrays.asList(Aggregates.match(Filters.in("cast", "Frank Powell", "Charles Wellesley")),
						Aggregates.project(Projections.fields(Projections.excludeId(), Projections.include("title")))));
	
		Subscriber<Document> sub = new Subscriber<Document>() {
			Subscription s;
	
			@Override
			public void onSubscribe(Subscription s) {
				this.s = s;
				s.request(1);
			}
	
			@Override
			public void onNext(Document t) {
				lstMoviesByCast.add(t);
				this.s.request(1);
			}
	
			@Override
			public void onError(Throwable t) {
				t.printStackTrace();
			}
	
			@Override
			public void onComplete() {
				latch.countDown();
				System.out.println("Successfully find movies by cast.");
			}
		};
	
		aggregatePublisher.subscribe(sub);
	
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	
		return lstMoviesByCast;
	}
	
	
	// 10. Tìm những quốc gia phát hành nhiều bộ phim nhất.
	// Tên quốc gia dựa vào thuộc tính countries.
	// Output là: [ { country: 'USA' } ]
	
/*
	db.movies.aggregate([
	    {
	        $unwind: "$countries"
	    },
	    {
	        $group: {
	            _id: "$countries",
	            count: { $sum: 1 }
	        }
	    },
	    {
	        $sort: { count: -1 }
	    },
	    {
	        $limit: 1
	    }
	])
*/	
	
	public List<Document> findCountriesWithMostMovies() {
		List<Document> lstCountry = new ArrayList<Document>();
		CountDownLatch latch = new CountDownLatch(1);
	
		// Using AggregatePublisher to find country with the most movies.
		AggregatePublisher<Document> aggregatePublisher = moviesDocColl.aggregate(
				Arrays.asList(Aggregates.unwind("$countries"), Aggregates.group("$countries", Accumulators.sum("count", 1)),
						Aggregates.sort(new Document("count", -1)), Aggregates.limit(1)));
	
		Subscriber<Document> sub = new Subscriber<Document>() {
			Subscription s;
	
			@Override
			public void onSubscribe(Subscription s) {
				this.s = s;
				s.request(1);
			}
	
			@Override
			public void onNext(Document t) {
				lstCountry.add(t);
				this.s.request(1);
			}
	
			@Override
			public void onError(Throwable t) {
				t.printStackTrace();
			}
	
			@Override
			public void onComplete() {
				latch.countDown();
				System.out.println("Successfully find country with the most movies.");
			}
		};
	
		aggregatePublisher.subscribe(sub);
	
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	
		return lstCountry;
	}
}
