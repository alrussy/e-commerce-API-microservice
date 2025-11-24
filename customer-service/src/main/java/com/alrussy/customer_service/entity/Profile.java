package com.alrussy.customer_service.entity;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.alrussy.customer_service.dto.Gender;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity(name = "Profiles")

public class Profile {
	
@Id
private String username;

private String firstname;
private String  lastname;
private String name;
private String imageProfile;
@DateTimeFormat(pattern = "yyyy-MM-dd")
private LocalDateTime dateOfBrith;
private Gender gender;



	
}
