package com.alrussy.customer_service.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.alrussy.customer_service.entity.Address;
import com.alrussy.customer_service.repository.AddressesRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AddressesServiceImpl implements AddressesService{
	
	private final AddressesRepository repository;

	@Override
	public List<Address> findByUsername(String username) {
		return  repository.findByUsername(username);
	}

	@Override
	public void deleteByIdAndUsername(Long id, String userName) {
		repository.deleteByIdAndUsername(id, userName);
		
	}

	@Override
	public Long save(Address request) {
		return repository.save(request).getId();
	}

	


	
}
