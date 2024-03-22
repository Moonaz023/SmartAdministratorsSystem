package com.sas.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

@Builder
@Entity
public class NoticeEntity {
	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	private long id;
	private String date;
    private String fileName;
    private String session;
    private String year;
    private String semester;  
    private String about;
    private String notice;
    
}
