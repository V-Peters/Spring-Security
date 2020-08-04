package springsecurity.helper;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RegisterUser {
	
	private int id;
	private String username;
	private String password;
	private String passwordCheck;
	private String firstname;
	private String lastname;
	private String email;
	private String company;
	
}
