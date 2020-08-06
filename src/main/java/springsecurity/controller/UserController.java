package springsecurity.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import springsecurity.entity.User;
import springsecurity.helper.RegisterUser;
import springsecurity.service.UserService;
import springsecurity.valid_paths.ValidPaths;
import springsecurity.constants.Constants;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/showLoginPage")
	public String showLoginPage(@ModelAttribute(Constants.ERROR_MESSAGE_LOGIN) String errorMessageLogin, Model model) {
		
		model.addAttribute(Constants.LOGIN_USER, new User());
		model.addAttribute(Constants.ERROR_MESSAGE_LOGIN, errorMessageLogin);
		
		return ValidPaths.USER_LOGIN_USER.getPath();
	}
	
	@GetMapping("/showRegisterPage")
	public String showRegisterPage(@ModelAttribute(Constants.ERROR_MESSAGE_USERNAME) String errorMessageUsername, @ModelAttribute(Constants.ERROR_MESSAGE_PASSWORD) String errorMessagePassword, @ModelAttribute(Constants.USER) User user, Model model) {
		
		model.addAttribute(Constants.REGISTER_USER, new RegisterUser());
		model.addAttribute(Constants.ERROR_MESSAGE_USERNAME, errorMessageUsername);
		model.addAttribute(Constants.ERROR_MESSAGE_PASSWORD, errorMessagePassword);
		model.addAttribute(Constants.USER, user);
		
		return ValidPaths.USER_REGISTER_USER.getPath();
	}
	
	@PostMapping("/register")
	public String register(@ModelAttribute(Constants.REGISTER_USER) RegisterUser registerUser, RedirectAttributes redirectAttribute) {
		
		if (!registerUser.getPassword().contentEquals(registerUser.getPasswordCheck())) {
			redirectAttribute.addFlashAttribute(Constants.ERROR_MESSAGE_PASSWORD, "Die beiden Passwörter stimmen nicht überein");
			redirectAttribute.addFlashAttribute(Constants.USER, this.convertToNormalUser(registerUser));
		} else {
			if (userService.getUserByUsername(registerUser.getUsername()) != null) {
				redirectAttribute.addFlashAttribute(Constants.ERROR_MESSAGE_USERNAME, "Der Benutzername \"" + registerUser.getUsername() + "\" ist bereits vergeben.");
				redirectAttribute.addFlashAttribute(Constants.USER, this.convertToNormalUser(registerUser));
			} else {
				userService.registerUser(registerUser);
				
				redirectAttribute.addFlashAttribute(Constants.USER, userService.getUserByUsername(registerUser.getUsername()));
				
				return ValidPaths.REDIRECT_USER_LOGIN_USER.getPath();
//				return "/authenticateTheUser";
			}
		}
		return ValidPaths.REDIRECT_USER_SHOW_REGISTER_PAGE.getPath();
	}
	
	private User convertToNormalUser(RegisterUser registerUser) {
		return new User(registerUser.getUsername(), registerUser.getFirstname(), registerUser.getLastname(), registerUser.getEmail(), registerUser.getCompany());
	}
	
	@PostMapping("/login")
	public String login(@ModelAttribute(Constants.LOGIN_USER) User loginUser, RedirectAttributes redirectAttribute, Model model, HttpServletRequest request) {
		
		if (userService.getUserByUsername(loginUser.getUsername()) != null) {
			if (userService.checkPassword(loginUser)) {
				
				redirectAttribute.addFlashAttribute(Constants.USER, userService.getUserByUsername(loginUser.getUsername()));
				
				return ValidPaths.REDIRECT_USER_LOGIN_USER.getPath();
			} else {
				model.addAttribute(Constants.ERROR_MESSAGE_PASSWORD, "Falsches Passwort für diesen Benutzernamen.");
			}
		} else {
			model.addAttribute(Constants.ERROR_MESSAGE_USERNAME, "Es gibt keinen Benutzer mit diesem Namen.");
		}
		return ValidPaths.USER_LOGIN_USER.getPath();
	}
	
	@GetMapping("/loginUser")
	public String loginUser(@ModelAttribute(Constants.USER) User user, HttpServletRequest request) {
		
		System.out.println("\n" + user.getId());
		System.out.println(user.getUsername());
		System.out.println(user.getPassword());
		System.out.println(user.getFirstname());
		System.out.println(user.getLastname());
		System.out.println(user.getEmail());
		System.out.println(user.getCompany());
		System.out.println(user.getRoles().stream().findFirst().get().getName() + "\n");
		
		request.getSession().setAttribute(Constants.USER, user);
		
//		return ValidPaths.REDIRECT_MEETING_LIST.getPath();
		return "/authenticateTheUser";
	}
	
	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {
		
		request.getSession().removeAttribute(Constants.USER);
		
		return ValidPaths.REDIRECT_USER_SHOW_LOGIN_PAGE.getPath();
	}

	@GetMapping("/autoLogout")
	public String autoLogout(RedirectAttributes redirectAttribute) {
		redirectAttribute.addFlashAttribute(Constants.ERROR_MESSAGE_LOGIN, "Unbefugter Zugriff: Sie müssen sich zuerst einloggen, um die Liste der Veranstaltungen einsehen zu können.");
		return ValidPaths.REDIRECT_USER_SHOW_LOGIN_PAGE.getPath();
	}

	@GetMapping("/accessDenied")
	public String showAccessDenied() {
		return "unauthorized/access-denied";
	}

}
