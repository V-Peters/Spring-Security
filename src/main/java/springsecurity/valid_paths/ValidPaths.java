package springsecurity.valid_paths;

public enum ValidPaths {

	REDIRECT_MEETING_LIST("redirect:/meeting/list"),
	REDIRECT_MEETING_SHOW_SAVE_PAGE("redirect:/meeting/showSavePage"),
	
	REDIRECT_USER_SHOW_LOGIN_PAGE("redirect:/user/showLoginPage"),
	REDIRECT_USER_SHOW_REGISTER_PAGE("redirect:/user/showRegisterPage"),
	REDIRECT_USER_LOGIN_USER("redirect:/user/loginUser"),
	REDIRECT_USER_AUTO_LOGOUT("redirect:/user/autoLogout"),
	
	MEETING_LIST_MEETINGS("meeting/list-meetings"),
	MEETING_SAVE_MEETINGS("meeting/save-meeting"),
	
	MEETINGUSER_LIST_PARTICIPANTS("meeting-user/list-participants"),
	
	USER_LOGIN_USER("user/login-user"),
	USER_REGISTER_USER("user/register-user");
	
	
	private String path;
	
	private ValidPaths(String path) {
		this.path = path;
	}
	
	public String getPath() {
		return path;
	}

}
