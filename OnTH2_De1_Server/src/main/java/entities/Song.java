package entities;

import java.io.Serializable;
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
@Table(name = "songs")
public class Song implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "song_id", columnDefinition = "CHAR(36)", unique = true, nullable = false)
	private String songId;
	
	@Column(name = "song_name", columnDefinition = "VARCHAR(255)")
	private String songName;
	
	@Column(name = "runtime", columnDefinition = "VARCHAR(255)")
	private String runtime;
	
	@Column(name = "lyrics", columnDefinition = "TEXT")
	private String lyrics;
	
	@Column(name = "file_link", columnDefinition = "VARCHAR(255)")
	private String fileLink;
	
	@ManyToMany
	@JoinTable(name = "albums_songs", 
        joinColumns = @JoinColumn(name = "song_id"), 
        inverseJoinColumns = @JoinColumn(name = "album_id"))
	private List<Album> albums;
}
