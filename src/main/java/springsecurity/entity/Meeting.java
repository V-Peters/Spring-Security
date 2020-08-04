package springsecurity.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "meeting")
@Getter
@Setter
@NoArgsConstructor
public class Meeting {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "date_time")
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private LocalDateTime datetime;
	
	@Column(name = "display")
	private boolean display;
	
	@Column(name = "last_updated")
	private LocalDateTime lastUpdated;
	
	public Meeting(int id, String name, LocalDateTime datetime, boolean display) {
		this.id = id;
		this.name = name;
		this.datetime = datetime;
		this.display = display;
	}

	public Meeting(String name, LocalDateTime datetime, boolean display) {
		this.name = name;
		this.datetime = datetime;
		this.display = display;
	}

	public String getDate() {
		return datetime.toString().substring(8, 10) + "." +  datetime.toString().substring(5, 7) + "." +  datetime.toString().substring(0, 4);
	}
	
	public String getTime() {
		return datetime.toString().substring(11, 16) + " Uhr";
	}
	
}
