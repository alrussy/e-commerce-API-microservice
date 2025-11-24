package com.alrussy.customer_service.dto;

import java.time.LocalDateTime;

public record ProfileRequest(String firstname,String lastname,LocalDateTime dateOfBrith ,Gender gender,String imageProfile) {

}
