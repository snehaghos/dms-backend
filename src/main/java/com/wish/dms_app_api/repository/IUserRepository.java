package com.wish.dms_app_api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wish.dms_app_api.entity.User;
import com.wish.dms_app_api.enums.Role;


@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
Optional<User> findByUsername(String username);
List<User> findByRole(Role role);
	
}

