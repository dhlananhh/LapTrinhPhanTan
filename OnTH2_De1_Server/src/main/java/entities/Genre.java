package entities;


import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
@Table(name = "genres")
public class Genre implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "genre_id", columnDefinition = "CHAR(36)", unique = true, nullable = false)
	private String genreId;
	
	@Column(name = "genre_name", columnDefinition = "VARCHAR(255)")
	private String genreName;
	
	@Column(name = "genre_description", columnDefinition = "TEXT")
	private String genreDescription;
	
	@OneToMany(mappedBy = "genre")
	private List<Album> albums;
}
