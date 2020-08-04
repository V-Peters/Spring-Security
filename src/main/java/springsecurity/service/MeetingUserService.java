package springsecurity.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import springsecurity.entity.Meeting;
import springsecurity.entity.MeetingUser;
import springsecurity.repository.MeetingUserRepository;
import springsecurity.repository.UserRepository;
import springsecurity.entity.User;

@Service
public class MeetingUserService {
	
	private MeetingUserRepository meetingUserRepository;
	private UserRepository userRepository;
	
	@Autowired
	public MeetingUserService(MeetingUserRepository meetingUserRepository, UserRepository userRepository) {
		this.meetingUserRepository = meetingUserRepository;
		this.userRepository = userRepository;
	}

	public List<User> getParticipants(int id) {
		
		List<MeetingUser> meetingUserList = meetingUserRepository.findByIdMeeting(id);
		List<User> users = new ArrayList<>();

		for (MeetingUser tempMeetingUser : meetingUserList) {
			users.add(userRepository.findById(tempMeetingUser.getIdUser()));
		}
		return users;
	}

	public List<MeetingUser> getMeetingsForUser(int id) {
		return meetingUserRepository.findByIdUser(id);
	}

	public void signUp(int userId, int meetingId) {
		meetingUserRepository.save(new MeetingUser(userId, meetingId));
	}

	public void signOut(int userId, int meetingId) {
		meetingUserRepository.deleteByIdUserAndIdMeeting(userId, meetingId);
	}

	public void deleteMeetings(int id) {
		meetingUserRepository.deleteByIdMeeting(id);
	}
}
