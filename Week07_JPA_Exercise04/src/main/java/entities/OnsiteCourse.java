package entities;


import java.time.LocalDateTime;

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
@Table(name = "OnsiteCourse")
public class OnsiteCourse extends Course {
	private String days;
	private String location;
	private LocalDateTime time;
}
