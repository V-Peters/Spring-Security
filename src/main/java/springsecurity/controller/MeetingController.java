package springsecurity.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import springsecurity.entity.Meeting;
import springsecurity.entity.MeetingUser;
import springsecurity.entity.User;
import springsecurity.helper.Counter;
import springsecurity.service.MeetingService;
import springsecurity.service.MeetingUserService;
import springsecurity.valid_paths.ValidPaths;
import springsecurity.constants.Constants;

@Controller
@RequestMapping("/meeting")
public class MeetingController {
	
	private MeetingService meetingService;
	private MeetingUserService meetingUserService;
	
	public MeetingController(MeetingService meetingService, MeetingUserService meetingUserService) {
		this.meetingService = meetingService;
		this.meetingUserService = meetingUserService;
	}
	
	@GetMapping("/list")
	public String listMeetings(Model model, HttpServletRequest request) {
		
		if (!this.isUserLoggedIn(request)) {
			return ValidPaths.REDIRECT_USER_AUTO_LOGOUT.getPath();
		}
		
		User user = (User)request.getSession().getAttribute(Constants.USER);

		List<MeetingUser> tempMeetingsSignedUpTo;
		List<Integer> meetingsSignedUpTo = new ArrayList<>();
		
		tempMeetingsSignedUpTo = meetingUserService.getMeetingsForUser(user.getId());
		
		for (MeetingUser meetingUser : tempMeetingsSignedUpTo) {
			meetingsSignedUpTo.add(meetingUser.getIdMeeting());
		}
		
		List<Meeting> meetings = meetingService.getMeetings();

		model.addAttribute("counter", new Counter());
		model.addAttribute("meetings", meetings);
		model.addAttribute("meetingsSignedUpTo", meetingsSignedUpTo);
		model.addAttribute(Constants.USER, user);
		
		return ValidPaths.MEETING_LIST_MEETINGS.getPath();
	}
	
	@GetMapping("/showSavePage")
	public String updateMeeting(@ModelAttribute(Constants.MEETING_ID) int id, @ModelAttribute(Constants.ERROR_MESSAGE) String errorMessage, Model model, RedirectAttributes redirectAttribute, HttpServletRequest request) {
		
		if (!this.isUserLoggedIn(request)) {
			return ValidPaths.REDIRECT_USER_AUTO_LOGOUT.getPath();
		}

		Meeting meeting = new Meeting(true);
		
		if (id != 0) {
			meeting = meetingService.getMeeting(id);
		}
		
		model.addAttribute(Constants.MEETING, meeting);
		redirectAttribute.addFlashAttribute(Constants.ERROR_MESSAGE, errorMessage);
		model.addAttribute("now", LocalDateTime.now().toString().substring(0, 16));
		
		return ValidPaths.MEETING_SAVE_MEETINGS.getPath();
	}
	
	@PostMapping("/save")
	public String saveMeeting(@ModelAttribute(Constants.MEETING) Meeting meeting, RedirectAttributes redirectAttribute, HttpServletRequest request) {
		
		if (!this.isUserLoggedIn(request)) {
			return ValidPaths.REDIRECT_USER_AUTO_LOGOUT.getPath();
		}
		
		if (meeting.getName().length() > 100) {
			
			redirectAttribute.addFlashAttribute(Constants.MEETING_ID, meeting.getId());
			redirectAttribute.addFlashAttribute(Constants.ERROR_MESSAGE, "Der Name darf maximal 100 Zeichen lang sein");

			return ValidPaths.REDIRECT_MEETING_SHOW_SAVE_PAGE.getPath();
		}
		meetingService.saveMeeting(meeting);
		
		return ValidPaths.REDIRECT_MEETING_LIST.getPath();
	}
	
	@GetMapping("/delete")
	public String deleteMeeting(@RequestParam(Constants.MEETING_ID) int id, RedirectAttributes redirectAttribute, HttpServletRequest request) {
		
		if (!this.isUserLoggedIn(request)) {
			return ValidPaths.REDIRECT_USER_AUTO_LOGOUT.getPath();
		}
		
		meetingService.deleteMeeting(id);
		
		return ValidPaths.REDIRECT_MEETING_LIST.getPath();
	}
	
	@GetMapping("/changeDisplay")
	public String changeDisplay(@RequestParam(Constants.MEETING_ID) int id, @RequestParam("displayValue") boolean display, RedirectAttributes redirectAttribute, HttpServletRequest request) {
		
		if (!this.isUserLoggedIn(request)) {
			return ValidPaths.REDIRECT_USER_AUTO_LOGOUT.getPath();
		}
		
		meetingService.changeDisplay(id, !display);
		
		return ValidPaths.REDIRECT_MEETING_LIST.getPath();
	}
	
	private boolean isUserLoggedIn(HttpServletRequest request) {
		return request.getSession().getAttribute(Constants.USER) != null;
	}
	
}
