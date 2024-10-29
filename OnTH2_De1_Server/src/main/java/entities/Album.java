package entities;


import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString


@Entity
@Table(name = "albums")
public class Album implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "album_id", columnDefinition = "CHAR(36)", unique = true, nullable = false)
	private String albumId;
	
	@Column(name = "album_title", columnDefinition = "VARCHAR(255)")
	private String title;
	
	@Column(name = "price", columnDefinition = "FLOAT")
	private double price;
	
	@Column(name = "year_of_release", columnDefinition = "INT")
	private int yearOfRelease;
	
	@Column(name = "download_link", columnDefinition = "VARCHAR(255)")
	private String downloadLink;
	
	@ManyToOne
	@JoinColumn(name = "genre_id")
	private Genre genre;
	
	@ManyToMany(mappedBy = "albums")
	private List<Artist> artists;
	
}
