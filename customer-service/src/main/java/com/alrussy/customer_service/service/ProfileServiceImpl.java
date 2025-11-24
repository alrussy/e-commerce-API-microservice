package com.alrussy.customer_service.service;

import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.alrussy.customer_service.entity.Profile;
import com.alrussy.customer_service.repository.ProfileRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Primary
public class ProfileServiceImpl implements ProfileService{
	
	private final ProfileRepository repository;

	@Override
	public Profile findById(String username) {
		return repository.findById(username).orElseThrow(()->new RuntimeException("Record Is Not Found By username :"+username));
	}
	
	@Override
	public void deleteByUsername(String userName) {
		repository.deleteById(userName);	
	}
	
	@Override
	public String save(Profile request) {
		
		return repository.save(request).getUsername();
	}
	
	
	@Override
	public void deleteByIdAndUsername(Long id, String userName) {}
	
	

	


	@Override
	public List<Profile> findByUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	


	
}
