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
	public String showLoginPage(@ModelAttribute(Constants.ERROR_MESSAGE_LOGIN) String errorMessageLogin, @ModelAttribute(Constants.REGISTRATION_SUCCESSFULL) String registrationSuccessfull, Model model) {
		
		model.addAttribute(Constants.LOGIN_USER, new User());
		model.addAttribute(Constants.ERROR_MESSAGE_LOGIN, errorMessageLogin);
		model.addAttribute(Constants.REGISTRATION_SUCCESSFULL, registrationSuccessfull);
		
		return ValidPaths.USER_LOGIN_USER.getPath();
	}
	
	@GetMapping("/showRegisterPage")
	public String showRegisterPage(@ModelAttribute(Constants.ERROR_MESSAGE_USERNAME) String errorMessageUsername, @ModelAttribute(Constants.ERROR_MESSAGE_PASSWORD) String errorMessagePassword, @ModelAttribute(Constants.REGISTER_USER) RegisterUser registerUser, Model model) {
		
		model.addAttribute(Constants.REGISTER_USER, registerUser);
		model.addAttribute(Constants.ERROR_MESSAGE_USERNAME, errorMessageUsername);
		model.addAttribute(Constants.ERROR_MESSAGE_PASSWORD, errorMessagePassword);
		
		return ValidPaths.USER_REGISTER_USER.getPath();
	}
	
	@PostMapping("/register")
	public String register(@ModelAttribute(Constants.REGISTER_USER) RegisterUser registerUser, RedirectAttributes redirectAttribute, HttpServletRequest request) {
		
		if (userService.getUserByUsername(registerUser.getUsername()) != null) {
			redirectAttribute.addFlashAttribute(Constants.ERROR_MESSAGE_USERNAME, "Der Benutzername \"" + registerUser.getUsername() + "\" ist bereits vergeben.");
		} else {
			if (!registerUser.getPassword().contentEquals(registerUser.getPasswordCheck())) {
				redirectAttribute.addFlashAttribute(Constants.ERROR_MESSAGE_PASSWORD, "Die beiden Passwörter stimmen nicht überein");
			} else {
				userService.registerUser(registerUser);

				redirectAttribute.addFlashAttribute(Constants.REGISTRATION_SUCCESSFULL, "Registrierung erfolgreich");
				redirectAttribute.addFlashAttribute(Constants.USER, userService.getUserByUsername(registerUser.getUsername()));
				
				return ValidPaths.REDIRECT_USER_SHOW_LOGIN_PAGE.getPath();
			}
		}
		redirectAttribute.addFlashAttribute(Constants.REGISTER_USER, registerUser);
		return ValidPaths.REDIRECT_USER_SHOW_REGISTER_PAGE.getPath();
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
