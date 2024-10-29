package entities;


import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
@Table(name = "artists")
public class Artist implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "artist_id", columnDefinition = "CHAR(36)", unique = true, nullable = false)
	private String artistId;
	
	@Column(name = "artist_name", columnDefinition = "VARCHAR(255)")
	private String artistName;
	
	@Column(name = "birth_date", columnDefinition = "DATE")
	private LocalDate birthDate;
	
	@Column(name = "url", columnDefinition = "TEXT")
	private String url;
	
	@ManyToMany
	@JoinTable(name = "albums_artists", 
		joinColumns = @JoinColumn(name = "artist_id"), 
		inverseJoinColumns = @JoinColumn(name = "album_id"))
	private List<Album> albums;
}
