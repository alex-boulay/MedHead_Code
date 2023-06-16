package com.ocal.medhead.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import com.ocal.medhead.model.Role;

public interface RoleRepository extends CrudRepository<Role, Long> {
	Optional<Role> findByName(String name);

}
