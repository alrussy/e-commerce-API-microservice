package com.alrussy.customer_service.dto;

public record AddressResponse(Long id, String name,String phoneNumber, String country, String city, String district, String street,
		String houseNumber, String note) {


}
