package com.sas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sas.entity.NoticeEntity;

@Repository
public interface NoticeRepository extends JpaRepository<NoticeEntity, Long> {

}
