package entities;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor


@Entity
@Table(name = "OnlineCourse")
public class OnlineCourse extends Course {
	private String url;
}
