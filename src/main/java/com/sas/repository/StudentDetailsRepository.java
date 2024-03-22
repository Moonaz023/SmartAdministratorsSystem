package com.sas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.sas.entity.StudentDetailsEntity;


@Repository
public interface StudentDetailsRepository  extends JpaRepository<StudentDetailsEntity, Long> {
}
