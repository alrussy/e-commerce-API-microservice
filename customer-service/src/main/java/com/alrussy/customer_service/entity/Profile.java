package com.alrussy.customer_service.entity;

import com.alrussy.customer_service.dto.Gender;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

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
    private String lastname;
    private String name;
    private String imageProfile;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime dateOfBrith;

    private Gender gender;
}
