package com.sas.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
public class StudentDetailsEntity {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long studentDetailsId;
	    @OneToOne
	    @JoinColumn(name = "roll", unique = true)
//	    @JsonManagedReference
		@JsonBackReference
//		@ToStringExclude
//	    @JsonIgnoreProperties("details")  // Use this annotation instead of @JsonBackReference
	    private StudentEntity student;
	    private String fatherName;
	    private String motherName;
	    private String address;
	    private String email;
	    private String phone;
}
