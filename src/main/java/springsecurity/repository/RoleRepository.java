package springsecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import springsecurity.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer>{
	
	public Role findByName(String name);

}
