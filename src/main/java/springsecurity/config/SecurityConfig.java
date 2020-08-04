package springsecurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import springsecurity.service.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserService userService;

    @Autowired
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    
   @Override
    protected void configure(AuthenticationManagerBuilder auth) {
		try {
	        auth.authenticationProvider(this.authenticationProvider());
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests()
        	.antMatchers("/", "/login", "/logout").permitAll()
			.antMatchers("/meeting/list", "/user/autoLogout").hasRole("USER")
			.antMatchers("/meeting/list", "/user/autoLogout").hasRole("ADMIN")
			.and()
			.formLogin()
				.loginPage("/user/showLoginPage")
				.loginProcessingUrl("/user/login")
				.successHandler(customAuthenticationSuccessHandler)
				.permitAll()
			.and()
			.logout().permitAll()
			.and()
			.exceptionHandling().accessDeniedPage("/unauthorized/access-denied");
		
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
		auth.setUserDetailsService(userService);
		auth.setPasswordEncoder(this.passwordEncoder());
		return auth;
	}
	  
}






