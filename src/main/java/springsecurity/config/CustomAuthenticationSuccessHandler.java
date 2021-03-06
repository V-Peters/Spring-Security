package springsecurity.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import springsecurity.entity.User;
import springsecurity.service.UserService;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UserService userService;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

		String userName = authentication.getName();
		
		User user = userService.getUserByUsername(userName);
		
		HttpSession session = request.getSession();
		session.setAttribute("user", user);
		
		try {
			response.sendRedirect(request.getContextPath() + "/meeting/list");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
