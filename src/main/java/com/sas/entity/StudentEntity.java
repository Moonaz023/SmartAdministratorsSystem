package com.sas.entity;




import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
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
public class StudentEntity {
	@Id
    private String roll;
    private String session;
    private String registration;
    private String name;
    @OneToOne(mappedBy = "student", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference
//    @ToStringExclude
//    @JsonBackReference
//   @JsonIgnoreProperties("student")
//    @JsonIgnore
    private StudentDetailsEntity details;
}
