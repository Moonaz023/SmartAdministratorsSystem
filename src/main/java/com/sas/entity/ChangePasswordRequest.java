package com.sas.entity;

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
public class ChangePasswordRequest {

	private int id;
	private String currentPassword;
    private String newPassword;
    private String confirmPassword;
}
