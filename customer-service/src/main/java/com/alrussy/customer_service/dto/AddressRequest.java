package com.alrussy.customer_service.dto;

import com.alrussy.customer_service.entity.Address;

public record AddressRequest(String name,String phoneNumber, String country, String city, String district, String street,
		String houseNumber, String note) {

	public Address mapToAddress() {
		return Address.builder().name(name).phoneNumber(phoneNumber).country(country).city(city).district(district).street(street)
				.houseNumber(houseNumber).note(note).build();
	}

}
