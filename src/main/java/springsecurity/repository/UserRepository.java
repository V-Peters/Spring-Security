package springsecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import springsecurity.entity.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	
	public User findByUsername(String username);

	public User findById(int id);
	
}
