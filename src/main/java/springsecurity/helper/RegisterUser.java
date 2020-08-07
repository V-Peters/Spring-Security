package springsecurity.helper;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RegisterUser {
	
	public RegisterUser(String firstname, String lastname, String email, String company) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.company = company;
	}
	private int id;
	private String username = "";
	private String password = "";
	private String passwordCheck = "";
	private String firstname = "";
	private String lastname = "";
	private String email = "";
	private String company = "";
	
}
