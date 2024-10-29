package entities;


import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table (name = "comments")
public class Comments {
	@Column(name = "comment_content", columnDefinition = "VARCHAR(255)")
	private String commentContent;
	
	@Column(name = "comment_date", columnDefinition = "TIMESTAMP")
	private LocalDateTime commentDate;
	
	@Id
	@ManyToOne (cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn (name = "user_id")
	private User user;
	
	@Id
	@ManyToOne (cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn (name = "news_id")
	private News news;
	
	
	@Override
	public String toString() {
		return String.format("Comments [commentContent = %s, commentDate = %s]", commentContent, commentDate);
	}
}
