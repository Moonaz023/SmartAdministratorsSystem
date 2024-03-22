package com.sas.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sas.entity.StudentEntity;
@Repository
public interface AllStudentRepository  extends JpaRepository<StudentEntity, String> {

	StudentEntity findByRoll(String roll);

	//public User findByEmail(String emaill);
}
