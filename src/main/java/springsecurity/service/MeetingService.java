package springsecurity.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import springsecurity.entity.Meeting;
import springsecurity.entity.User;
import springsecurity.repository.MeetingRepository;

@Service
public class MeetingService {
	
	private MeetingRepository meetingRepository;

	@Autowired
	public MeetingService(MeetingRepository meetingRepository) {
		this.meetingRepository = meetingRepository;
	}
	
	public List<Meeting> getMeetings() {
		return meetingRepository.findAll();
	}

	public Meeting getMeeting(int id) {
		Optional<Meeting> tempMeeting = meetingRepository.findById(id);
		
		Meeting meeting = null;
		
		if (tempMeeting.isPresent()) {
			meeting = tempMeeting.get();
		} else {
			// TODO später wieder entfernen
			System.err.println("ES KONNTE KEIN MEETING MIT DIESER ID GEFUNDEN WERDEN!!!");
			throw new RuntimeException("ES KONNTE KEIN MEETING MIT DIESER ID GEFUNDEN WERDEN - " + id);
		}
		
		return meeting;
	}

	public void saveMeeting(Meeting meeting) {
		meetingRepository.save(meeting);
		meetingRepository.setLastUpdatedToNow(meeting.getId());
	}

	public void deleteMeeting(int id) {
		meetingRepository.deleteById(id);
	}

	public void changeDisplay(int id, boolean notDisplay) {
		
//		boolean notDisplay = meetingRepository.findById(id).get().isDisplay();
		
		meetingRepository.changeDisplay(id, notDisplay);
//		meetingRepository.changeDisplay(true, 14);
		
	}

}
