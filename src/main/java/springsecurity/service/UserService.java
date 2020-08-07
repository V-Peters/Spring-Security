package springsecurity.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import springsecurity.entity.Role;
import springsecurity.entity.User;
import springsecurity.helper.RegisterUser;
import springsecurity.repository.RoleRepository;
import springsecurity.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	public void registerUser(RegisterUser registerUser) {
		
		User user = new User(registerUser.getUsername(), passwordEncoder.encode(registerUser.getPassword()), registerUser.getFirstname(), registerUser.getLastname(), registerUser.getEmail(), registerUser.getCompany(), Arrays.asList(roleRepository.findByName("ROLE_USER")));
		
		userRepository.save(user);

	}

	@Override
	public UserDetails loadUserByUsername(String username) {
		User user = userRepository.findByUsername(username);
		if (user == null) {
			return null;
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), this.mapRolesToAuthorities(user.getRoles()));
		
	}

	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	}

	public User getUserByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	
}
