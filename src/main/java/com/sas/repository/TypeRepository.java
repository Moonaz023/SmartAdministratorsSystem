package com.sas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sas.entity.Type;

@Repository
public interface TypeRepository extends JpaRepository<Type, Long> {
    // Add custom query methods if needed
	 Type findByName(String name);
}
