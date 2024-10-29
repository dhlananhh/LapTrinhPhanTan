package entities;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor


@Entity
@Table(name = "News")


// JPQL Queries
// SELECT n FROM News n WHERE n.news_tags LIKE :value OR n.news_categories LIKE :value
@NamedQueries ({
	@NamedQuery (name = "getNewsByTagsOrNewsCategories", query = "SELECT n FROM News n WHERE n.newsTags LIKE :value OR n.newsCategories LIKE :value"),
	@NamedQuery (name = "listAllNews", query = "SELECT n FROM News n")
})


public class News {
	@Id
	@Column(name = "news_id", columnDefinition = "BIGINT", unique = true, nullable = false)
	private long newsId;
	
	@Column(name = "news_title", columnDefinition = "NVARCHAR(255)")
	private String newsTitle;
	
	@Column(name = "news_content", columnDefinition = "NVARCHAR(255)")
	private String newsContent;
	
	@Column(name = "creation_date", columnDefinition = "DATETIME")
	private LocalDateTime creationDate;
	
	@Column(name = "news_categories", columnDefinition = "NVARCHAR(255)")
	private Set<String> newsCategories;
	
	@Column(name = "news_tags", columnDefinition = "NVARCHAR(255)")
	private Set<String> newsTags;
	
	@OneToMany (mappedBy = "news")
	private Set<Comments> comments;
	
	
	@Override
	public String toString() {
		return String.format("News [newsId = %s, newsTitle = %s, newsContent = %s, creationDate = %s, newsCategories = %s, newsTags = %s]", 
				newsId, newsTitle, newsContent, creationDate, newsCategories, newsTags);
	}
}
