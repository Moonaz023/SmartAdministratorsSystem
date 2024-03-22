package com.sas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sas.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	public User findByEmail(String emaill);

}
