package com.alrussy.customer_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alrussy.customer_service.entity.Profile;

public interface ProfileRepository extends JpaRepository<Profile, String> {
	
}
