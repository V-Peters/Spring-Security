package springsecurity.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "meeting_user")
@Getter
@Setter
@NoArgsConstructor
public class MeetingUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "id_meeting")
	private int idMeeting;
	
	@Column(name = "id_user")
	private int idUser;
	
	public MeetingUser(int userId, int meetingId) {
		this.idUser = userId;
		this.idMeeting = meetingId;
	}
	
}
