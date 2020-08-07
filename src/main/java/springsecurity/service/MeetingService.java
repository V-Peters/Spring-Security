package springsecurity.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import springsecurity.entity.Meeting;
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
		}
		
		return meeting;
	}

	public void saveMeeting(Meeting meeting) {
		meeting.setLastUpdated(LocalDateTime.now());
		meetingRepository.save(meeting);
	}

	public void deleteMeeting(int id) {
		meetingRepository.deleteById(id);
	}

	public void changeDisplay(int id, boolean notDisplay) {
		meetingRepository.changeDisplay(id, notDisplay);
	}

}
